package com.example.start;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
//the final Layout
public class FinalLayoutPath extends View {
    private Paint paint ;
    private float[] measures ;
   private int repeatDrawing = 0 ;
   private int targetIndex = -1 ;
   //add jump height
   private int jumpHeight = 110 ;
    //ArrayList<PointF> resultPoints = new ArrayList<PointF>();
    //private ArrayList<PathLine> resultPath = new ArrayList<PathLine>();
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
        paint.setStrokeWidth(8f);

        Log.i("alaa" , "done for constructor");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (startPoints.size() != 0) {
            for (int i = 0; i < intersectPoints.size(); i++) {
                Log.i("alaaa", " i1 = " + intersectPoints.get(i).getPoint().getX() + "l2 = " + intersectPoints.get(i).getPoint().getY());
                Log.i("alaa", "l1 " + intersectPoints.get(i).getIndexOfLine1() + " l2 = " + intersectPoints.get(i).getIndexOfLine2());
            }
            if (repeatDrawing == 0) {
                checkLinePath();
            }
            Log.i("alaa", "number of paths are " + pathLines.size());
            if (pathLines.size() != 0) {
                if (repeatDrawing == 0) {
                    checkDiagnolePath();
                    checkDiagnoleWallPath();
                    checkPathWallPath();
                   /// createPath();

                    for (int i = 0; i < diagnoleLines.size(); i++) {
                        Log.i("d", "size of diagnoleeee " + diagnoleLines.get(i).getSize() );

                        Log.i("d", "d1 x= " + diagnoleLines.get(i).getPoint1().getX() + " y = " + diagnoleLines.get(i).getPoint1().getY());
                        Log.i("d", "d2 x= " + diagnoleLines.get(i).getPoint2().getX() + " y = " + diagnoleLines.get(i).getPoint2().getY());
                    }
                   // createResultPath();
                }
                for (int i = 0; i < intersectPoints.size(); i++) {

                    String index = Integer.toString(i+1);
                    int d = 20;

                        paint.setTextSize(70);
                        paint.setColor(Color.GREEN);
                        canvas.drawText(index, intersectPoints.get(i).getPoint().getX() + d, intersectPoints.get(i).getPoint().getY(), paint);

                        if(targetIndex == i){
                            paint.setColor(Color.RED);
                        }else {
                            paint.setColor(Color.GRAY);
                        }

                    for (int radius = 0; radius < 41; radius++) {
                        canvas.drawCircle(intersectPoints.get(i).getPoint().getX(), intersectPoints.get(i).getPoint().getY(), radius, paint);
                    }
                    if (createRegion == 0) {
                        regions.add(new Region((int) (intersectPoints.get(i).getPoint().getX() - 40), (int) (intersectPoints.get(i).getPoint().getY() - 40), (int) (intersectPoints.get(i).getPoint().getX() + 40), (int) (intersectPoints.get(i).getPoint().getY() + 40)));
                    }
                    //Log.i("alaa", "the x = " + resultPoints.get(i).getX() + " y = " + resultPoints.get(i).getY());

                }
                repeatDrawing++;
                createRegion += 1;
                targetIndex = -1 ;
                Log.i("alaat", "  the ssssssssssss  " + regions.size());
            }
        }
        for (int i = 0; i < startPoints.size(); i++) {
            paint.setColor(Color.BLUE);
            canvas.drawLine(startPoints.get(i).getX(), startPoints.get(i).getY(), stopPoints.get(i).getX(), stopPoints.get(i).getY(), paint);
        }

