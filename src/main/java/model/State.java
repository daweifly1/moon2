package model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.PrintStream;
import net.httpclient.HttpClientRequestUtil;
import net.httpclient.HttpResult;

public class State
{
  private String state;
  private String auid;
  private String apid;
  private int level;
  private String end;
  private String flow;
  private String flowup;
  private String flowdown;
  private int link;
  private int weight;
  private String uid;
  private int shared;
  private int unrecive;
  private int recive;
  private String reason;
  private User user;
  private static State instance = new State();
  
  public static State getInstance()
  {
    return instance;
  }
  
  private String status_uri()
  {
    return 
    
      "/light/dispatch/v1?cmd=status&token=" + this.user.getToken() + "&" + Const.EnvText();
  }
  
  public Boolean getStatus()
  {
    String url = Const.brain + status_uri() + "&" + Const.SignText(status_uri());
    System.out.println("status url:" + url);
    try
    {
      HttpResult result = HttpClientRequestUtil.executeGet(url, Const.charset, Const.timeout);
      if (result.getStatusCode().intValue() != 200)
      {
        System.out.println("获取接口失败");
        return Boolean.valueOf(false);
      }
      System.out.println("status result:" + result);
      String content = result.getContent();
      
      JSONObject json = JSON.parseObject(content);
      String status = json.getString("status");
      String jreason = json.getString("reason");
      JSONObject juser = json.getJSONObject("user");
      JSONObject jaccess = juser.getJSONObject("access");
      if (status.equals(Const.State.OK))
      {
        setState(Const.State.OK);
        setEnd(juser.getString("endtime"));
        setFlow(juser.getString("allflow"));
        setAuid(jaccess.getString("auid"));
        setApid(jaccess.getString("apid"));
        setLevel(jaccess.getIntValue("level"));
        setLink(jaccess.getIntValue("link"));
        setWeight(jaccess.getIntValue("level"));
        setReason(jreason);
        return Boolean.valueOf(true);
      }
      if ((status.equals(Const.State.FAIL)) && (!jreason.equals(Const.State.INVALIDTOKEN)))
      {
        setState(Const.State.OK);
        setEnd(juser.getString("endtime"));
        setFlow(juser.getString("allflow"));
        setAuid(jaccess.getString("auid"));
        setApid(jaccess.getString("apid"));
        setLevel(jaccess.getIntValue("level"));
        setLink(jaccess.getIntValue("link"));
        setWeight(jaccess.getIntValue("level"));
        setReason(jreason);
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
  
  public String getState()
  {
    return this.state;
  }
  
  public void setState(String state)
  {
    this.state = state;
  }
  
  public String getAuid()
  {
    return this.auid;
  }
  
  public void setAuid(String auid)
  {
    this.auid = auid;
  }
  
  public String getEnd()
  {
    return this.end;
  }
  
  public void setEnd(String end)
  {
    this.end = end;
  }
  
  public String getFlow()
  {
    return this.flow;
  }
  
  public void setFlow(String flow)
  {
    this.flow = flow;
  }
  
  public String getFlowup()
  {
    return this.flowup;
  }
  
  public void setFlowup(String flowup)
  {
    this.flowup = flowup;
  }
  
  public String getFlowdown()
  {
    return this.flowdown;
  }
  
  public void setFlowdown(String flowdown)
  {
    this.flowdown = flowdown;
  }
  
  public int getLink()
  {
    return this.link;
  }
  
  public void setLink(int link)
  {
    this.link = link;
  }
  
  public int getWeight()
  {
    return this.weight;
  }
  
  public void setWeight(int weight)
  {
    this.weight = weight;
  }
  
  public String getUid()
  {
    return this.uid;
  }
  
  public void setUid(String uid)
  {
    this.uid = uid;
  }
  
  public int getShared()
  {
    return this.shared;
  }
  
  public void setShared(int shared)
  {
    this.shared = shared;
  }
  
  public int getUnrecive()
  {
    return this.unrecive;
  }
  
  public void setUnrecive(int unrecive)
  {
    this.unrecive = unrecive;
  }
  
  public int getRecive()
  {
    return this.recive;
  }
  
  public void setRecive(int recive)
  {
    this.recive = recive;
  }
  
  public String getReason()
  {
    return this.reason;
  }
  
  public void setReason(String reason)
  {
    this.reason = reason;
  }
  
  public String getApid()
  {
    return this.apid;
  }
  
  public void setApid(String apid)
  {
    this.apid = apid;
  }
  
  public int getLevel()
  {
    return this.level;
  }
  
  public void setLevel(int level)
  {
    this.level = level;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
  }
}
