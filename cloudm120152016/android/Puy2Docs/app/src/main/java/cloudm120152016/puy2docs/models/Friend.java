package cloudm120152016.puy2docs.models;

public class Friend {
    String id;
    String email;
    String username;
    String first_name;
    String lastname_name;

    public Friend(String id, String email, String username, String first_name, String lastname_name) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.first_name = first_name;
        this.lastname_name = lastname_name;
    }

    @Override
    public String toString() {
        return username;
    }
}
