package com.example.trackme;

public class UserDetailHelper {
    String uname,uemail,uphone,upassword;

    public UserDetailHelper() {
    }

    public UserDetailHelper(String uname, String uemail, String uphone, String upassword) {
        this.uname = uname;
        this.uemail = uemail;
        this.uphone = uphone;
        this.upassword = upassword;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }
}
