package com.wangyang.web.vo;

public class Weather {
	private String City;
	private String Aqi;
	private String Direct;
	private String Futureweather;
	private String Humidity;
	private String Info;
	private String Power;
	private String Temperature;
	private String WeatherImgUrl;
	
	
	
	public Weather() {
		super();
	}
	
	public Weather(String city, String aqi, String direct, String futureweather, String humidity, String info,
			String power, String temperature, String weatherImgUrl) {
		super();
		City = city;
		Aqi = aqi;
		Direct = direct;
		Futureweather = futureweather;
		Humidity = humidity;
		Info = info;
		Power = power;
		Temperature = temperature;
		WeatherImgUrl = weatherImgUrl;
	}

	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getAqi() {
		return Aqi;
	}
	public void setAqi(String aqi) {
		Aqi = aqi;
	}
	public String getDirect() {
		return Direct;
	}
	public void setDirect(String direct) {
		Direct = direct;
	}
	public String getFutureweather() {
		return Futureweather;
	}
	public void setFutureweather(String futureweather) {
		Futureweather = futureweather;
	}
	public String getHumidity() {
		return Humidity;
	}
	public void setHumidity(String humidity) {
		Humidity = humidity;
	}
	public String getInfo() {
		return Info;
	}
	public void setInfo(String info) {
		Info = info;
	}
	public String getPower() {
		return Power;
	}
	public void setPower(String power) {
		Power = power;
	}
	public String getTemperature() {
		return Temperature;
	}
	public void setTemperature(String temperature) {
		Temperature = temperature;
	}
	public String getWeatherImgUrl() {
		return WeatherImgUrl;
	}
	public void setWeatherImgUrl(String weatherImgUrl) {
		WeatherImgUrl = weatherImgUrl;
	}

	@Override
	public String toString() {
		return "Weather [City=" + City + ", Aqi=" + Aqi + ", Direct=" + Direct + ", Futureweather=" + Futureweather
				+ ", Humidity=" + Humidity + ", Info=" + Info + ", Power=" + Power + ", Temperature=" + Temperature
				+ ", WeatherImgUrl=" + WeatherImgUrl + "]";
	}
	
	

}
