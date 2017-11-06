package com.example.gracp.moneytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    public static final String TAG = "MyTag";

    boolean isEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final EditText item = findViewById(R.id.item);
        final EditText cost = findViewById(R.id.cost);
        final ImageButton add = findViewById(R.id.add);

        add.setEnabled(false);

        item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!TextUtils.isEmpty(charSequence)) isEnabled = true;

                else add.setEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!TextUtils.isEmpty(charSequence)&& isEnabled == true) add.setEnabled(true);

                else add.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddActivity.this, R.string.add_text, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
}
