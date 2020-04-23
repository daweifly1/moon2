//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

import java.util.Properties;
import util.JNIUtil;
import util.MD5Util;

public class Const {
  public static Properties props = System.getProperties();
  public static JNIUtil instance;
  public static String brain;
  public static String charset;
  public static int timeout;
  public static String app;
  public static String site;
  public static String device;
  public static String version;
  public static String channel;
  public static String platform;

  static {
    instance = JNIUtil.INSTANTCE;
    brain = "https://moon.mxnode.cn";
    charset = "utf-8";
    timeout = 5000;
    app = "moon";
    site = "月光";
    device = instance.GetDeviceID();
    version = "1.0";
    channel = "me";
    platform = props.getProperty("os.name") != null && props.getProperty("os.name").startsWith("Mac") ? "mac" : "pc";
  }

  public Const() {
  }

  public static String EnvText() {
    return "app=" + app + "&" + "version=" + version + "&" + "channel=" + channel + "&" + "device=" + device + "&" + "platform=" + platform;
  }

  public static String SignText(String txt) {
    txt = txt.replaceAll("&", "-");
    txt = txt.replaceAll("5", "*");
    txt = txt.replaceAll("8", "*");
    txt = txt.replaceAll("c", "f");
    return "sign=" + MD5Util.encrypt(txt);
  }

  public static String toChinese(String en) {
    switch(en.hashCode()) {
      case -1691209903:
        if (en.equals("not access")) {
          return "付费用户不能签到";
        }
        break;
      case 3548:
        if (en.equals("ok")) {
          return "成功";
        }
        break;
      case 1517949531:
        if (en.equals("not flow")) {
          return "当前无流量";
        }
        break;
      case 1518363642:
        if (en.equals("not time")) {
          return "当前已过期";
        }
        break;
      case 1655450256:
        if (en.equals("invalid token")) {
          return "当前登陆过期";
        }
    }

    return "失败";
  }

  public static class State {
    public static String OK = "ok";
    public static String FAIL = "fail";
    public static String INVALIDTOKEN = "invalid token";

    public State() {
    }
  }
}
