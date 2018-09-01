package org.mobiletrain.mycxbk.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobiletrain.mycxbk.R;
import org.mobiletrain.mycxbk.bean.TTEntity;
import org.mobiletrain.mycxbk.database.DatabasedeHelper;
import org.mobiletrain.mycxbk.uri.GetUri;
import org.mobiletrain.mycxbk.uri.MyHttpUtils;

public class ContentActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView autorTextView;
    private WebView webView;
    private ImageView backImageView;
    private ImageView shoucangImageView;
    private TTEntity.Data data;

    private int id;
    private String wap_thumb;

    //数据库助手
    private DatabasedeHelper helper;
    // 通过数据库助手，创建数据库对象,添加数据
    private SQLiteDatabase db;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        init();

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        wap_thumb = intent.getStringExtra("wap_thumb");
        loadData();

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shoucangImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (values==null){
                    db.insert(DatabasedeHelper.TABLE_NAME,null,values);
                    Toast.makeText(ContentActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    db.close();
                } else {
                    Toast.makeText(ContentActivity.this, "请勿重复收藏", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //加载数据
    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String titleJsonString = MyHttpUtils.getStringFromUrl(GetUri.getContentUri(id));
                try {
                    JSONObject jsonObject = new JSONObject(titleJsonString);
                    jsonObject = jsonObject.optJSONObject("data");
                    data = new TTEntity().new Data(jsonObject);
                    ContentActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titleTextView.setText(data.getTitle());
                            autorTextView.setText("创建时间："+data.getCreate_time()+"  "+"来源："+data.getSource());
                            webView.loadDataWithBaseURL(null,data.getWap_content(),"text/html","UTF-8",null);

                            //初始化数据库
                            helper = new DatabasedeHelper(ContentActivity.this);
                            db = helper.getReadableDatabase();
                            values = new ContentValues();
                            values.put("title", data.getTitle());
                            values.put("create_time",data.getCreate_time());
                            values.put("source",data.getSource());
                            values.put("wap_thumb",wap_thumb);
                            values.put("id",id);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void init() {
        titleTextView = (TextView) findViewById(R.id.contentactivity_title_textview);
        autorTextView = (TextView) findViewById(R.id.contentactivity_autor_textview);
        webView = (WebView) findViewById(R.id.contentactivity_webview);
        backImageView = (ImageView) findViewById(R.id.contentactivity_back_imageview);
        shoucangImageView = (ImageView) findViewById(R.id.contentactivity_shoucang_imageview);

    }
}
