package com.example.bustest8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ChosePlace = (Button) findViewById(R.id.ChosePlace);
        Button ChosePlace2 = (Button) findViewById(R.id.ChosePlace2);
        Button SearchBtn = (Button) findViewById(R.id.SearchBtn);
        TextView textView3 = (TextView) findViewById(R.id.TextView3);


        ChosePlace.setOnClickListener(mapListener);
        ChosePlace2.setOnClickListener(mapListener2);
        SearchBtn.setOnClickListener(FinalListener);
    }

    private Button.OnClickListener mapListener = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent1 = new Intent();
            intent1.setClass(MainActivity.this, MapsActivity.class);
            startActivity(intent1);
        }
    };

    private Button.OnClickListener mapListener2 = new Button.OnClickListener() {
        public void onClick(View view){
            Intent intent2 = new Intent();
            intent2.setClass(MainActivity.this, MapsActivity.class);
            startActivity(intent2);
        }
    };




    private Button.OnClickListener FinalListener = new Button.OnClickListener(){
        public void onClick(View view){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SearchResult.class);

            startActivity(intent);
        }
    };


}

