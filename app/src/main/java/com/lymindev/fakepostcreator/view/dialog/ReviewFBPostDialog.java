package com.lymindev.fakepostcreator.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import com.lymindev.fakepostcreator.R;

import java.util.Objects;

public class ReviewFBPostDialog {
    private Context context;
    private Dialog dialog;

    public ReviewFBPostDialog(Context context) {
        this.context = context;
        initialize();
    }
    public void initialize() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR); // before
        dialog.setContentView(R.layout.dialog_review_facebook_post);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);



    }
    public interface OnCallBack{
        void onConfirmClick(String value);
    }
}
