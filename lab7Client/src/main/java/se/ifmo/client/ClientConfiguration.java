package se.ifmo.client;

import lombok.Getter;

import java.net.InetAddress;

@Getter
public class ClientConfiguration {
    private final InetAddress host;
    private final int port;

    public ClientConfiguration(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }
}
