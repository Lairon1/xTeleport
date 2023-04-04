package com.lairon.xteleport.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Server {

    private final String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Server server = (Server) o;

        return name.equals(server.name);
    }

}
