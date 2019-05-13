package com.example.start;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Stack;
//the layout used for the user to draw the desired layout
public class LayoutCanvas extends View {
    private Paint paint ;
    private int intersectPointID = 0;
    private int intersectIndex ;
    private ArrayList<Integer> index = new ArrayList<Integer>();
    private ArrayList<Integer> startOrstop = new ArrayList<Integer>();
    private  float startX , startY , endX , endY ;
    private boolean drawPoints = true;
    //to check if points is intersectedpoints
   private float[] ipoints = {-1,-1};
//Stack contains array  the id of the lines that each intersection happened with
private Stack<int[]> donewithSTACK = new Stack<int[]>();
    //array that conntains the intersected point value and the index of lines that are connected with this point
private ArrayList<IntersectedPoints> intersect = new ArrayList<IntersectedPoints>();
    //array contaions the id of the lines that the current intersection happened with
    private ArrayList<Integer> donewith = new ArrayList<Integer>();
    //array contaions the points (x,y)of new line that caused intersection
    private ArrayList<PointF> intersectPoints = new ArrayList<PointF>();
  private   ArrayList<PointF> startPoints = new ArrayList<PointF>();
    private  ArrayList<PointF> stopPoints = new ArrayList<PointF>();
    public LayoutCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(9f);

        Log.i("alaa" , "done for constructor");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.BLUE);

        //PointF repeatedpoint = null ;
        if(drawPoints){
            canvas.drawLine(startX , startY , endX, endY , paint);
        }
        for(int i = 0 ; i < stopPoints.size();i++){
            canvas.drawLine(startPoints.get(i).getX() , startPoints.get(i).getY() , stopPoints.get(i).getX(), stopPoints.get(i).getY() , paint);
        }
        for(int i = 0 ; i < intersectPoints.size() ; i++){
            paint.setColor(Color.RED);
            canvas.drawCircle(intersectPoints.get(i).getX() , intersectPoints.get(i).getY() , 10 , paint);
        }
        Log.i("alaa" , "done for draw " + intersectPoints.size());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                drawPoints = true ;
//              startX = xPos ;
//                startY = yPos ;
//                endX = xPos ;
//                endY = yPos ;
//                startPoints.add(new PointF(xPos,yPos));
//                Log.i("alaat" , "ACTION mmmm " + xPos + "y =" + yPos);
//                invalidate();
//                return true;
                ipoints[0] = -1 ;
                ipoints[1] = -1 ;
                boolean startPointsDrawn = false ;
                startX = xPos ;
                startY = yPos ;
                endX = xPos ;
                endY = yPos ;
                if(stopPoints.size() == 0){
                    startPoints.add(new PointF(startX,startY));
                    startPointsDrawn = true ;
                }else{
                    for (int i = 0 ; i < intersectPoints.size() ; i ++){
                        if(((startX > intersectPoints.get(i).getX() -80) && (startX < intersectPoints.get(i).getX() +80)) ){
                            if((startY > intersectPoints.get(i).getY() -80) && (startY < intersectPoints.get(i).getY() +80)) {
                                ipoints[0] = intersectPoints.get(i).getX();
                                ipoints[1] = intersectPoints.get(i).getY();
                                intersectIndex = i ;
                                startPointsDrawn = true ;
                                Log.i("kk" , "intersected");
                            }
                        }

                    }
                    if(ipoints[0] == -1 ){


                        for(int i = 0 ; i < stopPoints.size() ; i++){

                            if(((startX > startPoints.get(i).getX() -80) && (startX < startPoints.get(i).getX() +80)) ){
                                if((startY >  startPoints.get(i).getY() -80) && (startY < startPoints.get(i).getY() +80)){
                                    Log.i("Alaa" , "ready for your second touch");
                                    startPoints.add(startPoints.get(i));
                                    startPointsDrawn = true ;
                                    donewith.add(i);
                                    intersectPoints.add(startPoints.get(i));
                                    break;
                                }
                            }
                            if(((startX > stopPoints.get(i).getX() -80) && (startX < stopPoints.get(i).getX() +80)) ){
                                if(((startY > stopPoints.get(i).getY() -80) && (startY < stopPoints.get(i).getY() +80)) ){
                                    Log.i("Alaa" , "ready for your second touch");
                                    startPoints.add(stopPoints.get(i));
                                    startPointsDrawn = true ;
                                    donewith.add(i);
                                    intersectPoints.add(stopPoints.get(i));
                                    break;
                                }
                            }
                        }}
                }
                if(!startPointsDrawn){
                    startPoints.add(new PointF(startX ,startY));

                }
                if(((ipoints[0] != -1) && (ipoints[1] != -1))){
                    for(int i = 0 ; i <stopPoints.size() ; i++){
                        if((startPoints.get(i).getX() == ipoints[0]) && (startPoints.get(i).getY() == ipoints[1]) ){
                            index.add(i);
                            startOrstop.add(-1);
                        }
                        if((stopPoints.get(i).getX() == ipoints[0]) && (stopPoints.get(i).getY() == ipoints[1]) ){
                            index.add(i);
                            startOrstop.add(1);
                        }
                    }

                }




                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
