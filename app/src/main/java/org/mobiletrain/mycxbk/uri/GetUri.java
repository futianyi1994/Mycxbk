package org.mobiletrain.mycxbk.uri;

/**
 * Created by 天一 on 2016/10/10.
 */
public class GetUri {

    public static final String TOUTIAO_URI = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines&page=0&row=15";

    public static final String BAIKE_URI = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&page=0&row=15&type=16";

    public static final String ZIXUN_URI = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&page=0&row=15&type=52";

    public static final String JINGYIN_URI = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&page=0&row=15&type=53";

    public static final String SHUJU_URI = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&page=0&row=15&type=54";

    public static final String HEADER_URI = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";

    public static final String SEARCH_URI = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.searcListByTitle&page=1&rows=10&search=%s";

    public static String getSearchUri(String search){
        String searchUri = String.format(SEARCH_URI,search);
        return searchUri;
    }

    public static final String CONTENT_URI = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=%d";
    public static String getContentUri(int id){
        String contentUri = String.format(CONTENT_URI,id);
        return contentUri;
    }
}
