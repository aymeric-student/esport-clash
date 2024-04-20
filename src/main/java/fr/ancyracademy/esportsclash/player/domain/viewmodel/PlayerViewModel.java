package fr.ancyracademy.esportsclash.player.domain.viewmodel;

public class PlayerViewModel {
    private String id;
    private String name;

    public PlayerViewModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public PlayerViewModel() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
