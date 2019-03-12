package com.example.start;

import android.graphics.Point;

public class IntersectedPoints  implements java.io.Serializable {
    private PointF point ;
    //    private PointF startPointLine ;
//    private PointF stopPointLine ;
//    private PointF startPointConnectedLine ;
//    private PointF stopPointConnectedLine ;
    private int indexOfLine1 ;
    private int indexOfLine2 ;
    private static int Id  = 0;
    public IntersectedPoints(PointF point , int indexOfLine1 , int indexOfLine2 ){
        this.point = point ;
        this.indexOfLine1 = indexOfLine1 ;
        this.indexOfLine2 = indexOfLine2 ;
        this.Id =  Id + 1 ;

    }
    public PointF getPoint(){
        return point ;
    }
    public int getIndexOfLine1(){
        return indexOfLine1 ;
    }
    public int getIndexOfLine2(){
        return indexOfLine2 ;
    }
}