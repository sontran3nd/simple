package com.example.admin.keyboard.Fragments;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.admin.keyboard.Fragments.Dialog.ViewInDialog;
import com.example.admin.keyboard.R;
import com.example.admin.keyboard.Utils.CustomKeyboard;
import com.example.admin.keyboard.Utils.SimpleQwertyKeyboard;
import com.example.admin.keyboard.Utils.TKKeyBoard;

/**
 * Created by Admin on 10/4/2017.
 */

public class ViewFragment extends Fragment {

    private Button btnShow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);

//        setupViews(view);
//        RelativeLayout relativeLayout = new RelativeLayout(getContext());
//        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        relativeLayout.addView(view, p);
//        mCustomKeyboard = new TKKeyBoard(getActivity());
//        RelativeLayout.LayoutParams kbParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        kbParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        kbParams.addRule(RelativeLayout.FOCUSABLE);
//        kbParams.addRule(RelativeLayout.FOCUSABLES_TOUCH_MODE);
//        relativeLayout.addView(mCustomKeyboard.getKeyboard(), kbParams);

        mCustomKeyboard = new SimpleQwertyKeyboard(getActivity(), null, false);

        setupViews(view);
        View viewx = mCustomKeyboard.setUpKeyBoardWithFragment(view);
        EditText editText = view.findViewById(R.id.fragment_view_edt0);
        mCustomKeyboard.registerEditText(editText);

        EditText edt2 = view.findViewById(R.id.fragment_view_edt2);
        mCustomKeyboard.registerEditText(edt2);
        EditText edt4 = view.findViewById(R.id.fragment_view_edt4);
        mCustomKeyboard.registerEditText(edt4);
        return viewx;
    }

    SimpleQwertyKeyboard mCustomKeyboard;
    private void setupViews(View view)
    {
        Button btn = view.findViewById(R.id.fragment_view_btn_show);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewInDialog viewDialog = new ViewInDialog();
//                viewDialog.simpleQwertyKeyboards = mCustomKeyboard;
                viewDialog.show(getFragmentManager(), "");

            }
        });
    }

}
