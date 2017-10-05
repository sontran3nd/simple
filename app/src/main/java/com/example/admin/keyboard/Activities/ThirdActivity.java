package com.example.admin.keyboard.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.keyboard.R;
import com.example.admin.keyboard.Utils.TKKeyBoard;

public class ThirdActivity extends AppCompatActivity {

    private TKKeyBoard tkKeyBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

//        tkKeyBoard = new TKKeyBoard(this);
//        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
//        tkKeyBoard.setUpTKKeyBoard(viewGroup);
//
//        EditText edt = (EditText) findViewById(R.id.activity_third_edt_sample);
//        tkKeyBoard.registerEditText(edt);
    }

}
