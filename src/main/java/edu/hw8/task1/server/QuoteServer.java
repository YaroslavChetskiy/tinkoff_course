package edu.hw8.task1.server;

import edu.hw8.task1.exception.ServerException;
import edu.hw8.task1.settings.ServerSettings;
import edu.hw8.task1.storage.QuoteStorage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuoteServer implements Server {

    private final ExecutorService pool;

    private final Map<SocketChannel, Boolean> sessions = new ConcurrentHashMap<>();
    private final QuoteStorage storage;

    private final ServerSettings settings;

    private volatile boolean isRunning;

    public QuoteServer(int poolSize, QuoteStorage storage) {
        this(poolSize, storage, new ServerSettings());
    }

    public QuoteServer(int poolSize, QuoteStorage storage, ServerSettings settings) {
        this.pool = Executors.newFixedThreadPool(poolSize);
        this.storage = storage;
        this.settings = settings;
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void start() {
        try (var selector = SelectorProvider.provider().openSelector()) {
            try (var serverSocket = ServerSocketChannel.open()) {
                configureServerSocketChannel(serverSocket, selector);
                isRunning = true;
                while (isRunning) {
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (!handleSelectionKey(selector, serverSocket, key)) {
                            key.cancel();
                            isRunning = false;
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            throw new ServerException("Could not start server", e);
        } finally {
            sessions.clear();
        }
    }

    private void configureServerSocketChannel(ServerSocketChannel serverSocket, Selector selector)
        throws IOException {
        serverSocket.socket().bind(new InetSocketAddress(settings.name(), settings.port()));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    }

    private void configureServerSocket(Selector selector, ServerSocketChannel serverSocket) {
        try {
            var client = serverSocket.accept();
            if (client != null) {
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ);
            }
        } catch (IOException e) {
            throw new ServerException("Could not accept client connection", e);
        }
    }

    private boolean handleSelectionKey(Selector selector, ServerSocketChannel serverSocket, SelectionKey key) {
        if (!key.isValid()) {
            return false;
        }
        if (key.isAcceptable()) {
            configureServerSocket(selector, serverSocket);
        }
        if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            if (!sessions.getOrDefault(socketChannel, false)) {
                sessions.put(socketChannel, true);
                var future = pool.submit(() -> {
                    var result = processSocket(socketChannel);
                    sessions.put(socketChannel, false);
                    return result;
                });
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean processSocket(SocketChannel socketChannel) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(settings.capacity());
            int r = -1;
            while ((r = socketChannel.read(buffer)) > 0) {
                buffer.flip();
                String request = new String(buffer.array()).trim();
                var quote = storage.getQuoteByKeyWord(request);
                buffer.clear();
                buffer.put(quote.getBytes());
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            return false;
        }
        try {
            socketChannel.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
