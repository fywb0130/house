package com.zeng.house;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zengqiang on 2017/8/11.
 */
@RestController
public class Controller {
//    @Autowired
//    private LianjiaCrawler lianjiaCrawler;

    public Controller() throws Exception {
        String storagePath = "./resultData";
        int threadNum = 1;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(storagePath);
        config.setIncludeHttpsPages(true);
        config.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        Thread ljThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CrawlController ljCtl = new CrawlController(config, pageFetcher, robotstxtServer);
                    ljCtl.addSeed("https://m.lianjia.com/wh/ershoufang/104100457642.html");
                    ljCtl.start(LianjiaCrawler.class, threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ljThread.start();
    }

    @RequestMapping("lj")
    public String result() {
        return "lj";
    }
}
