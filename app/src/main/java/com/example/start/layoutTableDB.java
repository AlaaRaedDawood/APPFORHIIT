package com.example.start;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;


//create entity class
@Entity(tableName = "layout_table")

public class layoutTableDB  implements java.io.Serializable{
    @PrimaryKey(autoGenerate = true)
    private int id ;
    private  String layout_name ;

    public ArrayList<PointF> startPoints = new ArrayList<PointF>();
    private ArrayList<PointF> stopPoints = new ArrayList<PointF>();
   private ArrayList<PointF> intersectPoints = new ArrayList<PointF>();
    private ArrayList<IntersectedPoints> intersect = new ArrayList<IntersectedPoints>();
    private ArrayList<PathLine> pathLines = new ArrayList<PathLine>();
    private float targetTime ;
   private int used ;



    public layoutTableDB(String layout_name , ArrayList<IntersectedPoints> intersect, ArrayList<PathLine> pathLines , ArrayList<PointF>  intersectPoints , ArrayList<PointF>  startPoints, ArrayList<PointF>  stopPoints , float targetTime,int used){
       this.intersectPoints = intersectPoints ;
       this.intersect = intersect;
       this.pathLines = pathLines ;
       this.layout_name = layout_name;
       this.startPoints=startPoints;
       this.stopPoints=stopPoints;
       this.targetTime = targetTime ;
       this.used = used ;
   }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<PointF> getIntersectPoints(){

       return intersectPoints ;
    }
    public ArrayList<PathLine> getPathLines() {
        return pathLines;
    }
    public ArrayList<PointF> getStartPoints(){
        return startPoints ;
    }

    public float getTargetTime() {
        return targetTime;
    }

    public ArrayList<PointF> getStopPoints(){

       return stopPoints ;
    }

    public ArrayList<IntersectedPoints> getIntersect() {
        return intersect;
    }


    public String getLayout_name(){

       return layout_name ;
    }
    public void setUsed(int x){

       this.used = x ;
    }
    public int getUsed(){

       return used ;
    }
}
