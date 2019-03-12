package com.example.start;

public class PathLine {
    private PointF point1 ;
    private PointF point2 ;
    private float size ;
    private int lineId ;
    private boolean checked ;
    public PathLine(PointF point1 ,PointF point2  , float size , int lineId){
         this.point1 = point1;
        this.point2 = point2;
        this.size = size;
        this.lineId = lineId;
        this.checked = false ;
    }
    public PointF getPoint1(){
        return point1 ;
    }
    public PointF getPoint2(){
        return point2 ;
    }
    public float getSize(){
        return size ;
    }
    public void setChecked (boolean f){
        checked = f ;
    }
    public boolean checkexist(PathLine p){
        if((this.point1.equals(p.point1))&&(this.point2.equals(p.point2))){
            return true ;
        }
        if((this.point1.equals(p.point2))&&(this.point2.equals(p.point1))){
            return true ;
        }
        return false ;
    }
}
