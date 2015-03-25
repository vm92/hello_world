package pku.vm92.bean;

/**
 * Created by HP-54441S on 2015/3/25.
 */
public class TodayWeather {
    private String city;
    private String updatetime;
    private String temperature;
    private String humidity;
    private String pm25;
    private String quality;
    private String fengxiang;
    private String wind;
    private String date;
    private String high;
    private String low;
    private String type;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString()
    {
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ",updatetime='" + updatetime + '\'' +
                ",wendu='" + temperature + '\'' +
                ",shidu='" + humidity + '\'' +
                ",pm25='" + pm25 + '\'' +
                ",quality='" + quality + '\'' +
                ",fengxiang='" + fengxiang + '\'' +
                ",fengli='" + wind + '\'' +
                ",date='" + date + '\'' +
                ",high='" + high + '\'' +
                ",low='" + low + '\'' +
                ",type='" + type + '\'' +
                "}";
    }
}
