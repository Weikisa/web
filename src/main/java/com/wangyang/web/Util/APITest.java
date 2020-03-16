package com.wangyang.web.Util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class APITest {

	public static void main(String[] args) {
		String ipAddress = "223.104.212.171";
		String city = getCityByIP(ipAddress);
		System.out.println("获取到城市名称："+city);
		Map<String,Object> weather = getWeatherByCity(city);
		
		System.out.println(weather.toString());
	}
	
	
	//根据IP获取所在城市的名称
		private static String getCityByIP(String IP) {
			String city ="";
			String jsonStr = "";
			String strUrl = "http://apis.juhe.cn/ip/ipNew";
			String srtVlaue = "ip="+IP+"&key=022287affefd4e498740d2d107efbe4c";
			jsonStr = HttpGetPost.sendGet(strUrl,srtVlaue);
			System.out.println(jsonStr);
			if(jsonStr.equals("请求超时")) {
				return "获取城市名称失败";
			}
			JSONObject json = JSONObject.fromObject(jsonStr);
			String resultcode = (String) json.get("resultcode");
			if(resultcode.equals("200")) {//接口請求成功
				JSONObject result = (JSONObject) json.get("result");
				city = (String) result.get("City");
			}else {
				city = "所在城市获取失败";
			}
			if(city.contains("市")) {
				city = city.substring(0, 2);//只截取前两位汉字
			}
			System.out.println("根据IP:"+IP+"获取城市名称为:"+city);
			return city;
		}
		
		//根据城市名称获取该城市的天气状况
		private static Map<String,Object> getWeatherByCity(String City) {
			Map<String,Object> hashmap = new HashMap<String, Object>();
			String jsonStr = "";
			String strUrl = "http://apis.juhe.cn/simpleWeather/query";
			String srtVlaue = "city="+City+"&key=93e7573d94a0b85b2ac38ded5aef41e0";
			jsonStr = HttpGetPost.sendGet(strUrl,srtVlaue);
			System.out.println(jsonStr);
			JSONObject json = JSONObject.fromObject(jsonStr);
			String reason = (String) json.get("reason");
			if(reason.equals("查询成功!")) {
				hashmap = JsonUtil.parseJSONstr2Map(jsonStr);
				
			}else {
				hashmap.put("error_code", "获取"+City+"天气失败");
			}
			
			return hashmap;
		}

}
