package com.example.teamproject.models;


import java.util.HashMap;
import java.util.Map;

public class User {

    public String id;
    public String email;
    public String passwd;
    public String name;
    public String nickname;
    public String phone;
    public String uid;

    public User(){

    }

    public User(String uid,String id,String email, String passwd,String name, String nickname, String phone){
        this.uid=uid;
        this.id=id;
        this.email=email;
        this.passwd=passwd;
        this.name=name;
        this.nickname=nickname;
        this.phone=phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //    public Map<String,Object> toMap(){
//        HashMap<String,Object> result=new HashMap<>();
//        result.put("uid",uid);
//        result.put("id",id);
//        result.put("password",passwd);
//        result.put("email",email);
//        result.put("name",name);
//        result.put("nickname",nickname);
//        result.put("phone",phone);
//        return result;
//    }
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", id='" + id + '\'' +
                ", passwd='" + passwd + '\'' +
                "name='"+ name +'\''+", nickname='"+nickname+
                '\''+", phone='"+phone+'\''+
                '}';
    }

}