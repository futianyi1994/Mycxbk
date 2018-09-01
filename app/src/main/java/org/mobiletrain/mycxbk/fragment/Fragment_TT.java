package org.mobiletrain.mycxbk.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobiletrain.mycxbk.R;
import org.mobiletrain.mycxbk.adapter.TTAdapter;
import org.mobiletrain.mycxbk.adapter.TTHeaderPagerAdapter;
import org.mobiletrain.mycxbk.bean.TTEntity;
import org.mobiletrain.mycxbk.bean.TTHeaderEntity;
import org.mobiletrain.mycxbk.ui.ContentActivity;
import org.mobiletrain.mycxbk.uri.GetUri;
import org.mobiletrain.mycxbk.uri.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 天一 on 2016/10/10.
 */
public class Fragment_TT extends BaseFragment {

    private SwipeRefreshLayout swipeRefresh;

    private String uri;
    private ListView listView;
    private TTAdapter ttAdapter;

    //头条数据实体类集合
    private List<TTEntity.Data> entityDatas;
    //头条头布局数据实体类
    private List<TTHeaderEntity.Data> headerEntityDatas;

    private View headerView;
    private ViewPager viewPager;
    private LinearLayout linearLayout;

    private List<Bitmap> bitmapList;
    //点数组
    private int[] pointIds = new int[]{R.id.fragment_tt_header_point1_imageviewId, R.id.fragment_tt_header_point2_imageviewId, R.id.fragment_tt_header_point3_imageviewId};
    private TTHeaderPagerAdapter ttHeaderPagerAdapter;
    List<ImageView> imgList;
    List<ImageView> pointList;

    public Fragment_TT() {
    }

    public static Fragment_TT newInstance(String uri) {

        Bundle args = new Bundle();
        args.putString("uri", uri);
        Fragment_TT fragment = new Fragment_TT();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        uri = bundle.getString("uri");

        imgList = new ArrayList<>();
        pointList = new ArrayList<>();
        bitmapList = new ArrayList<>();
        entityDatas = new ArrayList<>();
        headerEntityDatas = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_tt, container, false);
        //查找控件
        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_fragment_tt_swiperefreshId);
        listView = (ListView) rootView.findViewById(R.id.fragment_tt_listview);
        //实例化适配器
        ttAdapter = new TTAdapter(getContext());
        //判断是否为第一个fragment
        if (uri.equals(GetUri.TOUTIAO_URI)) {
            //添加头布局
            headerView = inflater.inflate(R.layout.fragment_tt_litview_header, listView, false);
            //初始化头布局
            initHeaderView();
            //添加头布局
            listView.addHeaderView(headerView);
        }

        //设置适配器
        listView.setAdapter(ttAdapter);
        //设置刷新样式
        swipeRefresh.setColorSchemeColors(new int[]{Color.GREEN, Color.GRAY, Color.DKGRAY});
        //设置刷新监听
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ttAdapter.clear();
                loadData(uri);
            }
        });

        //加载数据
        loadData(uri);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                if (uri.equals(GetUri.TOUTIAO_URI)) {
                    position -= 1;
                }
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra("id", entityDatas.get(position).getId());
                intent.putExtra("wap_thumb", entityDatas.get(position).getWap_thumb());
                startActivity(intent);
            }
        });
        return rootView;
    }

    //初始化头布局
    private void initHeaderView() {
        //查找头布局控件
        viewPager = (ViewPager) headerView.findViewById(R.id.fragment_tt_header_viewpagerId);
        linearLayout = (LinearLayout) headerView.findViewById(R.id.fragment_tt_header_linearlayoutId);
        //下载头布局图片
        loadeHeaderImage();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectionIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //设置滑动改变点
    private void selectionIndicator(int position) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if (position == i) {
                imageView.setImageResource(R.drawable.dot);
            } else {
                imageView.setImageResource(R.drawable.dot_1);
            }
        }
    }

    //下载头布局中的图片
    private void loadeHeaderImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ttHeaderJsonString = MyHttpUtils.getStringFromUrl(GetUri.HEADER_URI);
                try {
                    JSONObject jsonObject = new JSONObject(ttHeaderJsonString);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.optJSONObject(i);
                        TTHeaderEntity.Data headerEntityData = new TTHeaderEntity().new Data(jsonObject);
                        headerEntityDatas.add(headerEntityData);
                        String imageUri = headerEntityDatas.get(i).getImage();
                        Bitmap bitmap = MyHttpUtils.getBitmapFromUrl(imageUri);
                        bitmapList.add(bitmap);

                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < bitmapList.size(); i++) {
                                //初始化头布局图片
                                ImageView imageView = new ImageView(getContext());
                                imageView.setImageBitmap(bitmapList.get(i));
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                imgList.add(imageView);
                            }

                            //设置默认选中的点
                            ImageView pointImageView = (ImageView) linearLayout.getChildAt(0);
                            pointImageView.setImageResource(R.drawable.dot);
                            //实例化viewpager适配器
                            ttHeaderPagerAdapter = new TTHeaderPagerAdapter(imgList);
                            viewPager.setAdapter(ttHeaderPagerAdapter);
                            viewPager.setCurrentItem(0);
                            viewPager.setOffscreenPageLimit(3);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //加载fragment数据
    private void loadData(final String uri) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ttJsonString = MyHttpUtils.getStringFromUrl(uri);
                try {
                    JSONObject jsonObject = new JSONObject(ttJsonString);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.optJSONObject(i);
                        TTEntity.Data entityData = new TTEntity().new Data(jsonObject);
                        entityDatas.add(entityData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ttAdapter.addAll(entityDatas);
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
