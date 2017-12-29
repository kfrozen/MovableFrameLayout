package com.troy.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.layout_1).setOnClickListener(this);

        findViewById(R.id.layout_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.layout_1)
        {
            Toast.makeText(MainActivity.this, "Block A Clicked", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.layout_2)
        {
            Toast.makeText(MainActivity.this, "Block B Clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
