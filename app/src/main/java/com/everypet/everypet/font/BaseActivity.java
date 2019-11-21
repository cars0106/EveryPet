package com.everypet.everypet.font;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    public Typeface mTypeface=null;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (mTypeface == null)
            mTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/komacon.ttf");

        setGlobalFont(getWindow().getDecorView());
    }

    void setGlobalFont(View view) {
        if(view != null){
            if(view instanceof ViewGroup){
                ViewGroup vg=(ViewGroup) view;
                int vgCnt = vg.getChildCount();
                for(int i=0;i<vgCnt;i++){
                    View v = vg.getChildAt(i);
                    if(v instanceof TextView){
                        ((TextView) v).setTypeface(mTypeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }
}
