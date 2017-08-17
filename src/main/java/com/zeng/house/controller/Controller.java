package com.zeng.house.controller;

import com.zeng.house.bean.LianjiaHouse;
import com.zeng.house.dao.LianjiaDao;
import com.zeng.house.engine.LianjiaCrawler;
import com.zeng.house.util.JsonUtil;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zengqiang on 2017/8/11.
 */
@RestController
public class Controller {
    private Thread ljThread;

    @Autowired
    private LianjiaDao lianjiaDao;

    public Controller() throws Exception {
        String storagePath = "./resultData";
        int threadNum = 10;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(storagePath);
        config.setIncludeHttpsPages(true);
        config.setResumableCrawling(true);
        config.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        ljThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CrawlController ljCtl = new CrawlController(config, pageFetcher, robotstxtServer);
                    ljCtl.addSeed("https://m.lianjia.com/wh/ershoufang/104100457642.html");
                    Thread.sleep(30000);
                    ljCtl.start(LianjiaCrawler.class, threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ljThread.start();
    }

    @RequestMapping("lj")
    public String lj(@ModelAttribute LianjiaHouse house, @RequestParam Integer limit) {
        List<LianjiaHouse> houseList = lianjiaDao.select(house, limit);
        return JsonUtil.toJson(houseList);
    }

    @RequestMapping("ljState")
    public String ljState() {
        return ljThread.getState().toString();
    }

    @RequestMapping("ljCount")
    public String ljCount(@ModelAttribute LianjiaHouse house) {
        return lianjiaDao.count(house).toString();
    }

    @RequestMapping("ljPosition")
    public String ljPosition() {
        return JsonUtil.toJson(LianjiaHouse.positionV.keySet());
    }
}
