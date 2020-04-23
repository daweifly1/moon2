package model;

import java.io.PrintStream;
import net.httpclient.HttpClientRequestUtil;
import net.httpclient.HttpResult;

public class Brain
{
  public Boolean test()
  {
    try
    {
      HttpResult result = HttpClientRequestUtil.executeGet(Const.brain, Const.charset, Const.timeout);
      if (result.getStatusCode().intValue() != 200)
      {
        System.out.println("获取接口失败");
        return Boolean.valueOf(false);
      }
      System.out.println(result);
      return Boolean.valueOf(true);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return Boolean.valueOf(false);
  }
}
