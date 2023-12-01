package edu.hw8.task1.client;

import edu.hw8.task1.exception.ClientException;
import edu.hw8.task1.settings.ServerSettings;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class QuoteClient implements Client {

    private final ServerSettings settings;

    private SocketChannel client;
    private ByteBuffer buffer;

    public QuoteClient() {
        this(new ServerSettings());
    }

    public QuoteClient(ServerSettings settings) {
        this.settings = settings;
    }

    @Override
    public void start() {
        try {
            client = SocketChannel.open(new InetSocketAddress(settings.name(), settings.port()));
            buffer = ByteBuffer.allocate(settings.capacity());
        } catch (IOException e) {
            throw new ClientException("Could not start", e);
        }
    }

    @Override
    public Optional<byte[]> waitResponse() {
        try {
            buffer.clear();
            var bytesRead = client.read(buffer);
            if (bytesRead > 0) {
                buffer.flip();
                byte[] response = new byte[bytesRead];
                buffer.get(response);
                return Optional.of(response);
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new ClientException("Could not read response", e);
        }
    }

    @Override
    public void send(byte[] bytes) {
        try {
            buffer.clear();
            buffer.put(bytes);
            buffer.flip();
            client.write(buffer);
        } catch (IOException e) {
            throw new ClientException("Could not send", e);
        }
    }

    @Override
    public void close() throws IOException {
        if (client != null && client.isOpen()) {
            client.close();
        }
        buffer = null;
    }
}
