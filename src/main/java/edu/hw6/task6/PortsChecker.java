package edu.hw6.task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public final class PortsChecker {

    private static final String TABLE_HEADER = "Протокол\tПорт\tСтатус   \tСервис\n";
    private static final int MAX_PORT = 49151;

    private static final Map<Integer, String> PORTS_OWNERS = Map.of(
        53, "Domain Name System",
        80, "HyperText Transfer Protocol",
        135, "EPMAP",
        137, "Служба имен NetBIOS",
        138, "Служба датаграмм NetBIOS",
        445, "Server Message Block",
        3306, "MySQL DataBase",
        5432, "PostgreSQL Database",
        8080, "HTTP proxy server"
    );

    public enum Protocol {
        TCP,
        UDP
    }

    private PortsChecker() {
    }

    public static boolean checkTcp(int port) {
        try (var serverSocket = new ServerSocket(port)) {
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean checkUdp(int port) {
        try (var datagramSocket = new DatagramSocket(port)) {
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private static String makePortInfo(int port, Protocol protocol, boolean isUsed) {
        return "%-8s\t%-4d\t%-10s\t%s\n".formatted(
            protocol.name(),
            port,
            isUsed ? "Занят" : "Свободен",
            PORTS_OWNERS.getOrDefault(port, "")
        );
    }

    public static void checkPortsAndMakeInfoFile(Path path, Protocol protocol) {
        try {
            Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING).close();
            Files.write(path, TABLE_HEADER.getBytes());
            for (int i = 0; i <= MAX_PORT; i++) {
                String info = makePortInfo(i, protocol, checkTcp(i));
                Files.write(path, info.getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
