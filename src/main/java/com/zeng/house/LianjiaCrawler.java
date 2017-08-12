package com.zeng.house;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import org.springframework.stereotype.Component;

/**
 * Created by zengqiang on 2017/8/11.
 */
@Component
public class LianjiaCrawler extends WebCrawler {


    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        return true;
    }

    @Override
    public void visit(Page page) {

    }
}
