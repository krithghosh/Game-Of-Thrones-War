package com.got.krith.gameofthrones.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by krith on 05/03/17.
 */

public class LatoBoldTextView extends TextView {
    public LatoBoldTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public LatoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public LatoBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Lato-Bold.ttf", context);
        setTypeface(customFont);
    }
}