//                endX = xPos ;
//                endY = yPos ;
//                return true;
                endX = xPos ;
                endY = yPos ;
                drawPoints = false ;
                // Log.i("indexsize" , " " + index.size());
                if(index.size() > 1){
                    int intersectCindex = -1 ;
                    for(int k = 0 ; k < intersect.size() ; k++){
                       PointF intersectC = intersect.get(k).getPoint();
                       if((intersectC.getY() == intersectPoints.get(intersectIndex).getY())&&(intersectC.getX() == intersectPoints.get(intersectIndex).getX())){
                           intersectCindex = k ;
                           break;
                       }
                    }
                    for(int i=0 ; i < index.size() ; i++){
                        //  Log.i("index" , "index of i = " + index.get(i));
                        if(startOrstop.get(i) == -1){
                            PointF p = new PointF(endX,endY);
                            startPoints.set(index.get(i) , p);
                            intersectPoints.set(intersectIndex , p);
                           if( intersectCindex != -1){
                               intersect.get(intersectCindex).setPoint(p);
                           }

                            // Log.i("alaa" , "start is changing");
                        }else {
                            PointF p = new PointF(endX,endY);
                            stopPoints.set(index.get(i) , p);
                            intersectPoints.set(intersectIndex , p);
                            intersect.get(intersectCindex).setPoint(p);
                            if( intersectCindex != -1){
                                intersect.get(intersectCindex).setPoint(p);
                            }
                            // Log.i("alaa" , "stop is changing");
                        }
                    }

                }else {
                    drawPoints = true ;
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                endX=xPos ;
                endY=yPos ;
//                Log.i("s" , "sx = " + startX +" sy = " + startY +" ex = " + endX +" ey = " +endY );
//                float x = Math.max((endX-startX) , (startX-endX));
//                float y = Math.max((endY-startY) , (startY-endY));
//                    if(x > y){
//                    Log.i("s" , "x " + endX +" y = " + startY);
//                    endY = startY ;
//                }else {
//                    Log.i("s" , "x " + startX +" y = " + endY);
//                        endX = startX ;
//                    //s[1] = endy ;
//                }
//                stopPoints.add(new PointF(endX,endY));
//                    checkIntersection();
//                Log.i("alaat" , "ACTION_UP  " + xPos + "y5tyyyy =" + yPos);
//                if(intersectPoints.size() != 0){
//                    Log.i("alaaaa" , "before pushing " +donewith.size());
//                    int[] done = new int[donewith.size()];
//
//                    for(int i = 0 ; i < donewith.size();i++){
//                        done[i] = donewith.get(i);
//                        PointF intersecttP = intersectPoints.get((intersectPoints.size()-1)-i);
//                        intersectPointID += 1 ;
//                        intersect.add(new IntersectedPoints(intersecttP, startPoints.size()-1 ,donewith.get((donewith.size()-1)-i) , intersectPointID));
//                    }
//                    donewithSTACK.push(done);
//                }
//                drawPoints = false ;
//                donewith.clear();
//                break;
//            default:
//                return false;
 //
                startOrstop.clear();
                drawPoints = false ;
                boolean drawStopPoints = false ;
                if(! (index.size() > 1)){

                    endX=xPos ;
                    endY=yPos ;
                    //   Log.i("alaa" , "stop" + count);
                    for(int i = 0 ; i<stopPoints.size();i++){
                        if(((endX > stopPoints.get(i).getX() -80) && (endX < stopPoints.get(i).getX() +80)) ) {
                            if (((endY > stopPoints.get(i).getY() - 80) && (endY < stopPoints.get(i).getY() + 80))) {
                                donewith.add(i);
                                intersectPoints.add(stopPoints.get(i));
                                stopPoints.add(stopPoints.get(i));
                                drawStopPoints = true ;
                                break;
                            }
                        }
                        if(((endX > startPoints.get(i).getX() -80) && (endX < startPoints.get(i).getX()  +80)) ) {
                            if (((endY > startPoints.get(i).getY() - 80) && (endY < startPoints.get(i).getY()  + 80))) {
                                donewith.add(i);
                                intersectPoints.add(startPoints.get(i));
                                stopPoints.add(startPoints.get(i));
                                drawStopPoints = true ;
                                break;
                            }
                        }
                    }
                    if(!drawStopPoints){
                        stopPoints.add(new PointF(endX,endY));
                    }


                }
                if(intersectPoints.size() != 0){
                    Log.i("alaaaa" , "before pushing " +donewith.size());
                    int[] done = new int[donewith.size()];

                    for(int i = 0 ; i < donewith.size();i++){
                        done[i] = donewith.get(i);
                        Log.i("aaaaaaaa" , "done" + done[i]);
                        PointF intersecttP = intersectPoints.get((intersectPoints.size()-1)-i);
                      intersectPointID += 1 ;
                      intersect.add(new IntersectedPoints(intersecttP, startPoints.size()-1 ,donewith.get((donewith.size()-1)-i) , intersectPointID));
                  }
                   donewithSTACK.push(done);
               }
                drawPoints = false ;
                donewith.clear();
                index.clear();
                break;
            default:
                return false;
        }
       checkI();
        invalidate();


        return true ;
    }
    public void checkI(){
        int repeatedpointindex = -1 ;
        for(int i = 0 ; i < stopPoints.size();i++){
            if(startPoints.get(i).getX() == stopPoints.get(i).getX()){
                if(startPoints.get(i).getY() == stopPoints.get(i).getY()){
                    repeatedpointindex = i;
                    Log.i("repeated" ," =================== " + i);
                }
            }
        }

        Log.i("repeated" ," =================== "+ intersectPoints.size()+" =======================" );
        if(repeatedpointindex != -1){
            PointF repeatedpoint = stopPoints.get(repeatedpointindex);

            int x = 0;
            ArrayList<PointF>inn = new  ArrayList<PointF>();
            for(int i = 0 ; i < intersectPoints.size() ;i++){
                if((intersectPoints.get(i).getX() == repeatedpoint.getX())&&(intersectPoints.get(i).getY() == repeatedpoint.getY())){
                    x++;
                    inn.add(intersectPoints.get(i));
                   // break;
                }
            }
            for(int i = 0 ; i < inn.size() ; i++){
                intersectPoints.remove(inn.get(i));
            }
           // x = 0 ;
            ArrayList<IntersectedPoints>in = new  ArrayList<IntersectedPoints>();
            Log.i("repeated" ," =================== here =======================" );
            for(int i = 0 ; i < intersect.size() ;i++){
                if((intersect.get(i).getPoint().getX() == repeatedpoint.getX())&&(intersect.get(i).getPoint().getY() == repeatedpoint.getY())){
                    in .add(intersect.get(i));
                   // x++ ;
                }
            }
            for(int i = 0 ; i < in.size() ; i++){
                intersect.remove(in.get(i));
            }
            startPoints.remove(repeatedpointindex);
            stopPoints.remove(repeatedpointindex);
        }
        Log.i("repeated" ," =================== "+ intersectPoints.size()+" =======================" );


    }
    private void checkIntersection() {
        if(startPoints.size() > 1){
            int lastIndex = startPoints.size()-1 ;
            float constant ;

            boolean in = false ;
            float sx = startPoints.get(lastIndex).getX();
            float sy = startPoints.get(lastIndex).getY();
            float ex = stopPoints.get(lastIndex).getX();
            float ey = stopPoints.get(lastIndex).getY();
            if(ex-sx == 0){
                constant = sx ;
                float intersectY ;
                for(int i = 0 ; i < startPoints.size()-1 ; i++){
                    if(!(donewith.contains(i))){
                    intersectY = startPoints.get(i).getY();
                    float maxX = Math.max(startPoints.get(i).getX() ,stopPoints.get(i).getX()  );
                    float minX = Math.min(startPoints.get(i).getX()  ,stopPoints.get(i).getX()  );
//                    float maxY = Math.max(startPoints.get(i).getY() ,stopPoints.get(i).getY() );
//                    float minY = Math.min(startPoints.get(i).getY() ,stopPoints.get(i).getY() );
                    float minnyY = Math.min(sy ,ey );
                    float maxxy = Math.max(sy ,ey );
                    float intersect =  startPoints.get(i).getY();
                    if(((maxX + 40 >= constant) && ((minX-40 <= constant)))){
                        if(((intersect <= maxxy+40 && intersect>= minnyY-40))){

                            Log.i("Intersection" , "at constants x = " + constant + " y = " + intersect +" " +
                                    startPoints.get(i).getY()   );
                            int[] ix = new int[2] ;
                            donewith.add(i);
                            intersectPoints.add(new PointF(constant , intersect));
                            in = true ;
                        }
                    }}}
            }else {
                constant = sy ;
                for(int i = 0 ; i < startPoints.size()-1 ; i++){
                    if(!(donewith.contains(i))){
                    float maxY = Math.max(startPoints.get(i).getY() ,stopPoints.get(i).getY() );
                    float minY = Math.min(startPoints.get(i).getY() ,stopPoints.get(i).getY() );
                    float minnyX = Math.min(sx ,ex );
                    float maxxX= Math.max(sx ,ex );
                    if(((maxY + 40 >= constant) && ((minY-40 <= constant)))){
                        float intersect =  startPoints.get(i).getX();
                        if((intersect <= maxxX+40 && intersect>= minnyX-40)){

                            Log.i("Intersection" , "at constants x = " + intersect + " " + " y = " + constant);
                            intersectPoints.add(new PointF(intersect , constant));
                            donewith.add(i);
                            in = true ;
                        }
                    }}}
            }}}
    public ArrayList<PointF> getStartPoints(){
        return startPoints ;
    }
    public ArrayList<PointF> getStopPoints(){
        return stopPoints ;
    }
    public ArrayList<IntersectedPoints> getIntersectedPoints(){
        return intersect ;
    }
    public ArrayList<int[]> getDonewithStack(){
        ArrayList<int[]> donewithstackArray = new ArrayList<>();
        for(int i = 0 ; i < donewithSTACK.size() ; i++){
            donewithstackArray.add(donewithSTACK.pop());
        }
        return donewithstackArray ;
    }
    public void setStartPoints(ArrayList<PointF> x){

        startPoints = x;
    }
    public void setStopPoints(ArrayList<PointF> x){

        stopPoints = x;
    }
    public void setIntersectedPoints(ArrayList<IntersectedPoints> x){

         intersect = x ;
         for(int i = 0 ; i <intersect.size() ; i ++){
             intersectPoints.add(intersect.get(i).getPoint());
         }
    }
    public void setDonewithStack(ArrayList<int[]> x){
        ArrayList<int[]> donewithstackArray = new ArrayList<>();
        for(int i = 0 ; i < x.size() ; i++){
          donewithSTACK.push(x.get(i));
        }
    }

    public void resetDrawing(){
        Log.i("alaa" ,"intersectedpoints" + intersectPoints.size());

//        if(!(donewithSTACK.empty())) {
//            Log.i("aaaaaaaaaaaaa" , "donewithSTACK is "+ donewithSTACK.size());
//            int[] x = donewithSTACK.pop();
//            int xsize = x.length;
//            Log.i("aaaaaaaaaaaaa" , "xsize is "+ x.length);
//            for (int i = 0; i < xsize; i++) {
//                intersect.remove((intersect.size() - 1));
//                Log.i("alaa", "intersectedpoints x = " + intersectPoints.get(intersectPoints.size()-1).getX());
//                intersectPoints.remove((intersectPoints.size() - 1));
//                if(intersectPointID != 0 ) {
//                    intersectPointID -= 1;
//                }
//            }
//        }
        Log.i("intersect" , "size of intersection " +intersectPoints.size());
         ArrayList<Integer> index = new ArrayList<>();
        ArrayList<Integer> intersectIndex = new ArrayList<>();
          PointF startPoint = startPoints.get(startPoints.size() - 1);
          PointF stopPoint = stopPoints.get(stopPoints.size() - 1);
          for(int i = 0 ; i < intersectPoints.size() ; i ++){
            boolean flag1 = (intersectPoints.get(i).getX() == startPoint.getX()) && (intersectPoints.get(i).getY() == startPoint.getY());
            boolean flag2 = (intersectPoints.get(i).getX() == stopPoint.getX()) && (intersectPoints.get(i).getY() == stopPoint.getY());
            Log.i("intersect" , "flag1" + flag1 + " flag2 " + flag2);
            if(flag1 || flag2)  {
                index.add(i);
                Log.i("intersect" , "flag is true " +i);

            }
        }
        for(int i = 0 ; i < intersect.size() ; i ++){
            boolean flag1 = (intersect.get(i).getPoint().getX() == startPoint.getX()) && (intersect.get(i).getPoint().getY() == startPoint.getY());
            boolean flag2 = (intersect.get(i).getPoint().getX() == stopPoint.getX()) && (intersect.get(i).getPoint().getY() == stopPoint.getY());
            Log.i("intersect" , "flag1" + flag1 + " flag2 " + flag2);
            if(flag1 || flag2)  {
                intersectIndex.add(i);
                Log.i("intersect" , "intersectarray flag is true " +i);

            }
        }
          Log.i("intersect" , "size of index = " + index.size());
          for(int i = index.size()-1  ; i > -1 ; i--){
              Log.i("intersect" , "before removing size = "+ intersectPoints.size());
              PointF in = intersectPoints.get(index.get(i));
              intersectPoints.remove(in);

              intersect.remove(index.get(i));
              Log.i("intersect" , "i am here " + index.get(i) +" size = "+ intersectPoints.size());

          }
        for(int i = intersectIndex.size()-1  ; i > -1 ; i--){
            Log.i("intersect" , "intersect array before removing size = "+ intersect.size());
            IntersectedPoints in = intersect.get(index.get(i));
            intersect.remove(in);
            //intersect.remove(index.get(i));
            Log.i("intersect" , "i am here " + index.get(i) +" size = "+ intersect.size());
            if(intersectPointID != 0 ) {
                intersectPointID -= 1;
            }
        }
//        Log.i("alaa", "intersectedpoints x = " + intersectPoints.get(intersectPoints.size()-1).getX());
            Log.i("alaa", "intersectedpoints" + intersectPoints.size());
            stopPoints.remove(stopPoints.size() - 1);
            startPoints.remove(startPoints.size() - 1);
            invalidate();
        }

    public void clearDrawing(){
            donewithSTACK.clear();
            startPoints.clear();
            stopPoints.clear();
            intersect.clear();
            intersectPoints.clear();
            intersectPointID = 0 ;
            invalidate();

    }
}