        Log.i("alaa", "done for drawwwwwwwwwwwww " + regions.size());
    }

    public void checkLinePath(){
        for(int i = 0 ; i< intersectPoints.size();i++){
            for(int j = i+1 ; j <intersectPoints.size();j++){
                boolean flag = false ;
                IntersectedPoints i1 = intersectPoints.get(i) ;
                IntersectedPoints i2 = intersectPoints.get(j);
                if((i1.getIndexOfLine1() == i2.getIndexOfLine1())&& (!flag)){
                    pathLines.add(new PathLine(i1.getPoint() , i1.getId() ,i2.getPoint(),i2.getId(),measures[i2.getIndexOfLine1()] , i2.getIndexOfLine1() ));
                    plines.add(new PathLine(i1.getPoint() ,i1.getId(),i2.getPoint(),i2.getId(),measures[i2.getIndexOfLine1()] , i2.getIndexOfLine1() ));
                    flag = true ;
                    int ii = pathLines.size()-1;
                    Log.i("alaa", "p1 x= " + pathLines.get(ii).getPoint1().getX() +" y = "+ pathLines.get(ii).getPoint1().getY());
                    Log.i("alaa", "p2 x= " + pathLines.get(ii).getPoint2().getX() +" y = "+ pathLines.get(ii).getPoint2().getY());
                    Log.i("alaa" , "line id = " +pathLines.get(ii).getLineid()  );

                    Log.i("alaaaaaaaaa" , "1111111111111111 "  );
                }
            if((i1.getIndexOfLine2() == i2.getIndexOfLine2()) && (!flag)){
             pathLines.add(new PathLine(i1.getPoint() ,i1.getId() ,i2.getPoint() ,i2.getId(),measures[i2.getIndexOfLine2()] , i2.getIndexOfLine2() ));
                plines.add(new PathLine(i1.getPoint(),i1.getId() ,i2.getPoint(),i2.getId() ,measures[i2.getIndexOfLine2()] , i2.getIndexOfLine2() ));

                flag = true ;
                int ii = pathLines.size()-1;
                Log.i("alaa", "p1 x= " + pathLines.get(ii).getPoint1().getX() +" y = "+ pathLines.get(ii).getPoint1().getY());
                Log.i("alaa", "p2 x= " + pathLines.get(ii).getPoint2().getX() +" y = "+ pathLines.get(ii).getPoint2().getY());
                Log.i("alaa" , "line id = " +pathLines.get(ii).getLineid()  );
               Log.i("alaaaaaaaaa" , "222222222222222 "  );
                  }

                if((i1.getIndexOfLine2() == i2.getIndexOfLine1()) && (!flag)){
                    pathLines.add(new PathLine(i1.getPoint(),i1.getId() ,i2.getPoint(),i2.getId() ,measures[i2.getIndexOfLine1()] , i2.getIndexOfLine1() ));
                    plines.add(new PathLine(i1.getPoint(),i1.getId() ,i2.getPoint(),i2.getId() ,measures[i2.getIndexOfLine1()] , i2.getIndexOfLine1() ));
                    int ii = pathLines.size()-1;
                    Log.i("alaa", "p1 x= " + pathLines.get(ii).getPoint1().getX() +" y = "+ pathLines.get(ii).getPoint1().getY());
                    Log.i("alaa", "p2 x= " + pathLines.get(ii).getPoint2().getX() +" y = "+ pathLines.get(ii).getPoint2().getY());
                    Log.i("alaa" , "line id = " +pathLines.get(ii).getLineid()  );
                    Log.i("alaaaaaaaaa" , "33333333333333333 "  );
                    flag = true ;
                }
                if((i1.getIndexOfLine1() == i2.getIndexOfLine2()) && (!flag)){
                    pathLines.add(new PathLine(i1.getPoint(),i1.getId() ,i2.getPoint(),i2.getId(),measures[i2.getIndexOfLine2()] , i2.getIndexOfLine2() ));
                    plines.add(new PathLine(i1.getPoint(),i1.getId() ,i2.getPoint(),i2.getId(),measures[i2.getIndexOfLine2()] , i2.getIndexOfLine2() ));
                    int ii = pathLines.size()-1;
                    Log.i("alaa", "p1 x= " + pathLines.get(ii).getPoint1().getX() +" y = "+ pathLines.get(ii).getPoint1().getY());
                    Log.i("alaa", "p2 x= " + pathLines.get(ii).getPoint2().getX() +" y = "+ pathLines.get(ii).getPoint2().getY());
                    Log.i("alaa" , "line id = " +pathLines.get(ii).getLineid()  );
                    Log.i("alaaaaaaaaa" , "44444444444 "  );

                }
            }
        }
    }
      public  void checkDiagnolePath() throws NullPointerException {
        if(pathLines.size() > 3){
          for (int i = 0; i < pathLines.size(); i++) {
              for (int j = i + 1; j < pathLines.size(); j++) {
                  PointF path1_p1 = pathLines.get(i).getPoint1();
                  int path1_p1Id = pathLines.get(i).getPoint1ID();
                  PointF path1_p2 = pathLines.get(i).getPoint2();
                  int path1_p2Id = pathLines.get(i).getPoint2ID();
                  PointF path2_p1 = pathLines.get(j).getPoint1();
                  int path2_p1Id = pathLines.get(j).getPoint1ID();
                  PointF path2_p2 = pathLines.get(j).getPoint2();
                  int path2_p2Id = pathLines.get(j).getPoint2ID();
                  float sizePath = pathLines.get(j).getSize() + pathLines.get(i).getSize();
                  boolean flag = false;
                  if ((path1_p1.equals(path2_p1)) && (!flag)) {
                      PathLine p = new PathLine(path1_p2 ,path1_p2Id, path2_p2,path2_p2Id, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          plines.add(p);
                          flag = true;
                      }
                  }
                  if ((path1_p1.equals(path2_p2)) && (!flag)) {
                      PathLine p = new PathLine(path1_p2,path1_p2Id, path2_p1,path2_p1Id, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          plines.add(p);
                          flag = true;
                      }
                  }
                  if ((path1_p2.equals(path2_p1)) && (!flag)) {
                      PathLine p = new PathLine(path1_p1,path1_p1Id, path2_p2,path2_p2Id, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          plines.add(p);
                          flag = true;
                      }
                  }
                  if ((path1_p2.equals(path2_p2)) && (!flag)) {
                      PathLine p = new PathLine(path1_p1,path1_p1Id, path2_p1,path2_p1Id, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          plines.add(p);
                      }
                  }
              }
          }}
      }
    //check distance between targets on the wall and the position of the player
    public void checkPathWallPath(){
        int size = pathLines.size();
        for(int i = 0 ; i < pathLines.size() ; i++){
            PathLine pathWall = pathLines.get(i);
            float new_size = pathWall.getSize() + jumpHeight;
            PathLine p = new PathLine(pathWall.getPoint1() ,pathWall.getPoint1ID() , pathWall.getPoint2() , pathWall.getPoint2ID()+ size , new_size , -2);
            plines.add(p);
            p = new PathLine(pathWall.getPoint1() ,pathWall.getPoint1ID()+ size , pathWall.getPoint2() , pathWall.getPoint2ID() , new_size , -2);
            plines.add(p);
        }
    }
      //check distance between targets on the wall and the position of the player
      public void checkDiagnoleWallPath(){
        int size = pathLines.size();
       for(int i = 0 ; i < diagnoleLines.size() ; i++){
           PathLine diagnole = diagnoleLines.get(i);
           float new_size = diagnole.getSize() + jumpHeight;
           PathLine p = new PathLine(diagnole.getPoint1() ,diagnole.getPoint1ID() , diagnole.getPoint2() , diagnole.getPoint2ID()+ size , new_size , -3);
           plines.add(p);
           p = new PathLine(diagnole.getPoint1() ,diagnole.getPoint1ID()+ size , diagnole.getPoint2() , diagnole.getPoint2ID() , new_size , -3);
           plines.add(p);
       }
      }

      public void createPath(){
          PathLine maxiumPath = plines.get(0); ;
          int chosenTarget = -1 ;
          ArrayList<PathLine> r = new ArrayList<PathLine>();
          //boolean to check if it is first target in the game
        boolean firstTarget = true ;
        while(!(plines.isEmpty())){
            if(firstTarget){

            for(int i = 1 ; i < plines.size() ; i++){
                if(plines.get(i).getSize() > maxiumPath.getSize()){

                    maxiumPath = plines.get(i);
                }
        }
                  chosenTarget = maxiumPath.getPoint2ID();
            Log.i("target " , "t1 = " + maxiumPath.getPoint1ID() + " t2 = " + maxiumPath.getPoint2ID() + " size = " + maxiumPath
            .getSize());
                  plines.remove(maxiumPath);
                  //r.add(maxiumPath);
            firstTarget = false ;
            }else {
                int chosenIndex = -1 ;
                if( chosenTarget > pathLines.size()){
                    chosenTarget = chosenTarget - pathLines.size();
                }
                for(int i = 0 ; i < plines.size() ; i++){
                    if((chosenTarget == plines.get(i).getPoint1ID()) || (chosenTarget == plines.get(i).getPoint2ID())){
                        maxiumPath = plines.get(i);
                        chosenIndex = i ;
                        break;
                    }
                }
                if(chosenIndex != -1) {

                    for (int i = chosenIndex + 1; i < plines.size(); i++) {
                        if ((chosenTarget == plines.get(i).getPoint1ID()) || (chosenTarget == plines.get(i).getPoint2ID())) {

                            if (maxiumPath.getSize() < plines.get(i).getSize()) {
                                maxiumPath = plines.get(i);
                            }

                        }

                    }
                    if (maxiumPath.getPoint1ID() == chosenTarget) {
                        chosenTarget = maxiumPath.getPoint2ID();
                    } else {
                        chosenTarget = maxiumPath.getPoint1ID();

                    }
                    Log.i("target " , "t1 = " + maxiumPath.getPoint1ID() + " t2 = " + maxiumPath.getPoint2ID() + " size = " + maxiumPath
                            .getSize());
                    plines.remove(maxiumPath);
                }else {

                    Log.i("target" , "finished");
                    break;
                }
            }
        }
      }
