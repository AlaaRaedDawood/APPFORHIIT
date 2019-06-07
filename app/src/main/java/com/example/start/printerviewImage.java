package com.example.start;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class printerviewImage extends Fragment {

   // private TextView txtTop;
    private ImageView imagetargetView ;
    private imagetarget imagetarget ;
    printerviewImage.viewSectionListener activityCommander;

    public interface viewSectionListener{
        public void backClick();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            activityCommander = ( printerviewImage.viewSectionListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_image, container, false);
        imagetargetView = (ImageView) view.findViewById(R.id.imagetarget);
        imagetargetView.setImageDrawable(getActivity().getResources().getDrawable(imagetarget.getImage()));
        //txtTop.setText(textt);
        final Button button1 = (Button)view.findViewById(R.id.buttonb);
        // final Button button2 = (Button)view.findViewById(R.id.button2);
        button1.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        backbuttonClicked(v);

                    }
                }
        );
        return view;
    }

    public void setClickedText(imagetarget image){
        //txtTop = (TextView) findViewById(R.id.texto);
        imagetarget = image ;

    }
    public void backbuttonClicked(View view){
        activityCommander.backClick();
    }






}
