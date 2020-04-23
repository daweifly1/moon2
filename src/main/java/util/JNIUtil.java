//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package util;

import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Properties;

public interface JNIUtil extends Library {
  Properties props = System.getProperties();
  String deview = props.getProperty("os.name");
  boolean isMac = deview != null && deview.startsWith("Mac");
  String path = props.getProperty("user.dir");
  String url = isMac ? path + "/cxlib_mac.so" : path + "/cxlib_win.dll";
  JNIUtil INSTANTCE = (JNIUtil)Native.loadLibrary(url, JNIUtil.class);

  void TunnelInit();

  void TunnelClose();

  void TunnelOption(String var1, String var2);

  void TunnelLoop();

  void TunnelNewLoop(String var1, String var2, String var3);

  void FlushProxy(String var1);

  String GetDeviceID();
}
