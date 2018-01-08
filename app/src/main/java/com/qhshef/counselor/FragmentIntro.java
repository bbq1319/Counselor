package com.qhshef.counselor;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentIntro extends Fragment{


    public FragmentIntro() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro, container, false);

        // MBTI
        final TextView textMBTI = v.findViewById(R.id.textMBTI);
        final TextView textMBTIContent = v.findViewById(R.id.textMBTIContent);
        textMBTIContent.setVisibility(View.GONE);

        textMBTI.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                if(textMBTIContent.getVisibility() == View.VISIBLE){
                    textMBTIContent.setVisibility(View.GONE);
                    textMBTI.setTextColor(Color.parseColor("#696969"));
                }
                else{
                    textMBTIContent.setVisibility(View.VISIBLE);
                    textMBTI.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        // NEO
        final TextView textNeo = v.findViewById(R.id.textNeo);
        final TextView textNeoContent = v.findViewById(R.id.textNeoContent);
        textNeoContent.setVisibility(View.GONE);

        textNeo.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                if(textNeoContent.getVisibility() == View.VISIBLE){
                    textNeoContent.setVisibility(View.GONE);
                    textNeo.setTextColor(Color.parseColor("#696969"));
                }
                else{
                    textNeoContent.setVisibility(View.VISIBLE);
                    textNeo.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        // TCI
        final TextView textTCI = v.findViewById(R.id.textTCI);
        final TextView textTCIContent = v.findViewById(R.id.textTCIContent);
        textTCIContent.setVisibility(View.GONE);

        v.findViewById(R.id.textTCI).setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                if(textTCIContent.getVisibility() == View.VISIBLE){
                    textTCIContent.setVisibility(View.GONE);
                    textTCI.setTextColor(Color.parseColor("#696969"));
                }
                else{
                    textTCIContent.setVisibility(View.VISIBLE);
                    textTCI.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        // MMPI
        final TextView textMMPI = v.findViewById(R.id.textMMPI);
        final TextView textMMPIContent = v.findViewById(R.id.textMMPIContent);
        textMMPIContent.setVisibility(View.GONE);

        v.findViewById(R.id.textMMPI).setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                if(textMMPIContent.getVisibility() == View.VISIBLE){
                    textMMPIContent.setVisibility(View.GONE);
                    textMMPI.setTextColor(Color.parseColor("#696969"));
                }
                else{
                    textMMPIContent.setVisibility(View.VISIBLE);
                    textMMPI.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        // MLST
        final TextView textMLST = v.findViewById(R.id.textMLST);
        final TextView textMLSTContent = v.findViewById(R.id.textMLSTContent);
        textMLSTContent.setVisibility(View.GONE);

        v.findViewById(R.id.textMLST).setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                if(textMLSTContent.getVisibility() == View.VISIBLE){
                    textMLSTContent.setVisibility(View.GONE);
                    textMLST.setTextColor(Color.parseColor("#696969"));
                }
                else{
                    textMLSTContent.setVisibility(View.VISIBLE);
                    textMLST.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        // UI
        final TextView textUI = v.findViewById(R.id.textUI);
        final TextView textUIContent = v.findViewById(R.id.textUIContent);
        textUIContent.setVisibility(View.GONE);

        v.findViewById(R.id.textUI).setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                if(textUIContent.getVisibility() == View.VISIBLE){
                    textUIContent.setVisibility(View.GONE);
                    textUI.setTextColor(Color.parseColor("#696969"));
                }
                else{
                    textUIContent.setVisibility(View.VISIBLE);
                    textUI.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        // HOLLAND
        final TextView textHOLLAND = v.findViewById(R.id.textHOLLAND);
        final TextView textHOLLANDContent = v.findViewById(R.id.textHOLLANDContent);
        textHOLLANDContent.setVisibility(View.GONE);

        v.findViewById(R.id.textHOLLAND).setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                if(textHOLLANDContent.getVisibility() == View.VISIBLE){
                    textHOLLANDContent.setVisibility(View.GONE);
                    textHOLLAND.setTextColor(Color.parseColor("#696969"));
                }
                else{
                    textHOLLANDContent.setVisibility(View.VISIBLE);
                    textHOLLAND.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        // PAINT
        final TextView textPaint = v.findViewById(R.id.textPaint);
        final TextView textPaintContent = v.findViewById(R.id.textPaintContent);
        textPaintContent.setVisibility(View.GONE);

        v.findViewById(R.id.textPaint).setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                if(textPaintContent.getVisibility() == View.VISIBLE){
                    textPaintContent.setVisibility(View.GONE);
                    textPaint.setTextColor(Color.parseColor("#696969"));
                }
                else{
                    textPaintContent.setVisibility(View.VISIBLE);
                    textPaint.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        // SCT
        final TextView textSCT = v.findViewById(R.id.textSCT);
        final TextView textSCTContent = v.findViewById(R.id.textSCTContent);
        textSCTContent.setVisibility(View.GONE);

        v.findViewById(R.id.textSCT).setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v){
                if(textSCTContent.getVisibility() == View.VISIBLE){
                    textSCTContent.setVisibility(View.GONE);
                    textSCT.setTextColor(Color.parseColor("#696969"));
                }
                else{
                    textSCTContent.setVisibility(View.VISIBLE);
                    textSCT.setTextColor(Color.parseColor("#000000"));
                }
            }
        });

        return v;
        //return inflater.inflate(R.layout.fragment_intro, container, false);
    }

}
