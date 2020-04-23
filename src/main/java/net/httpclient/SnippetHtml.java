//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.httpclient;

import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SnippetHtml {
  public SnippetHtml() {
  }

  public static String parseHtml(String url) {
    HttpClient client = new HttpClient();
    HttpMethod method = null;
    String html = "";

    try {
      method = new GetMethod(url);
      client.executeMethod(method);
      html = method.getResponseBodyAsString();
    } catch (HttpException var9) {
      var9.printStackTrace();
    } catch (IOException var10) {
      var10.printStackTrace();
    } finally {
      if (method != null) {
        method.releaseConnection();
      }

    }

    return html;
  }

  public static void getHtmlEarthBean(String html) {
    if (html != null && !"".equals(html)) {
      Document doc = Jsoup.parse(html);
      Elements linksElements = doc.getElementsByAttributeValue("class", "news-table");
      Iterator var4 = linksElements.iterator();

      while(var4.hasNext()) {
        Element ele = (Element)var4.next();
        Elements linksElements1 = ele.getElementsByTag("td");
        Iterator var7 = linksElements1.iterator();

        while(var7.hasNext()) {
          Element ele1 = (Element)var7.next();
          System.out.println(ele1.text());
        }
      }
    }

  }
}
