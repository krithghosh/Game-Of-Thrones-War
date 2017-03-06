package com.got.krith.gameofthrones.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by krith on 04/03/17.
 */

public class SnackbarUtils {
    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}