package com.example.elijah.golfplayertimemanagement;

public class Users {
    String Uid;
    String email;
    String isAdmin;


    public Users(String uid, String email,  String isAdmin) {
        Uid = uid;
        this.email = email;

        this.isAdmin = isAdmin;

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



    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }



    @Override
    public String toString() {
        return "Users{" +
                "Uid='" + Uid + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                '}';
    }
}
