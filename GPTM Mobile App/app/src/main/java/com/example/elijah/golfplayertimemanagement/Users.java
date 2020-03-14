package com.example.elijah.golfplayertimemanagement;

public class Users {
    String Uid;
    String email;

    public Users() {
    }

    public Users(String uid, String email) {
        Uid = uid;
        this.email = email;

    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





    @Override
    public String toString() {
        return "Users{" +
                "Uid='" + Uid + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
