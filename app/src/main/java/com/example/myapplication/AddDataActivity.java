package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddDataActivity extends AppCompatActivity {
    private int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        Intent intent = getIntent();
        if (intent != null) {
            String key = intent.getStringExtra("key");

            if (null != key) {
                EditText editText = findViewById(R.id.editTextText1);
                position = intent.getIntExtra("position",-1);
                editText.setText(key);
            }
        }
        Button button_ok = findViewById(R.id.button_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText editText1 = findViewById(R.id.editTextText1);
                intent.putExtra("key", editText1.getText().toString());
                intent.putExtra("position",position);
                setResult(Activity.RESULT_OK, intent);
                AddDataActivity.this.finish();
            }
        });
    }
}