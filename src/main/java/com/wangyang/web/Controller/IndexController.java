package com.wangyang.web.Controller;

import com.wangyang.web.Service.BlogService;
import com.wangyang.web.Service.SpeakService;
import com.wangyang.web.Service.TagService;
import com.wangyang.web.Service.TopSpeakService;
import com.wangyang.web.Service.TypeService;
import com.wangyang.web.Util.APIUtil;
import com.wangyang.web.Util.IpUtil;
import com.wangyang.web.vo.Weather;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	APIUtil apiutil = new APIUtil();
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private SpeakService speakService;
    @Autowired
    private TopSpeakService topSpeakService;
    @Value("${comment.avatar}")
    private String avatar;
    @Value("${blog.serverurl}")
    private String bolg_serverurl;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        System.out.println("-------------加载index页面----------------------");
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));//设置显示type的数量
        model.addAttribute("tags",tagService.listTagTop(10));//设置显示tags的数量
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        //获取到设置为置顶的留言信息
        model.addAttribute("newspeak",speakService.getOneSpeak(topSpeakService.getTopSpeak().getSpeak_id()));
        
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query); //将查询的字符串回显
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        System.out.println("-------------加载blog页面------------");
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
    
    @GetMapping("/getWeather")
    public String test(HttpServletRequest request,Model model){
        //获取IP地址
        String ipAddress =IpUtil.getIpAddr(request);
        System.out.println(ipAddress);
        if(ipAddress.equals("0:0:0:0:0:0:0:1")) {
//        	1.192.119.149  鄭州
//        	223.104.212.171 上海
//        	42.227.160.255 南陽
        	ipAddress = "223.104.212.171";
        }
        Weather weather = apiutil.returnWeather(ipAddress);
        
        if(weather!=null&&weather.getCity().contains("未知")) {//今日接口次数使用已达上线
        	model.addAttribute("weathers",weather);
            model.addAttribute("aqi",weather.getAqi());
            model.addAttribute("city",weather.getCity());
            model.addAttribute("message","今日请求次数已达上限");
        }else if(weather!=null&&weather.getCity().contains("获取失败")) {//HttpGetPost请求超时(Time Out)
        	model.addAttribute("weathers",weather);
            model.addAttribute("aqi",weather.getAqi());
            model.addAttribute("city",weather.getCity());
            model.addAttribute("message","请求超时");
        }else{//请求正常
        	model.addAttribute("weathers",weather);
            model.addAttribute("aqi",weather.getAqi());
            model.addAttribute("city",weather.getCity());
            model.addAttribute("message",null);
        }


        return "index :: weatherCard";
    }


}
