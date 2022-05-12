package entity;

public class FbPostUser {
    private String id_str;
    private String name;
    private String screen_name;

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    @Override
    public String toString() {
        return id_str +
                "\u0001" +
                name +
                "\u0001" +
                screen_name +
                "";
    }

}
