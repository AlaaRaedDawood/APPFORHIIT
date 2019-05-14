package com.example.start;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "performance_table")
public class PerformanceTableDB {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    private  String date ;
    private float time ;
    private int user_heartRate ;
    private int user_score ;
    //to be able to know if it is a trial or playinggame
   // private int flag ;
    public PerformanceTableDB(String date,float time , int user_heartRate ,int user_score ){
        this.date=date;
        this.time = time ;
        this.user_heartRate = user_heartRate;
        this.user_score = user_score ;
       // this.user_heartRate = user_heartRate ;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public float getTime() {
        return time;
    }

    public int getUser_heartRate() {
        return user_heartRate;
    }
    public int getUser_score() {
        return user_score;
    }
}
