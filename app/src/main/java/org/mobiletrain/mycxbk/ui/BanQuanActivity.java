package org.mobiletrain.mycxbk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.mobiletrain.mycxbk.R;

public class BanQuanActivity extends AppCompatActivity {
    private ImageView backImageView;
    private ImageView homeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_quan);
        backImageView = (ImageView) findViewById(R.id.activity_ban_quan_back_imageview);
        homeImageView = (ImageView) findViewById(R.id.activity_ban_quan_home_imageview);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BanQuanActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
