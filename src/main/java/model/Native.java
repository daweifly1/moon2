package model;

import java.io.PrintStream;
import util.JNIUtil;

public class Native
{
  public static Native instance = new Native();

  public static Native getInstance()
  {
    return instance;
  }

  public static JNIUtil jniUtil = JNIUtil.INSTANTCE;

  public static void Build()
  {
    jniUtil.TunnelInit();
    jniUtil.TunnelNewLoop("0.0.0.0", "8589", "9090");
    System.out.println("build");
  }

  public static void Connect(Node node)
  {
    User user = User.getInstance();
    jniUtil.TunnelOption("App", Const.app);
    jniUtil.TunnelOption("Token", user.getToken());
    jniUtil.TunnelOption("UID", String.valueOf(user.getUsername()));
    jniUtil.TunnelOption("Device", Const.device);
    jniUtil.TunnelOption("Platform", Const.platform);
    jniUtil.TunnelOption("Version", Const.version);
    jniUtil.TunnelOption("HiveIP", node.getIp());
    jniUtil.TunnelOption("Hive", node.getServer());

    System.out.println("App:" + Const.app + " Token:" + user.getToken() + " UID:" + String.valueOf(user.getUsername()) +
      " Device:" + Const.device + " Version:" + Const.version + " HiveIP:" + node.getIp() +
      " Hive:" + node.getServer());
    System.out.println("connect");
  }

  public static void Force(Boolean on)
  {
    jniUtil.TunnelOption("DomainRules", "");
    jniUtil.TunnelOption("ForceRouting", on.booleanValue() ? "on" : "off");
    System.out.println("force");
  }

  public static void Proxy(Boolean on)
  {
    if (on.booleanValue()) {
      jniUtil.FlushProxy("http://localhost:8589/pac");
    } else {
      jniUtil.FlushProxy("off");
    }
    System.out.println("proxy");
  }
}
