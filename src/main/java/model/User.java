package model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.PrintStream;
import net.httpclient.HttpClientRequestUtil;
import net.httpclient.HttpResult;

public class User
{
  private String username;
  private String password;
  private String token;
  private int uid;
  private String reason;
  private static User instance = new User();
  
  public static User getInstance()
  {
    return instance;
  }
  
  private String login_uri()
  {
    return 
    
      "/light/dispatch/v1?cmd=signin&name=" + this.username.trim() + "&" + "password=" + this.password.trim() + "&" + Const.EnvText();
  }
  
  public Boolean login()
  {
    String url = Const.brain + login_uri() + "&" + Const.SignText(login_uri());
    System.out.println("login url:" + url);
    try
    {
      HttpResult result = HttpClientRequestUtil.executeGet(url, Const.charset, Const.timeout);
      if (result.getStatusCode().intValue() != 200)
      {
        System.out.println("获取接口失败");
        return Boolean.valueOf(false);
      }
      System.out.println("login result:" + result);
      String content = result.getContent();
      JSONObject json = JSON.parseObject(content);
      String status = json.getString("status");
      if (status.equals(Const.State.OK))
      {
        JSONObject t = json.getJSONObject("token");
        String token = t.getString("token");
        int uid = t.getIntValue("uid");
        System.out.println(token);
        setToken(token);
        setUid(uid);
        return Boolean.valueOf(true);
      }
      return Boolean.valueOf(false);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return Boolean.valueOf(false);
  }
  
  private String sign_uri()
  {
    return 
    
      "/light/dispatch/v1?cmd=checkin&token=" + this.token.trim() + "&" + Const.EnvText();
  }
  
  public Boolean sign()
  {
    String url = Const.brain + sign_uri() + "&" + Const.SignText(sign_uri());
    System.out.println("sign url:" + url);
    try
    {
      HttpResult result = HttpClientRequestUtil.executeGet(url, Const.charset, Const.timeout);
      if (result.getStatusCode().intValue() != 200)
      {
        System.out.println("获取接口失败");
        return Boolean.valueOf(false);
      }
      System.out.println("sign result:" + result);
      String content = result.getContent();
      JSONObject json = JSON.parseObject(content);
      String status = json.getString("status");
      if (status.equals(Const.State.OK))
      {
        setReason(json.getString("reason"));
        return Boolean.valueOf(true);
      }
      setReason(json.getString("reason"));
      return Boolean.valueOf(false);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return Boolean.valueOf(false);
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getToken()
  {
    return this.token;
  }
  
  public void setToken(String token)
  {
    this.token = token;
  }
  
  public int getUid()
  {
    return this.uid;
  }
  
  public void setUid(int uid)
  {
    this.uid = uid;
  }
  
  public String getReason()
  {
    return this.reason;
  }
  
  public void setReason(String reason)
  {
    this.reason = reason;
  }
}
