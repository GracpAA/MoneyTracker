package com.example.gracp.moneytracker;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.gracp.moneytracker.api.Api;


public class AddActivity extends AppCompatActivity {

    public static final String TAG = "MyTag";

    boolean isEnabled = false;

    public final static String EXTRA_TYPE = "type";
    public final static String RESULT_ITEM = "item";
    public final static int RESULT = 100;
    private String type;


    //private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final EditText item = findViewById(R.id.item);
        final EditText cost = findViewById(R.id.cost);
        final ImageButton add = findViewById(R.id.add);
        Toolbar toolbar = findViewById(R.id.toolbar_add);
        type = getIntent().getStringExtra(EXTRA_TYPE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                   Intent result = new Intent();
                   result.putExtra(RESULT_ITEM, new Item(item.getText().toString(),Integer.parseInt(cost.getText().toString()),type));
                   setResult(RESULT_OK,result);
                   finish();
            }
        });

        //itemsIsAdded();

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





 //   private void itemsIsAdded(){
 //       new AddedItem(new Handler(Looper.getMainLooper()){
 //           @Override
 //           public void handleMessage(Message msg) {
 //               switch (msg.what){
 //                   case ITEMS_ADDED:ItemsFragment.items.add((Item) msg.obj); break;
//
//                    case ERROR: showError((String)msg.obj);
//                }
//            }
//        }).start();
//    }

//    private final static int ITEMS_ADDED= 0;
//    private final static int ERROR = 1;



    //   private class AddedItem implements Runnable{

    //    private Handler handler;
    //private Thread thread;

     //   public AddedItem(Handler handler){

     //       thread = new Thread(this);
     //       this.handler = handler;
     //   }

     //   public void start(){
     //       thread.start();
     //   }

     //   @Override
     //   public void run() {
     //      try {
     //           Item addResult = api.add("name",100,"type").execute().body();
     //           handler.obtainMessage(ITEMS_ADDED, addResult).sendToTarget();
     //       } catch (Exception e) {
     //          handler.obtainMessage(ERROR, e.getMessage()).sendToTarget();
     //       }
     //   }
    //}

    //private void showError(String error){
    //    Toast.makeText(AddActivity.this,error,Toast.LENGTH_SHORT).show();
   // }

}
