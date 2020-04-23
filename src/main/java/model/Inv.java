package model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.PrintStream;
import net.httpclient.HttpClientRequestUtil;
import net.httpclient.HttpResult;

public class Inv
{
  private static Inv instance = new Inv();
  private JSONObject kv;
  
  public static Inv getInstance()
  {
    return instance;
  }
  
  private String inv_uri()
  {
    return 
    
      "/light/dispatch/v1?cmd=const&" + Const.EnvText();
  }
  
  public boolean load()
  {
    String url = Const.brain + inv_uri() + "&" + Const.SignText(inv_uri());
    System.out.println("inv url:" + url);
    try
    {
      HttpResult result = HttpClientRequestUtil.executeGet(url, Const.charset, Const.timeout);
      if (result.getStatusCode().intValue() != 200)
      {
        System.out.println("获取接口失败");
        return false;
      }
      System.out.println("status result:" + result);
      String content = result.getContent();
      
      JSONObject json = JSON.parseObject(content);
      String status = json.getString("status");
      if (status.equals(Const.State.OK))
      {
        JSONObject jnode = json.getJSONObject("const");
        JSONObject kv = jnode.getJSONObject("kv");
        setKv(kv);
        return true;
      }
      return false;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public JSONObject getKv()
  {
    return this.kv;
  }
  
  public void setKv(JSONObject kv)
  {
    this.kv = kv;
  }
}
