package fr.ancyracademy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;

public class RenamePlayerCommand implements Command<Void> {
    private final String name;
    private final String id;


    public RenamePlayerCommand(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
