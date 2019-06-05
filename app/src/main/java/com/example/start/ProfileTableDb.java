package com.example.start;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "profile_table")
public class ProfileTableDb {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    private  String user_name ;
    private int user_height ;
   // private int user_weight ;
    private  String user_gender ;
    private String birthdate ;
    private String user_backproblems ;
    public ProfileTableDb(String user_name,int user_height , String user_gender ,String birthdate ,String user_backproblems
    ){
        this.user_name=user_name;
        this.user_height = user_height ;
        //this.user_weight = user_weight ;
        this.user_gender=user_gender;
        this.birthdate=birthdate;
        this.user_backproblems = user_backproblems ;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }
    public int getUser_height() {
        return user_height;
    }

    public String getUser_backproblems() {
        return user_backproblems;
    }
//    public int getUser_weight(){
//        return user_weight ;
//    }
    public String getBirthdate() {
        return birthdate;
    }
}