//    public void createResultPath(){
//
//        while (resultPath.size() < pathLines.size()){
//            PathLine maxiumPath = plines.get(0);
//            int point = 1 ;
//            for(int i = 0 ; i < plines.size();i++){
//                if(resultPath.size() == 0){
//                if(maxiumPath.getSize() < plines.get(i).getSize()){
//                    maxiumPath = plines.get(i);
//
//                }
//                }
//                else{
//                    for(int k = 0 ; k < plines.size() ; k++){
//                        PointF p1 = plines.get(k).getPoint1();
//                        PointF p2 = plines.get(k).getPoint2();
//                        if((resultPoints.get(resultPoints.size()-1).equals(p1)) && (!p2.getChecked())){
//                            maxiumPath = plines.get(k);
//                            point = 1 ;
//                            break;
//                        }
//                        else if ((resultPoints.get(resultPoints.size()-1).equals(p2)) && (!p1.getChecked()) ){
//                            maxiumPath = plines.get(k);
//                            point = 2 ;
//                            break;
//                        }
//                    }
//                    if(maxiumPath.getSize() < plines.get(i).getSize()){
//                        PointF p1 = plines.get(i).getPoint1();
//                        PointF p2 = plines.get(i).getPoint2();
//                        if((resultPoints.get(resultPoints.size()-1).equals(p1))&& (!p2.getChecked())){
//                        maxiumPath = plines.get(i);
//                        point = 1 ;
//                    }
//                        else if ((resultPoints.get(resultPoints.size()-1).equals(p2))&& (!p1.getChecked())){
//                            maxiumPath = plines.get(i);
//                            point = 2 ;
//                        }
//                        }
//                }
//            }
//            plines.remove(maxiumPath);
//            Log.i("alaa" , "size of pline now" + plines.size());
//            if(resultPath.size() == 0){
//                maxiumPath.getPoint1().setChecked(true);
//                maxiumPath.getPoint2().setChecked(true);
//                resultPoints.add(maxiumPath.getPoint1());
//                resultPoints.add(maxiumPath.getPoint2());
//
//            }else {
//                if(point == 1 ) {
//                    maxiumPath.getPoint2().setChecked(true);
//                    resultPoints.add(maxiumPath.getPoint2());
//                }
//                if(point == 2) {
//                    maxiumPath.getPoint1().setChecked(true);
//                    resultPoints.add(maxiumPath.getPoint1());
//                }
//            }
//            Log.i("alaa" , "point1 = " + maxiumPath.getPoint1().getX()  +" p2 = " + maxiumPath.getPoint2().getX() + " size = " +maxiumPath.getSize());
//            resultPath.add(maxiumPath);
//        }
//
//}
    //check if there exist a diagnole with same points but bigger size
