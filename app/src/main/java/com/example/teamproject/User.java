package com.example.teamproject;

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
    public Map<String,Object> toMap(){
        HashMap<String,Object> result=new HashMap<>();
        result.put("uid",uid);
        result.put("id",id);
        result.put("password",passwd);
        result.put("email",email);
        result.put("name",name);
        result.put("nickname",nickname);
        result.put("phone",phone);
        return result;
    }

}
