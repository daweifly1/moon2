package util;

import java.util.List;

public class ListUtil
{
  public static boolean isEmpty(List list)
  {
    return list == null ? true : list.isEmpty();
  }
  
  public static boolean isNotEmpty(List list)
  {
    return !isEmpty(list);
  }
}
