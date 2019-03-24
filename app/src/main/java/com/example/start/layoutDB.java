package com.example.start;

import java.util.ArrayList;

public class layoutDB {
   private ArrayList<PointF> resultPoints = new ArrayList<PointF>();
   public layoutDB(ArrayList<PointF>  resultPoints){
       this.resultPoints = resultPoints ;
   }
   public ArrayList<PointF> getResultPoints(){
       return resultPoints ;
   }
}
