package csie.ase.ro;

public class Subscription {
    private String id;
    private String name;
    private String type;
    private String duration;

    public Subscription() {}

    public Subscription(String name, String type, String duration) {
        this.name = name;
        this.type = type;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
