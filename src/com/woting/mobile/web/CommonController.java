package com.woting.mobile.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woting.mobile.MobileUtils;
import com.woting.mobile.model.MobileParam;
import com.woting.mobile.session.mem.SessionMemoryManage;
import com.woting.mobile.session.model.MobileSession;
import com.woting.mobile.session.model.SessionKey;

@Controller
public class CommonController {

    private SessionMemoryManage smm=SessionMemoryManage.getInstance();

    @RequestMapping(value="/common/getVersion.do")
    @ResponseBody
    public Map<String,Object> getVersion(HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            //0-处理访问
            Map<String, Object> m=MobileUtils.getDataFromRequest(request);
            if (m!=null&&m.size()>0) {
                MobileParam mp=MobileUtils.getMobileParam(m);
                SessionKey sk=(mp==null?null:mp.getSessionKey());
                if (sk!=null){
                    map.put("SessionId", sk.getSessionId());
                    MobileSession ms=smm.getSession(sk);
                    if (ms!=null) ms.access();
                }
            }
            //1-获取版本
            map.put("Version", MobileUtils.getLastVersion());
            return map;
        } catch(Exception e) {
            e.printStackTrace();
            map.put("ReturnType", "T");
            map.put("TClass", e.getClass().getName());
            map.put("Message", e.getMessage());
            return map;
        }
    }

    @RequestMapping(value="/common/getZoneList.do")
    @ResponseBody
    public Map<String,Object> getZoneList(HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            //0-处理访问
            Map<String, Object> m=MobileUtils.getDataFromRequest(request);
            if (m!=null&&m.size()>0) {
                MobileParam mp=MobileUtils.getMobileParam(m);
                SessionKey sk=(mp==null?null:mp.getSessionKey());
                if (sk!=null){
                    map.put("SessionId", sk.getSessionId());
                    MobileSession ms=smm.getSession(sk);
                    if (ms!=null) ms.access();
                }
                //获得上级地区分类Id
            }
            //1-获取地区信息
            List<Map<String, Object>> zl = new ArrayList<Map<String, Object>>();
            Map<String, Object> zone;
            zone = new HashMap<String, Object>();
            zone.put("ZoneId", "001");
            zone.put("ZoneName", "北京");
            zl.add(zone);
            zone = new HashMap<String, Object>();
            zone.put("ZoneId", "001");
            zone.put("ZoneName", "北京");
            zl.add(zone);
            zone.put("ZoneId", "002");
            zone.put("ZoneName", "天津");
            zl.add(zone);
            zone.put("ZoneId", "003");
            zone.put("ZoneName", "上海");
            zl.add(zone);
            zone.put("ZoneId", "004");
            zone.put("ZoneName", "广州");
            zl.add(zone);
            zone.put("ZoneId", "005");
            zone.put("ZoneName", "深圳");
            zl.add(zone);
            zone.put("ZoneId", "006");
            zone.put("ZoneName", "重庆");
            zl.add(zone);
            zone.put("ZoneId", "007");
            zone.put("ZoneName", "杭州");
            zl.add(zone);
            map.put("ReturnType", "1001");
            map.put("ZoneList", zl);
            return map;
        } catch(Exception e) {
            e.printStackTrace();
            map.put("ReturnType", "T");
            map.put("TClass", e.getClass().getName());
            map.put("Message", e.getMessage());
            return map;
        }
    }

    @RequestMapping(value="/mainPage.do")
    @ResponseBody
    public Map<String,Object> mainPage(HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            //0-处理访问
            Map<String, Object> m=MobileUtils.getDataFromRequest(request);
            if (m!=null&&m.size()>0) {
                MobileParam mp=MobileUtils.getMobileParam(m);
                SessionKey sk=(mp==null?null:mp.getSessionKey());
                if (sk!=null){
                    map.put("SessionId", sk.getSessionId());
                    MobileSession ms=smm.getSession(sk);
                    if (ms!=null) ms.access();
                }
                //获得SessionId
            }
            //1-获取地区信息
            List<Map<String, Object>> ml = new ArrayList<Map<String, Object>>();
            Map<String, Object> media;
            media = new HashMap<String, Object>();
            media.put("MediaType", "RADIO"); //电台
            media.put("RadioName", "北京交通广播");
            media.put("RadioId", "001");
            media.put("RadioImg", "images/dft_broadcast.png");
            media.put("RadioURI", "mms://a.b.c/aaa.mpg");
            media.put("CurrentContent", "路况信息");//当前节目
            ml.add(media);
            media = new HashMap<String, Object>();
            media.put("MediaType", "RES"); //文件资源
            media.put("ResType", "mp3");
            media.put("ResClass", "评书");
            media.put("ResStyle", "文学名著");
            media.put("ResActor", "张三");
            media.put("ResName", "三打白骨精");
            media.put("ResImg", "images/dft_res.png");
            media.put("ResURI", "http://www.woting.fm/resource/124osdf3.mp3");
            media.put("ResTime", "14:35");
            ml.add(media);
            media = new HashMap<String, Object>();
            media.put("MediaType", "RES"); //文件资源
            media.put("ResType", "mp3");
            media.put("ResClass", "歌曲");
            media.put("ResStyle", "摇滚");
            media.put("ResActor", "李四");
            media.put("ResName", "歌曲名称");
            media.put("ResImg", "images/dft_actor.png");
            media.put("ResURI", "http://www.woting.fm/resource/124osdf3.mp3");
            media.put("ResTime", "4:35");
            ml.add(media);
            media = new HashMap<String, Object>();
            media.put("MediaType", "RADIO"); //电台
            media.put("RadioName", "央广音乐");
            media.put("RadioId", "002");
            media.put("RadioImg", "images/dft_broadcast.png");
            media.put("RadioURI", "mms://a.b.c/aaa.mpg");
            media.put("CurrentContent", "经典回顾");//当前节目
            ml.add(media);
            media = new HashMap<String, Object>();
            media.put("MediaType", "RADIO"); //电台
            media.put("RadioName", "央广新闻");
            media.put("RadioId", "003");
            media.put("RadioImg", "images/dft_broadcast.png");
            media.put("RadioURI", "mms://a.b.c/aaa.mpg");
            media.put("CurrentContent", "时政要闻");//当前节目
            ml.add(media);
            media = new HashMap<String, Object>();
            media.put("MediaType", "RES"); //文件资源
            media.put("ResType", "mp3");
            media.put("ResClass", "脱口秀");
            media.put("ResStyle", "文化");
            media.put("ResSeries", "逻辑思维");
            media.put("ResActor", "罗某某");
            media.put("ResName", "逻辑思维001");
            media.put("ResImg", "images/dft_actor.png");
            media.put("ResURI", "http://www.woting.fm/resource/124osdf3.mp3");
            media.put("ResTime", "4:35");
            ml.add(media);
            map.put("ReturnType", "1001");
            map.put("MainList", ml);
            return map;
        } catch(Exception e) {
            e.printStackTrace();
            map.put("ReturnType", "T");
            map.put("TClass", e.getClass().getName());
            map.put("Message", e.getMessage());
            return map;
        }
    }

    @RequestMapping(value="/searchByVoice.do")
    @ResponseBody
    public Map<String,Object> searchByVoice(HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String, Object>();
        try {
            //0-处理访问
            Map<String, Object> m=MobileUtils.getDataFromRequest(request);
            if (m!=null&&m.size()>0) {
                MobileParam mp=MobileUtils.getMobileParam(m);
                SessionKey sk=(mp==null?null:mp.getSessionKey());
                if (sk!=null){
                    map.put("SessionId", sk.getSessionId());
                    MobileSession ms=smm.getSession(sk);
                    if (ms!=null) ms.access();
                }
                //获得语音转文字的结果
            }
            //1-获取地区信息
            List<Map<String, Object>> rl = new ArrayList<Map<String, Object>>();
            Map<String, Object> media;
            media = new HashMap<String, Object>();
            media.put("MediaType", "RES"); //文件资源
            media.put("ResType", "mp3");
            media.put("ResClass", "评书");
            media.put("ResStyle", "文学名著");
            media.put("ResActor", "张三");
            media.put("ResName", "三打白骨精");
            media.put("ResImg", "images/dft_res.png");
            media.put("ResURI", "http://www.woting.fm/resource/124osdf3.mp3");
            media.put("ResTime", "14:35");
            rl.add(media);
            media = new HashMap<String, Object>();
            media.put("MediaType", "RADIO"); //电台
            media.put("RadioName", "北京交通广播");
            media.put("RadioId", "001");
            media.put("RadioImg", "images/dft_broadcast.png");
            media.put("RadioURI", "mms://a.b.c/aaa.mpg");
            media.put("CurrentContent", "路况信息");//当前节目
            rl.add(media);
            media = new HashMap<String, Object>();
            media.put("MediaType", "RES"); //文件资源
            media.put("ResType", "mp3");
            media.put("ResClass", "歌曲");
            media.put("ResStyle", "摇滚");
            media.put("ResActor", "李四");
            media.put("ResName", "歌曲名称");
            media.put("ResImg", "images/dft_actor.png");
            media.put("ResURI", "http://www.woting.fm/resource/124osdf3.mp3");
            media.put("ResTime", "4:35");
            rl.add(media);
            media = new HashMap<String, Object>();
            media.put("MediaType", "RADIO"); //电台
            media.put("RadioName", "央广新闻");
            media.put("RadioId", "003");
            media.put("RadioImg", "images/dft_broadcast.png");
            media.put("RadioURI", "mms://a.b.c/aaa.mpg");
            media.put("CurrentContent", "时政要闻");//当前节目
            rl.add(media);
            media = new HashMap<String, Object>();
            media.put("MediaType", "RES"); //文件资源
            media.put("ResType", "mp3");
            media.put("ResClass", "脱口秀");
            media.put("ResStyle", "文化");
            media.put("ResSeries", "逻辑思维");
            media.put("ResActor", "罗某某");
            media.put("ResName", "逻辑思维001");
            media.put("ResImg", "images/dft_actor.png");
            media.put("ResURI", "http://www.woting.fm/resource/124osdf3.mp3");
            media.put("ResTime", "4:35");
            rl.add(media);
            media = new HashMap<String, Object>();
            media.put("MediaType", "RADIO"); //电台
            media.put("RadioName", "央广音乐");
            media.put("RadioId", "002");
            media.put("RadioImg", "images/dft_broadcast.png");
            media.put("RadioURI", "mms://a.b.c/aaa.mpg");
            media.put("CurrentContent", "经典回顾");//当前节目
            rl.add(media);
            map.put("ReturnType", "1001");
            map.put("ResultList", rl);
            return map;
        } catch(Exception e) {
            e.printStackTrace();
            map.put("ReturnType", "T");
            map.put("TClass", e.getClass().getName());
            map.put("Message", e.getMessage());
            return map;
        }
    }
}