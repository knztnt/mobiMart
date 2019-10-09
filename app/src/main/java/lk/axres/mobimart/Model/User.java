package lk.axres.mobimart.Model;

public class User {
    private String Displayname;
    private String Email;
    private long createdAt;

    public User() {
    }

    public User(String displayname, String email, long createdAt) {
        Displayname = displayname;
        Email = email;
        this.createdAt = createdAt;
    }

    public String getDisplayname() {
        return Displayname;
    }

    public void setDisplayname(String displayname) {
        Displayname = displayname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}