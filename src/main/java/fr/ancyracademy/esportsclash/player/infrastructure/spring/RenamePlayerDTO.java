package fr.ancyracademy.esportsclash.player.infrastructure.spring;

public class RenamePlayerDTO {
    private String name;

    public RenamePlayerDTO(String name) {
        this.name = name;
    }

    public RenamePlayerDTO() {
    }


    public String getName() {
        return name;
    }
}
