package com.example.start;

public class PointF implements java.io.Serializable {
   private float x ;
    private  float y ;
    public PointF(float x, float y){
        this.x = x ;
        this.y = y ;
    }
    public float getX(){
        return this.x ;
    }
    public float getY(){
        return this.y ;
    }

}
