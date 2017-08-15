package com.zeng.house.engine;

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

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by zengqiang on 2017/8/11.
 */
public class LianjiaCrawler extends WebCrawler {
    private final static Pattern PATTERN = Pattern.
            compile("^https://m\\.lianjia\\.com/wh/ershoufang/" + "[0-9]+" + "\\.html$");

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
            String title = document.title();
            Elements shortC = document.getElementsByClass("short");
            Elements info_li = document.getElementsByClass("info_li");
            Elements house_model_tit = document.getElementsByClass("house_model_tit");
            Elements red_big = document.getElementsByClass("red big");
            List<Node> childNodes;
            String key, value;
            for (Element element : shortC) {
                childNodes = element.childNodes();
                key = ((TextNode) (childNodes.get(0).childNode(0))).text();
                value = ((TextNode) childNodes.get(1)).text();
            }
            for (Element element : info_li) {
//            children = element.getAllElements();
            }
            for (Element element : house_model_tit) {
//            children = element.getAllElements();
            }
            for (Element element : red_big) {
//            children = element.getAllElements();
            }
            System.err.println(document.body());
        } catch (Exception e) {}
    }
}
