package com.everypet.everypet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.everypet.everypet.R;

public class CustomDialog extends Dialog {
    private Button positiveButton;
    private Button negativeButton;

    private View.OnClickListener positiveListener;
    private View.OnClickListener negativeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_photo_dialog);

        positiveButton = findViewById(R.id.button_recommend);
        negativeButton = findViewById(R.id.button_back);

        positiveButton.setOnClickListener(positiveListener);
        negativeButton.setOnClickListener(negativeListener);
    }

    public CustomDialog(@NonNull Context context, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        super(context);
        this.positiveListener = positiveListener;
        this.negativeListener = negativeListener;
    }
}
