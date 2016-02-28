package cloudm120152016.puy2docs.models;

public class User {
    String id;
    String email;
    String username;
    String first_name;
    String lastname_name;
    String birthday;

    public User(String id, String email, String username, String first_name, String lastname_name, String birthday) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.first_name = first_name;
        this.lastname_name = lastname_name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return username;
    }
}
