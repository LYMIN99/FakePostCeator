package com.lymindev.fakepostcreator.view.bottomsheet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.lymindev.fakepostcreator.R;

import java.util.Objects;

public class SetValueBottomSheet {
    private Context context;
    private String s;
    private OnCallBack onCallBack;
    private BottomSheetDialog bsDialogEditName;

    public SetValueBottomSheet(Context context, String s, OnCallBack onCallBack) {
        this.context = context;
        this.s = s;
        this.onCallBack = onCallBack;

        show();
    }
    private void show(){
        @SuppressLint("InflateParams") View view = ((Activity)context).getLayoutInflater().inflate(R.layout.bottom_sheet_editor,null);
        bsDialogEditName = new BottomSheetDialog(context);
        bsDialogEditName.setContentView(view);

        view.findViewById(R.id.btn_cancel).setOnClickListener(view1 -> bsDialogEditName.dismiss());

        final EditText edUserName = view.findViewById(R.id.ed_username);
        edUserName.setHint(s);
        view.findViewById(R.id.btn_save).setOnClickListener(view12 -> {
            if (TextUtils.isEmpty(edUserName.getText().toString())){
                Toast.makeText(context,"Name can't be empty",Toast.LENGTH_SHORT).show();
            } else {
                onCallBack.onSet(edUserName.getText().toString());
                bsDialogEditName.dismiss();
            }
        });


        Objects.requireNonNull(bsDialogEditName.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        bsDialogEditName.setOnDismissListener(dialog -> bsDialogEditName = null);

        bsDialogEditName.show();
    }

    public interface OnCallBack{
        void onSet(String s);
    }
}
