package com.example.admin.keyboard;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.keyboard.Activities.SecondActivity;
import com.example.admin.keyboard.Activities.ThirdActivity;
import com.example.admin.keyboard.Utils.CustomKeyboard;
import com.example.admin.keyboard.View.KeyBoardOne;
import com.example.simple.MyView;

public class MainActivity extends AppCompatActivity {

    CustomKeyboard mCustomKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Create the Keyboard
        mCustomKeyboard= new CustomKeyboard(this, R.id.keyboardview, R.xml.kbx);

        final EditText edt0 = (EditText) findViewById(R.id.edittext0);
        mCustomKeyboard.registerEditText(R.id.edittext0);
        mCustomKeyboard.registerEditText(R.id.edittext1);
        mCustomKeyboard.registerEditText(R.id.edittext2);
        mCustomKeyboard.registerEditText(R.id.edittext3);
        mCustomKeyboard.registerEditText(R.id.edittext4);

        Button btn = (Button) findViewById(R.id.btnsubmit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        Button btnTxt = (Button) findViewById(R.id.btntxt);
        btnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentx = new Intent(getApplicationContext(), ThirdActivity.class);
                startActivity(intentx);
            }
        });
    }

    @Override public void onBackPressed() {
        // NOTE Trap the back key: when the CustomKeyboard is still visible hide it, only when it is invisible, finish activity
        if( mCustomKeyboard.isCustomKeyboardVisible() ) mCustomKeyboard.hideCustomKeyboard(); else this.finish();
    }
}
