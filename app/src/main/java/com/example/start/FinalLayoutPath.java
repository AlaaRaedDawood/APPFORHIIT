package com.example.start;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;

public class FinalLayoutPath extends View {
    private Paint paint ;
    private float[] measures ;
   private int repeatDrawing = 0 ;
    ArrayList<PointF> resultPoints = new ArrayList<PointF>();
    private ArrayList<PathLine> resultPath = new ArrayList<PathLine>();
    ArrayList<Region> regions = new ArrayList<Region>();
    int createRegion = 0 ;
    private ArrayList<PathLine> diagnoleLines = new ArrayList<PathLine>();
    ArrayList<IntersectedPoints> intersectPoints = new ArrayList<IntersectedPoints>();
    private   ArrayList<PointF> startPoints = new ArrayList<PointF>();
    private  ArrayList<PointF> stopPoints = new ArrayList<PointF>();
    private ArrayList<PathLine> pathLines = new ArrayList<PathLine>();
    private ArrayList<PathLine> plines = new ArrayList<PathLine>();
    public FinalLayoutPath(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        Log.i("alaa" , "done for constructor");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(startPoints.size() != 0){
            for(int i = 0 ; i < intersectPoints.size() ;i++){
                Log.i("alaaa" , " i1 = " + intersectPoints.get(i).getPoint().getX() + "l2 = " + intersectPoints.get(i).getPoint().getY());
                Log.i("alaa" , "l1 "+ intersectPoints.get(i).getIndexOfLine1() + " l2 = " + intersectPoints.get(i).getIndexOfLine2());
            }
            if(repeatDrawing == 0) {
                checkLinePath();
            }
            Log.i("alaa" , "number of paths are " + pathLines.size());
            if(pathLines.size() != 0) {
                if(repeatDrawing == 0) {
                    checkDiagnolePath();
//                for(int i = 0 ; i < pathLines.size() ; i++) {
//                    Log.i("alaa", "p1 x= " + pathLines.get(i).getPoint1().getX() +" y = "+ pathLines.get(i).getPoint1().getY());
//                    Log.i("alaa", "p2 x= " + pathLines.get(i).getPoint2().getX() +" y = "+ pathLines.get(i).getPoint2().getY());
//                    Log.i("alaa" , "line id = " +pathLines.get(i).getLineid()  );
//                }
                    for (int i = 0; i < diagnoleLines.size(); i++) {
                        Log.i("alaa", "d1 x= " + diagnoleLines.get(i).getPoint1().getX() + " y = " + diagnoleLines.get(i).getPoint1().getY());
                        Log.i("alaa", "d2 x= " + diagnoleLines.get(i).getPoint2().getX() + " y = " + diagnoleLines.get(i).getPoint2().getY());
                    }
                    createResultPath();
                }
                for(int i = 0 ; i <resultPoints.size() ;i++){

                    String index  = Integer.toString( i );
                    int d = 20 ;
                    if(i < pathLines.size()) {
                        paint.setTextSize(70);
                        paint.setColor(Color.GREEN);
                        canvas.drawText(index, resultPoints.get(i).getX()+ d, resultPoints.get(i).getY(), paint);

                    }
                    paint.setColor(Color.GRAY);
                    for(int radius = 0 ; radius < 41 ; radius++) {
                        canvas.drawCircle(resultPoints.get(i).getX(), resultPoints.get(i).getY(), radius, paint);
                    }
                    if(createRegion == 0) {
                        regions.add(new Region((int) (resultPoints.get(i).getX() - 40), (int) (resultPoints.get(i).getY() - 40), (int) (resultPoints.get(i).getX() + 40), (int) (resultPoints.get(i).getY() + 40)));
                    }
                    Log.i("alaa" , "the x = " + resultPoints.get(i).getX() + " y = " + resultPoints.get(i).getY() );

                    }
                    repeatDrawing ++ ;
                createRegion += 1 ;
               Log.i("alaat" ,"  the ssssssssssss  "  + regions.size());
            }
        }
        for(int i = 0 ; i < startPoints.size();i++){
            paint.setColor(Color.BLUE);
                canvas.drawLine(startPoints.get(i).getX() , startPoints.get(i).getY() , stopPoints.get(i).getX() , stopPoints.get(i).getY() , paint);
            }

        Log.i("alaa" , "done for drawwwwwwwwwwwww " + regions.size());

    }   public boolean onTouchEvent(MotionEvent event) {
        int xPos = (int) event.getX();
        int yPos = (int)  event.getY();
        // Log.i("alaa" , "done for 5ra");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for(int i = 0 ;i< regions.size();i++){
            if (regions.get(i).contains(xPos, yPos)) {
                Log.i("region" , "region is selected" + i);
                break;
            }}


        }

        invalidate();


        return true ;
    }
    public void checkLinePath(){
        for(int i = 0 ; i< intersectPoints.size();i++){
            for(int j = i+1 ; j <intersectPoints.size();j++){
                boolean flag = false ;
                IntersectedPoints i1 = intersectPoints.get(i) ;
                IntersectedPoints i2 = intersectPoints.get(j);
                if((i1.getIndexOfLine1() == i2.getIndexOfLine1())&& (!flag)){
                    pathLines.add(new PathLine(i1.getPoint() ,i2.getPoint(),measures[i2.getIndexOfLine1()] , i2.getIndexOfLine1() ));
                    plines.add(new PathLine(i1.getPoint() ,i2.getPoint(),measures[i2.getIndexOfLine1()] , i2.getIndexOfLine1() ));
                    flag = true ;
                    int ii = pathLines.size()-1;
                    Log.i("alaa", "p1 x= " + pathLines.get(ii).getPoint1().getX() +" y = "+ pathLines.get(ii).getPoint1().getY());
                    Log.i("alaa", "p2 x= " + pathLines.get(ii).getPoint2().getX() +" y = "+ pathLines.get(ii).getPoint2().getY());
                    Log.i("alaa" , "line id = " +pathLines.get(ii).getLineid()  );

                    Log.i("alaaaaaaaaa" , "1111111111111111 "  );
                }
            if((i1.getIndexOfLine2() == i2.getIndexOfLine2()) && (!flag)){
             pathLines.add(new PathLine(i1.getPoint() ,i2.getPoint() ,measures[i2.getIndexOfLine2()] , i2.getIndexOfLine2() ));
                plines.add(new PathLine(i1.getPoint() ,i2.getPoint() ,measures[i2.getIndexOfLine2()] , i2.getIndexOfLine2() ));

                flag = true ;
                int ii = pathLines.size()-1;
                Log.i("alaa", "p1 x= " + pathLines.get(ii).getPoint1().getX() +" y = "+ pathLines.get(ii).getPoint1().getY());
                Log.i("alaa", "p2 x= " + pathLines.get(ii).getPoint2().getX() +" y = "+ pathLines.get(ii).getPoint2().getY());
                Log.i("alaa" , "line id = " +pathLines.get(ii).getLineid()  );
               Log.i("alaaaaaaaaa" , "222222222222222 "  );
                  }

                if((i1.getIndexOfLine2() == i2.getIndexOfLine1()) && (!flag)){
                    pathLines.add(new PathLine(i1.getPoint() ,i2.getPoint() ,measures[i2.getIndexOfLine1()] , i2.getIndexOfLine1() ));
                    plines.add(new PathLine(i1.getPoint() ,i2.getPoint() ,measures[i2.getIndexOfLine1()] , i2.getIndexOfLine1() ));
                    int ii = pathLines.size()-1;
                    Log.i("alaa", "p1 x= " + pathLines.get(ii).getPoint1().getX() +" y = "+ pathLines.get(ii).getPoint1().getY());
                    Log.i("alaa", "p2 x= " + pathLines.get(ii).getPoint2().getX() +" y = "+ pathLines.get(ii).getPoint2().getY());
                    Log.i("alaa" , "line id = " +pathLines.get(ii).getLineid()  );
                    Log.i("alaaaaaaaaa" , "33333333333333333 "  );
                    flag = true ;
                }
                if((i1.getIndexOfLine1() == i2.getIndexOfLine2()) && (!flag)){
                    pathLines.add(new PathLine(i1.getPoint() ,i2.getPoint(),measures[i2.getIndexOfLine2()] , i2.getIndexOfLine2() ));
                    plines.add(new PathLine(i1.getPoint() ,i2.getPoint(),measures[i2.getIndexOfLine2()] , i2.getIndexOfLine2() ));
                    int ii = pathLines.size()-1;
                    Log.i("alaa", "p1 x= " + pathLines.get(ii).getPoint1().getX() +" y = "+ pathLines.get(ii).getPoint1().getY());
                    Log.i("alaa", "p2 x= " + pathLines.get(ii).getPoint2().getX() +" y = "+ pathLines.get(ii).getPoint2().getY());
                    Log.i("alaa" , "line id = " +pathLines.get(ii).getLineid()  );
                    Log.i("alaaaaaaaaa" , "44444444444 "  );

                }
            }
        }
    }
      public  void checkDiagnolePath() {
          for (int i = 0; i < pathLines.size(); i++) {
              for (int j = i + 1; j < pathLines.size(); j++) {
                  PointF path1_p1 = pathLines.get(i).getPoint1();
                  PointF path1_p2 = pathLines.get(i).getPoint2();
                  PointF path2_p1 = pathLines.get(j).getPoint1();
                  PointF path2_p2 = pathLines.get(j).getPoint2();
                  float sizePath = pathLines.get(j).getSize() + pathLines.get(i).getSize();
                  boolean flag = false;
                  if ((path1_p1.equals(path2_p1)) && (!flag)) {
                      PathLine p = new PathLine(path1_p2, path2_p2, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          plines.add(p);
                          flag = true;
                      }
                  }
                  if ((path1_p1.equals(path2_p2)) && (!flag)) {
                      PathLine p = new PathLine(path1_p2, path2_p1, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          plines.add(p);
                          flag = true;
                      }
                  }
                  if ((path1_p2.equals(path2_p1)) && (!flag)) {
                      PathLine p = new PathLine(path1_p1, path2_p2, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          plines.add(p);
                          flag = true;
                      }
                  }
                  if ((path1_p2.equals(path2_p2)) && (!flag)) {
                      PathLine p = new PathLine(path1_p1, path2_p1, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          plines.add(p);
                      }
                  }
              }
          }
      }
    public void createResultPath(){

        while (resultPath.size() < pathLines.size()){
            PathLine maxiumPath = plines.get(0);
            int point = 1 ;
            for(int i = 0 ; i < plines.size();i++){
                if(resultPath.size() == 0){
                if(maxiumPath.getSize() < plines.get(i).getSize()){
                    maxiumPath = plines.get(i);

                }
                }
                else{
                    for(int k = 0 ; k < plines.size() ; k++){
                        PointF p1 = plines.get(k).getPoint1();
                        PointF p2 = plines.get(k).getPoint2();
                        if((resultPoints.get(resultPoints.size()-1).equals(p1)) && (!p2.getChecked())){
                            maxiumPath = plines.get(k);
                            point = 1 ;
                            break;
                        }
                        else if ((resultPoints.get(resultPoints.size()-1).equals(p2)) && (!p1.getChecked()) ){
                            maxiumPath = plines.get(k);
                            point = 2 ;
                            break;
                        }
                    }
                    if(maxiumPath.getSize() < plines.get(i).getSize()){
                        PointF p1 = plines.get(i).getPoint1();
                        PointF p2 = plines.get(i).getPoint2();
                        if((resultPoints.get(resultPoints.size()-1).equals(p1))&& (!p2.getChecked())){
                        maxiumPath = plines.get(i);
                        point = 1 ;
                    }
                        else if ((resultPoints.get(resultPoints.size()-1).equals(p2))&& (!p1.getChecked())){
                            maxiumPath = plines.get(i);
                            point = 2 ;
                        }
                        }
                }
            }
            plines.remove(maxiumPath);
            Log.i("alaa" , "size of pline now" + plines.size());
            if(resultPath.size() == 0){
                maxiumPath.getPoint1().setChecked(true);
                maxiumPath.getPoint2().setChecked(true);
                resultPoints.add(maxiumPath.getPoint1());
                resultPoints.add(maxiumPath.getPoint2());

            }else {
                if(point == 1 ) {
                    maxiumPath.getPoint2().setChecked(true);
                    resultPoints.add(maxiumPath.getPoint2());
                }
                if(point == 2) {
                    maxiumPath.getPoint1().setChecked(true);
                    resultPoints.add(maxiumPath.getPoint1());
                }
            }
            Log.i("alaa" , "point1 = " + maxiumPath.getPoint1().getX()  +" p2 = " + maxiumPath.getPoint2().getX() + " size = " +maxiumPath.getSize());
            resultPath.add(maxiumPath);
        }



    }
public boolean checkinDiagnole(ArrayList<PathLine> p , PathLine x){
        for(int i = 0 ; i < p.size() ; i++){
            if(p.get(i).checkexist(x)){
                return true ;
            }
        }
        return false ;
}

    public void setStartPoints(ArrayList<PointF> x){
        startPoints = x ;
    }
    public void setStopPoints(ArrayList<PointF> x){
        stopPoints = x ;
    }
    public void setIntersectPoints(ArrayList<IntersectedPoints> x){
        intersectPoints = x ;
    }
    public void setMeasures(float[] x){
        measures = x ;
    }


}