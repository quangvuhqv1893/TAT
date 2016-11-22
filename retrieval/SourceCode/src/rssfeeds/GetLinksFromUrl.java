package rssfeeds;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Hà Viết Tráng - HAVIETTRANG
 * @date Nov 13, 2016 4:08:32 PM
 * @website haviettrang.blogspot.com
 * @Notes View my notes at haviettrang.postach.io
 */
public class GetLinksFromUrl {

    public static final String WEBSITE_24H_COM_VN =
            "http://www.24h.com.vn/tai-nan-giao-thong-c408.html";
    public static final String BAO_GIAO_THONG_VN =
            "http://www.baogiaothong.vn/tin-tuc-tai-nan-giao-thong-moi-nhat-trong-ngay--hinh-anh-video-clip-tngt-channel30/";
    public static final String NEWS_ZING_VN ="http://news.zing.vn/giao-thong.html";
    public static final String EVA_VN =
            "http://eva.vn/tai-nan-giao-thong-moi-nhat-p1375c73.html";

    private Document document;
    private Elements elements;
    private Iterator<Element> iterator;

    public GetLinksFromUrl() {
    }

    private void connectUrl(String url) {
        try {
            document = Jsoup.connect(url).timeout(10000).userAgent("Chrome").get();
        } catch (IOException ex) {
            System.out.println("Can't connect to website " + url);
            ex.printStackTrace();
        }
    }
    
    /**
     * 
     * @return Các links về mục tai nạn giao thông tại trang
     * <a href="http://www.24h.com.vn/tai-nan-giao-thong-c408.html">
     * http://www.24h.com.vn/tai-nan-giao-thong-c408.html</a>
     */
    public HashSet<String> getLinksFrom24h() {
        return getLinksFrom24h(WEBSITE_24H_COM_VN);
    }

    /**
     * 
     * @param url nguồn url để lấy các links từ trang 24h.com.vn
     * @return Các links về mục tai nạn giao thông tại trang <code>url</code>
     */
    public HashSet<String> getLinksFrom24h(String url) {
        connectUrl(url);

        HashSet<String> links = new HashSet<>();

        elements = document.select("a[href]");
        iterator = elements.iterator();

        while (iterator.hasNext()) {
            String s = iterator.next().attr("href");
            if (s.contains("/tai-nan-giao-thong/")) {
                if (!s.contains("24h.com.vn")) {
                    s = "http://www.24h.com.vn" + s;
                }
                links.add(s);
            }
        }
        return links;
    }

    /**
     * Lấy links từ "http://tintuc.vn/giao-thong?page=<b>pageBegin</b>"</ br>
     * tới "http://tintuc.vn/giao-thong?page=<b>pageEnd</b> về chuyên mục
     * giao thông
     *
     * @param pageBegin
     * @param pageEnd
     * @return Các links về mục tai nạn giao thông tại trang
     * <a href="http://tintuc.vn/giao-thong">
     * http://tintuc.vn/giao-thong</a>
     */
    public HashSet<String> getLinksFromTinTucVn(int pageBegin, int pageEnd) {

        HashSet<String> links = new HashSet<>();

        for (int i = pageBegin; i <= pageEnd; i++) {
            connectUrl("http://tintuc.vn/giao-thong?page=" + i);

            elements = document.select("a[href]");
            iterator = elements.iterator();

            while (iterator.hasNext()) {
                String s = iterator.next().attr("href");
                if (s.contains("/giao-thong/")) {
                    links.add(s);
                }
            }
        }

        return links;
    }

    //chua hoan thien
    public HashSet<String> getLinksFromZing() {
        connectUrl(NEWS_ZING_VN);

        HashSet<String> links = new HashSet<>();

        elements = document.select("a[href]");
        iterator = elements.iterator();

        while (iterator.hasNext()) {
            String s = iterator.next().attr("href");
//            if (s.contains("news.zing.vn/")) {
//                if (!s.contains("24h.com.vn")) {
//                    s = "http://www.24h.com.vn" + s;
//                    links.add(s);
//                }
            links.add(s);
            System.out.println(s);
//            }
        }
        return links;
    }
    
    /**
     * 
     * @return Các links về mục tai nạn giao thông tại trang
     * <a href="http://eva.vn/tai-nan-giao-thong-moi-nhat-p1375c73.html">
     * http://eva.vn/tai-nan-giao-thong-moi-nhat-p1375c73.html</a>
     */
    public HashSet<String> getLinksFromEva() {
        return getLinksFromEva(EVA_VN);
    }

    /**
     * 
     * @param url nguồn url để lấy các links từ trang eva.vn
     * @return Các links về mục tai nạn giao thông tại trang <code>url</code>
     */
    public HashSet<String> getLinksFromEva(String url) {
        connectUrl(url);

        HashSet<String> links = new HashSet<>();

        elements = document.select("a[href]");
        iterator = elements.iterator();

        while (iterator.hasNext()) {
            String s = iterator.next().attr("href");
            if (s.contains("/tin-tuc/")) {
                if (!s.contains("tai-nan-giao-thong-moi-nhat") && !s.contains("eva.vn/")) {
                    s = "http://eva.vn" + s;
                    links.add(s);
                }
            }
        }
        return links;
    }
}