public boolean checkinDiagnole(ArrayList<PathLine> p , PathLine d){
        for(int i = 0 ; i < p.size() ; i++){
            if(p.get(i).checkexist(d)){
                PathLine path = p.get(i);
                if(d.getSize() > path.getSize()){
                    diagnoleLines.get(i).setSize(d.getSize());

                }
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
    public ArrayList<Region> getRegions() {
        return regions;
    }
    public int getSize(){
        return pathLines.size();
    }
    //returns array with the resultPoints that used in drawing the canvas
//    public ArrayList<PointF> getResultPoint(){
//        ArrayList<PointF> result = new ArrayList<PointF>();
//        for(int i = 0 ; i < pathLines.size() ; i++){
//           result.add( resultPoints.get(i));
//        }
//        return result;

    public ArrayList<IntersectedPoints> getIntersectPoints() {
        return intersectPoints;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public ArrayList<PointF> getIntersectPointF(){
        ArrayList<PointF> intersectPointF = new ArrayList<PointF>();
        for(int i=0 ; i < intersectPoints.size() ; i++){
           intersectPointF.add(intersectPoints.get(i).getPoint() ) ;
        }
        return  intersectPointF ;
   }
    public ArrayList<PathLine> getPlines() {
        return plines;
    }
    public ArrayList<PointF> getStartPointsPoint(){
        return startPoints;
    }
    public ArrayList<PointF> getStopPointsPoint(){
        return stopPoints;
    }
    public void setTargetIndex(int i){
        targetIndex = i;
        invalidate();

    }
}