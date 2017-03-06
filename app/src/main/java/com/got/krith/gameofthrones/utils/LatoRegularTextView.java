package com.got.krith.gameofthrones.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by krith on 04/03/17.
 */

public class LatoRegularTextView extends TextView {
    public LatoRegularTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public LatoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public LatoRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Lato-Regular.ttf", context);
        setTypeface(customFont);
    }
}
