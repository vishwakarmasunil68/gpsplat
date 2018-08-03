package com.voxgps.app.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class ProximaNovaLightTextView extends TextView {

    public ProximaNovaLightTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ProximaNovaLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProximaNovaLightTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/proxima_nova_light.otf");
        setTypeface(tf ,1);

    }
}