package com.example.start;

public class PointF implements java.io.Serializable {
   private float x ;
    private  float y ;
    private boolean checked ;
    public PointF(float x, float y){
        this.x = x ;
        this.y = y ;
        this.checked = false ;
    }
    public float getX(){
        return this.x ;
    }
    public float getY(){
        return this.y ;
    }
    public void setChecked(boolean x){
        this.checked = x ;
    }
    public boolean getChecked(){
        return this.checked;
    }
}
