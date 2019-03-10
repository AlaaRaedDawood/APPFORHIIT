package com.example.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class DrawLayoutActivity extends AppCompatActivity {
     ArrayList<Float>  xstart ;
    ArrayList<Float>  ystart ;
    ArrayList<Float>  xstop ;
    ArrayList<Float>  ystop ;
     Button button_next ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_layout);
        LayoutCanvas canvas = (LayoutCanvas) findViewById(R.id.draw_canvas);

        button_next = (Button) findViewById(R.id.button_layout_next);
         button_next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                LayoutCanvas canvas = (LayoutCanvas) findViewById(R.id.draw_canvas);
                xstart =  canvas.getXstart();
                ystart = canvas.getYstart();
                xstop = canvas.getXstop();
                ystop = canvas.getYstop();
               // Log.i("alaa", "ssizzee of x is " + xstart.size());
                if(xstart.size() !=  0) {
                    Intent intent = new Intent(DrawLayoutActivity.this, LayOutForMeasuresActivity.class);
                 intent.putExtra("Arrayxstart", xstart);
                    intent.putExtra("Arrayystart", ystart);
                    intent.putExtra("Arrayxstop", xstop);
                    intent.putExtra("Arrayystop", ystop);

                    startActivity(intent);
                    //Log.i("alaa", "ssizzee of x is " + xstart.size());
                }
            }
        });





    }

}
