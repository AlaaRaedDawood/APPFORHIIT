package com.example.start;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class targetPosition extends View {
    private Paint paint ;
    private int targetIndex = -1 ;
    private int targetSize = -1 ;
    private boolean showTarget = false;
    public targetPosition(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        Log.i("alaa" , "done for constructor");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(showTarget){
        canvas.drawLine(506, 180, 506, 600, paint);
        canvas.drawLine(506, 180, 793, 273, paint);
        canvas.drawLine(793, 273, 781, 739, paint);
        canvas.drawLine(506, 600, 781, 739, paint);
        canvas.drawLine(506, 600, 781, 739, paint);
        canvas.drawLine(506, 600, 231, 739, paint);
        canvas.drawLine(506, 180, 219, 273, paint);
        canvas.drawLine(231, 739, 219, 273, paint);
        paint.setColor(Color.BLUE);


        for (int i = 1; i < 50; i += 5) {
            canvas.drawCircle(506, 250, i, paint);
        }

        for (int i = 1; i < 50; i += 5) {
            canvas.drawCircle(506, 550, i, paint);
        }
        paint.setColor(Color.GREEN);
            paint.setTextSize(80);
        canvas.drawText("5 cm", 506 , 400, paint);
            paint.setTextSize(50);
            if((targetIndex != -1) && (targetSize != 0)) {

                canvas.drawText(Integer.toString(targetIndex), 506, 250, paint);
                canvas.drawText(Integer.toString(targetIndex + targetSize), 506, 550, paint);
            }
        canvas.drawRect((float) 456, (float) 500, (float) 556, (float) 600, paint);
        canvas.drawRect((float) 456, (float) 200, (float) 556, (float) 300, paint);
    }}
    public void setShowTarget(int index , int regionSize){
        showTarget = true ;
        targetIndex = index ;
        targetSize = regionSize ;
        invalidate();
    }
}

