package com.zeng.house.engine;

import com.zeng.HouseApplication;
import com.zeng.house.bean.LianjiaHouse;
import com.zeng.house.dao.LianjiaDao;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by zengqiang on 2017/8/11.
 */
public class LianjiaCrawler extends WebCrawler {
    private final static Pattern PATTERN = Pattern.
            compile("^https://m\\.lianjia\\.com/wh/ershoufang/" + "[0-9]+" + "\\.html$");

    private LianjiaDao dao;

    public LianjiaCrawler() {
        dao = HouseApplication.ac.getBean(LianjiaDao.class);
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        return PATTERN.matcher(url.getURL()).matches();
//        return url.getURL().startsWith("https://m.lianjia.com/wh/ershoufang/") && url.getURL().endsWith(".html");
    }

    @Override
    public void visit(Page page) {
        try {
            System.err.println("visit: " + page.getWebURL().getURL());
            Document document = Jsoup.parse(((HtmlParseData) page.getParseData()).getHtml());
            String title = document.title(), url = page.getWebURL().getURL();
            String price = null, total = null, size = null, floor = null,
                    buildTime = null, shape = null, priceAvg = null, lastSale = null,
                    position = null, putOut = null, direction = null, decorate = null,
                    elevator = null, property = null;
            Elements shortC = document.getElementsByClass("short");
            Elements info_li = document.getElementsByClass("info_li");
            Elements house_model_tit = document.getElementsByClass("house_model_tit");
            Elements similar_data_detail = document.getElementsByClass("similar_data_detail");
            Elements marker_title = document.getElementsByClass("marker_title");
            List<Node> childNodes;
            String key, value;
            for (Element element : shortC) {
                if (element.getElementsByClass("gray").isEmpty()) {
                    continue;
                }
                childNodes = element.childNodes();
                key = ((TextNode) (childNodes.get(0).childNode(0))).text();
                value = ((TextNode) childNodes.get(1)).text();
                if (key.contains("挂牌")) {
                    putOut = value;
                } else if (key.contains("单价")) {
                    price = value;
                } else if (key.contains("朝向")) {
                    direction = value;
                } else if (key.contains("装修")) {
                    decorate = value;
                } else if (key.contains("年代")) {
                    buildTime = value;
                } else if (key.contains("电梯")) {
                    elevator = value;
                } else if (key.contains("权属")) {
                    property = value;
                } else if (key.contains("楼层")) {
                    floor = value;
                }
            }
            for (Element element : info_li) {
                key = element.getElementsByClass("info_title").get(0).text();
                value = element.getElementsByClass("info_content").get(0).text();
                if (key.contains("房源户型")) {
                    shape = value;
                } else if (key.contains("建筑面积")) {
                    size = value;
                } else if (key.contains("上次交易")) {
                    lastSale = value;
                } else if (key.contains("产权所属")) {
                    property = value;
                }
            }
            for (Element element : house_model_tit) {
                if (element.getElementsByClass("red").isEmpty()) {
                    continue;
                }
                childNodes = element.childNodes();
                value = ((TextNode) (childNodes.get(1).childNode(0))).text();
                key = ((TextNode) childNodes.get(0)).text();
                if (key.contains("参考均价")) {
                    priceAvg = value;
                    break;
                }
            }
            for (Element element : similar_data_detail) {
                key = element.getElementsByClass("gray").get(0).text();
                value = element.getElementsByClass("red").get(0).getElementsByTag("span").get(0).text();
                if (key.contains("售价")) {
                    total = value;
                    break;
                }
            }
            if (null != marker_title && !marker_title.isEmpty()) {
                position = marker_title.get(0).text();
            }
            LianjiaHouse house = new LianjiaHouse(title, price, total, size,
                    floor, buildTime, shape, priceAvg, lastSale, position,
                    putOut, direction, decorate, elevator, property, url);
            dao.insert(house);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
