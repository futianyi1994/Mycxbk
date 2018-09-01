package org.mobiletrain.mycxbk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.mobiletrain.mycxbk.R;

public class YiJianActivity extends AppCompatActivity {
    private ImageView backImageView;
    private ImageView homeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_jian);
        backImageView = (ImageView) findViewById(R.id.activity_yi_jian_back_imageview);
        homeImageView = (ImageView) findViewById(R.id.activity_yi_jian_home_imageview);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YiJianActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
