package se.ifmo.server;

import lombok.Getter;

import java.util.Optional;

@Getter
public class ServerConfiguration {

    private int port;

    public ServerConfiguration(int port) {
        this.port = port;
    }

}
