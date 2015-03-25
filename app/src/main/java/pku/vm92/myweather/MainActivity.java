package pku.vm92.myweather;

import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.zip.GZIPInputStream;

import pku.vm92.bean.TodayWeather;
import pku.vm92.util.NetUtil;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private ImageView mUpdateBtn;
    private TextView cityTv, timeTv, humidityTv, weekTv, pmTv, qualityTv, temperatureTv, typeTv, windTv;
    private ImageView weatherImg, pmImg;
    private static final int UPDATE_TODAY_WEATHER = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather)msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    void initView()
    {
        cityTv = (TextView)findViewById(R.id.city);
        timeTv = (TextView)findViewById(R.id.time);
        humidityTv = (TextView)findViewById(R.id.humidity);
        weekTv = (TextView)findViewById(R.id.week_today);
        pmTv = (TextView)findViewById(R.id.pmdata);
        qualityTv = (TextView)findViewById(R.id.pm2_5quality);
        temperatureTv = (TextView)findViewById(R.id.tem);
        typeTv = (TextView)findViewById(R.id.climate);
        windTv = (TextView)findViewById(R.id.wind);
        weatherImg = (ImageView)findViewById(R.id.weather_img);
        pmImg = (ImageView)findViewById(R.id.pm2_5img);

        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        weekTv.setText("N/A");
        pmTv.setText("N/A");
        qualityTv.setText("N/A");
        temperatureTv.setText("N/A");
        typeTv.setText("N/A");
        windTv.setText("N/A");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);
        mUpdateBtn = (ImageView)findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);

        initView();

    }

    public void updateTodayWeather(TodayWeather td)
    {
        cityTv.setText(td.getCity());
        timeTv.setText(td.getUpdatetime() + "发布");
        humidityTv.setText("湿度：" + td.getHumidity());
        pmTv.setText(td.getPm25());
        qualityTv.setText(td.getQuality());
        weekTv.setText(td.getDate());
        temperatureTv.setText(td.getLow() + "~" + td.getHigh());
        typeTv.setText(td.getType());
        windTv.setText("风力：" + td.getWind());

        int pm = Integer.parseInt(td.getPm25());
        if (pm >= 300)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_greater_300);
        else if (pm > 200)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_201_300);
        else if (pm > 150)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_151_200);
        else if (pm > 100)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_101_150);
        else if (pm > 50)
            pmImg.setImageResource(R.drawable.biz_plugin_weather_51_100);

        String wea = td.getType();
        if (wea.equals("暴雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
        else if (wea.equals("暴雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoyu);
        else if (wea.equals("大暴雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
        else if (wea.equals("大雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_daxue);
        else if (wea.equals("大雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_dayu);
        else if (wea.equals("多云"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_duoyun);
        else if (wea.equals("雷阵雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
        else if (wea.equals("雷阵雨冰雹"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
        else if (wea.equals("沙尘暴"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
        else if (wea.equals("特大暴雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
        else if (wea.equals("雾"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_wu);
        else if (wea.equals("小雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
        else if (wea.equals("小雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
        else if (wea.equals("阴"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_yin);
        else if (wea.equals("雨夹雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
        else if (wea.equals("阵雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
        else if (wea.equals("阵雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
        else if (wea.equals("中雪"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
        else if (wea.equals("中雨"))
            weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongyu);



        Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view)
    {
        if (view.getId() == R.id.title_update_btn)
        {
            SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
            String citycode = sp.getString("main_city_code", "101010100");
            Log.d("myweather", citycode);
            if (NetUtil.getNetwokeState(this) != NetUtil.NETWORN_NONE)
            {
                Log.d("mtweather", "网络连通");
                queryWeatherCode(citycode);
            }
            else
            {
                Log.d("mtweather", "网络不通");
                Toast.makeText(MainActivity.this, "网络不通", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void queryWeatherCode(String cc) {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cc;
        Log.d("myweather", address);

        new Thread( new Runnable() {
            @Override
            public void run() {
                try{
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet httpget = new HttpGet(address);
                    HttpResponse httpresponse = httpclient.execute(httpget);
                    if (httpresponse.getStatusLine().getStatusCode() == 200)
                    {
                        HttpEntity entity = httpresponse.getEntity();
                        InputStream responseStream = entity.getContent();
                        responseStream = new GZIPInputStream(responseStream);

                        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
                        StringBuilder response = new StringBuilder();
                        String str;
                        while((str = reader.readLine()) != null)
                        {
                            response.append(str);
                        }
                        String responseStr = response.toString();
                        Log.d("myweather",responseStr);
                        TodayWeather todayweather = null;
                        todayweather = parseXML(responseStr);
                        if (todayweather != null)
                        {
                            //Log.d("myweather", todayweather.toString());
                            Message msg = new Message();
                            msg.what = UPDATE_TODAY_WEATHER;
                            msg.obj = todayweather;
                            mHandler.sendMessage(msg);
                        }
                        else
                            Log.d("myweather", "empty");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        ).start();
    }

    private TodayWeather parseXML(String XMLdata) {
        TodayWeather todayweather = null;
        try {
            int fengxiangCount = 0;
            int windCount = 0;
            int dateCount = 0;
            int highCount = 0;
            int lowCount = 0;
            int typeCount = 0;

            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParse = fac.newPullParser();
            xmlPullParse.setInput(new StringReader(XMLdata));
            int eventType = xmlPullParse.getEventType();
            Log.d("myweather", "parsing");
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                switch(eventType)
                {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (xmlPullParse.getName().equals("resp"))
                            todayweather = new TodayWeather();
                        if (todayweather != null)
                        {
                            if (xmlPullParse.getName().equals("city"))
                            {
                                eventType = xmlPullParse.next();
                                todayweather.setCity(xmlPullParse.getText());
                            }
                            else if (xmlPullParse.getName().equals("updatetime"))
                            {
                                eventType = xmlPullParse.next();
                                todayweather.setUpdatetime(xmlPullParse.getText());
                            }
                            else if (xmlPullParse.getName().equals("shidu"))
                            {
                                eventType = xmlPullParse.next();
                                todayweather.setHumidity(xmlPullParse.getText());
                            }
                            else if (xmlPullParse.getName().equals("wendu"))
                            {
                                eventType = xmlPullParse.next();
                                todayweather.setTemperature(xmlPullParse.getText());
                            }
                            else if (xmlPullParse.getName().equals("pm25"))
                            {
                                eventType = xmlPullParse.next();
                                todayweather.setPm25(xmlPullParse.getText());
                            }
                            else if (xmlPullParse.getName().equals("quality"))
                            {
                                eventType = xmlPullParse.next();
                                todayweather.setQuality(xmlPullParse.getText());
                            }
                            else if (xmlPullParse.getName().equals("fengxiang") && fengxiangCount == 0)
                            {
                                eventType = xmlPullParse.next();
                                todayweather.setFengxiang(xmlPullParse.getText());
                                fengxiangCount++;
                            }
                            else if (xmlPullParse.getName().equals("fengli") && windCount == 0)
                            {
                                eventType = xmlPullParse.next();
                                todayweather.setWind(xmlPullParse.getText());
                                windCount++;
                            }
                            else if (xmlPullParse.getName().equals("date") && dateCount == 0)
                            {
                                eventType = xmlPullParse.next();
                                todayweather.setDate(xmlPullParse.getText());
                                dateCount++;
                            }
                            else if (xmlPullParse.getName().equals("high") && highCount == 0)
                            {
                                String sub = null;
                                eventType = xmlPullParse.next();
                                sub = xmlPullParse.getText().replaceAll("\\s|[\u4e00-\u9fa5]+", "");
                                todayweather.setHigh(sub);
                                highCount++;
                            }
                            else if (xmlPullParse.getName().equals("low") && lowCount == 0)
                            {
                                String sub = null;
                                eventType = xmlPullParse.next();
                                sub = xmlPullParse.getText().replaceAll("\\s|[\u4e00-\u9fa5]+", "");

                                todayweather.setLow(sub);
                                lowCount++;
                            }
                            else if (xmlPullParse.getName().equals("type") && typeCount == 0)
                            {
                                eventType = xmlPullParse.next();
                                todayweather.setType(xmlPullParse.getText());
                                typeCount++;
                            }
                        }
                    break;
                }
                eventType = xmlPullParse.next();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return todayweather;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
