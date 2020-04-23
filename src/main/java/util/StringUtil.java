package util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class StringUtil
{
  public static final String DEFAULT_SEPARATOR = ",";
  
  public static String getMessageFormat(String message, Object... objects)
  {
    if (message == null) {
      return message;
    }
    return MessageFormatter.arrayFormat(message, objects).getMessage();
  }
  
  public static boolean isJdkInnerObject(Object object)
  {
    if (object == null) {
      return false;
    }
    if (object.getClass().isPrimitive()) {
      return true;
    }
    String packageName = object.getClass().getPackage().getName();
    if (packageName.indexOf("java.") > -1) {
      return true;
    }
    return false;
  }
  
  public static boolean isEmptyObjects(Object... objects)
  {
    if (objects == null) {
      return true;
    }
    Object[] arrayOfObject = objects;int j = objects.length;
    for (int i = 0; i < j; i++)
    {
      Object object = arrayOfObject[i];
      if (object == null) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isBlankObjects(Object... objects)
  {
    if (objects == null) {
      return true;
    }
    Object[] arrayOfObject = objects;int j = objects.length;
    for (int i = 0; i < j; i++)
    {
      Object object = arrayOfObject[i];
      if (object == null) {
        return true;
      }
      if (((object instanceof String)) && 
        (StringUtils.isBlank(object.toString()))) {
        return true;
      }
      if (((object instanceof List)) && 
        (ListUtil.isEmpty((List)object))) {
        return true;
      }
      if (((object instanceof Map)) && 
        (MapUtil.isEmpty((Map)object))) {
        return true;
      }
    }
    return false;
  }
  
  public static String[] getSplitArrayBySeparator(String value, String separator)
  {
    if (value == null) {
      return null;
    }
    return value.split(separator);
  }
  
  public static List<String> getSplitListBySeparator(String value, String separator)
  {
    if (value == null) {
      return null;
    }
    return Arrays.asList(getSplitArrayBySeparator(value, separator));
  }
  
  public static List<String> getSplitListByDefaultSeparator(String value)
  {
    if (value == null) {
      return null;
    }
    return Arrays.asList(getSplitArrayBySeparator(value, ","));
  }
  
  public static boolean isContainDefaultSeparator(String value)
  {
    if (value == null) {
      return false;
    }
    if (value.indexOf(",") > -1) {
      return true;
    }
    return false;
  }
  
  public static Set<String> getReplaceKeyFromExpress(String express, String... strings)
  {
    Matcher m = Pattern.compile("\\#.*?\\#").matcher(express);
    Set<String> set = new HashSet();
    while (m.find())
    {
      boolean flag = true;
      String replaceKey = m.group().replace("#", "").replace("#", "");
      String[] arrayOfString;
      int j = (arrayOfString = strings).length;
      for (int i = 0; i < j; i++)
      {
        String key = arrayOfString[i];
        if (key.equals(replaceKey)) {
          flag = false;
        }
      }
      if (flag) {
        set.add(m.group().replace("#", "").replace("#", ""));
      }
    }
    return set;
  }
  
  public static String FlowString(String flow)
  {
    String x = "0.00G";
    double d = Double.valueOf(flow).doubleValue();
    if (d < 0.0D) {
      return x;
    }
    double flowG = Math.round(d / 1024.0D / 1024.0D / 1024.0D);
    double flowM = Math.round(d / 1024.0D / 1024.0D);
    double flowK = Math.round(d / 1024.0D);
    if (flowG > 1024.0D) {
      return "无限";
    }
    if ((flowG > 1.0D) && (flowG <= 1024.0D)) {
      return String.valueOf(flowG) + "G";
    }
    if (flowM > 1.0D) {
      return String.valueOf(flowM) + "M";
    }
    if (flowK > 1.0D) {
      return String.valueOf(flowK) + "K";
    }
    return x;
  }
}
