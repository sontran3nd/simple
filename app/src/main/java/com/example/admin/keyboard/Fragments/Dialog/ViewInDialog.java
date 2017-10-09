package com.example.admin.keyboard.Fragments.Dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.admin.keyboard.R;
import com.example.admin.keyboard.Utils.SimpleQwertyKeyboard;

/**
 * Created by Admin on 10/6/2017.
 */

public class ViewInDialog extends BaseDialogFragment {

    private static ViewInDialog inDialog;
    public SimpleQwertyKeyboard simpleQwertyKeyboards;

    public ViewInDialog instance(ViewInDialog minDialog, SimpleQwertyKeyboard RsimpleQwertyKeyboard){
        if (inDialog == null)
        {
            inDialog = minDialog;
        }
        inDialog.simpleQwertyKeyboards = RsimpleQwertyKeyboard;
        return inDialog;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_view, container, false);
        String sample = getActivity().getClass().getName();
        System.out.println(sample);

        simpleQwertyKeyboards = new SimpleQwertyKeyboard(getActivity(), getDialog(), true);

     //   this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager manager = getActivity().getWindowManager();

        if (manager != null) {
            manager.getDefaultDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
//            rootView.setMinimumWidth((int) (width * 0.90))
            rootView.setMinimumWidth(width);
            rootView.setMinimumHeight(height);
        }

        View viewx = simpleQwertyKeyboards.setUpKeyBoardWithFragment(rootView);
        EditText edt0 = rootView.findViewById(R.id.fragment_dialog_view_edt0);
        simpleQwertyKeyboards.registerEditText(edt0);
        return viewx;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        System.out.println("1");
        return super.onCreateDialog(savedInstanceState);
    }
}
