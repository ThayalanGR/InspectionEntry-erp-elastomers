package com.siva;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.paperdb.Paper;

public class URLActivity extends AppCompatActivity {

    EditText mText;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);

        mText=(EditText)findViewById(R.id.editText);
        mButton=(Button)findViewById(R.id.button);
        Paper.init(this);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mText.getText().toString())){
                    Paper.book().write("url",mText.getText().toString());
                }
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
