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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
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
//        start(10);
    }

    private synchronized void start(int threadNum) {
        // start lianjia job
        if (null == ljThread || ljThread.getState().equals(Thread.State.TERMINATED)) {
            String storagePath = "./resultDataLj";
            deletePath(new File(storagePath));
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
			List<String> seed = lianjiaDao.getSeed();
			if (null == seed || seed.isEmpty()) {
                            ljCtl.addSeed("https://m.lianjia.com/wh/ershoufang/104100457642.html");
			} else {
			    for(String s : seed) {
				ljCtl.addSeed(s);
			    }
			}
                        Thread.sleep(30000);
                        ljCtl.start(LianjiaCrawler.class, threadNum);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            ljThread.start();
        }
    }

    private boolean deletePath(File dir) {
        if (!dir.exists()) {
            return true;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i< children.length; i++) {
                boolean success = deletePath(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    @Scheduled(cron="12 39 15 * * ?")
    public void task() {
        start(1);
    }

    @RequestMapping("lj")
    public String lj(@ModelAttribute LianjiaHouse house, @RequestParam Integer limit) {
        List<LianjiaHouse> houseList = lianjiaDao.select(house, limit);
        return JsonUtil.toJson(houseList);
    }

    @RequestMapping("ljState")
    public String ljState(int state) {
        if (0 == state) {
            return null == ljThread ? "NULL" : ljThread.getState().toString();
        }
        if (state > 0 && state < 11) {
            start(state);
            return "OK";
        }
        return "ERROR";
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
