package model;

public class Node
{
  private int id;
  private String country;
  private String name;
  private String server;
  private String ip;
  private int online;
  private int maxonline;
  private int pick;
  private String type;
  
  public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getCountry()
  {
    return this.country;
  }
  
  public void setCountry(String country)
  {
    this.country = country;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getServer()
  {
    return this.server;
  }
  
  public void setServer(String server)
  {
    this.server = server;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public int getOnline()
  {
    return this.online;
  }
  
  public void setOnline(int online)
  {
    this.online = online;
  }
  
  public int getMaxonline()
  {
    return this.maxonline;
  }
  
  public void setMaxonline(int maxonline)
  {
    this.maxonline = maxonline;
  }
  
  public int getPick()
  {
    return this.pick;
  }
  
  public void setPick(int pick)
  {
    this.pick = pick;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public String toString()
  {
    return this.name;
  }
}
