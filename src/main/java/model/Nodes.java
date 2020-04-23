//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import model.Const.State;
import net.httpclient.HttpClientRequestUtil;
import net.httpclient.HttpResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Nodes {
    private List<Node> freelist;
    private List<Node> viplist;
    private User user;
    private Node customerNode;
    private static Nodes instance = new Nodes();

    private Nodes() {
    }

    public static Nodes getInstance() {
        return instance;
    }

    private String node_uri() {
        return "/light/dispatch/v1?cmd=node&" + Const.EnvText();
    }

    public Boolean getNodes() {
        String url = Const.brain + this.node_uri() + "&" + Const.SignText(this.node_uri());
        System.out.println("nodes url:" + url);

        try {
            HttpResult result = HttpClientRequestUtil.executeGet(url, Const.charset, Const.timeout);
            if (result.getStatusCode() != 200) {
                System.out.println("获取接口失败");
                return false;
            } else {
                System.out.println("nodes result:" + result);
                String content = result.getContent();
                JSONObject json = JSON.parseObject(content);
                String status = json.getString("status");
                JSONObject free = null;
                JSONObject vip = null;
                if (!status.equals(State.OK)) {
                    return false;
                } else {
                    JSONObject jnode = json.getJSONObject("node");
                    Iterator var10 = jnode.keySet().iterator();

                    while (var10.hasNext()) {
                        String str = (String) var10.next();
                        if (str.equals("free")) {
                            free = jnode.getJSONObject(str);
                        } else {
                            vip = jnode.getJSONObject(str);
                        }
                    }

                    List<Node> freeNodes = new ArrayList();
                    Iterator var11 = free.keySet().iterator();

                    while (true) {
                        JSONObject temp;
                        do {
                            if (!var11.hasNext()) {
                                List<Node> vipNodes = new ArrayList();
                                Iterator var23 = vip.keySet().iterator();

                                while (true) {
                                    do {
                                        if (!var23.hasNext()) {
                                            this.setFreelist(freeNodes);
                                            this.setViplist(vipNodes);
                                            return true;
                                        }

                                        String str = (String) var23.next();
                                        temp = vip.getJSONObject(str);
                                    } while (temp.equals("{}"));

                                    Iterator var26 = temp.keySet().iterator();

                                    while (var26.hasNext()) {
                                        String nip = (String) var26.next();
                                        JSONObject jnip = temp.getJSONObject(nip);
                                        Node node = new Node();
                                        node.setIp(nip);
                                        node.setName(jnip.getString("name"));
                                        node.setServer(jnip.getString("host"));
                                        node.setMaxonline(jnip.getIntValue("limit"));
                                        node.setOnline(jnip.getIntValue("online"));
                                        node.setType("vip");
                                        node.setPick(9999);
                                        vipNodes.add(node);
                                    }
                                }
                            }

                            String str = (String) var11.next();
                            temp = free.getJSONObject(str);
                        } while (temp.equals("{}"));

                        Iterator var14 = temp.keySet().iterator();

                        while (var14.hasNext()) {
                            String nip = (String) var14.next();
                            JSONObject jnip = temp.getJSONObject(nip);
                            Node node = new Node();
                            node.setIp(nip);
                            node.setName(jnip.getString("name"));
                            node.setServer(jnip.getString("host"));
                            node.setMaxonline(jnip.getIntValue("limit"));
                            node.setOnline(jnip.getIntValue("online"));
                            node.setType("free");
                            node.setPick(9999);
                            freeNodes.add(node);
                        }
                    }
                }
            }
        } catch (Exception var18) {
            var18.printStackTrace();
            return false;
        }
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Node> getFreelist() {
        return this.freelist;
    }

    public void setFreelist(List<Node> freelist) {
        this.freelist = freelist;
    }

    public List<Node> getViplist() {
        return this.viplist;
    }

    public void setViplist(List<Node> viplist) {
        this.viplist = viplist;
    }

    public Node getCustomerNode() {
        return this.customerNode;
    }

    public void setCustomerNode(Node customerNode) {
        this.customerNode = customerNode;
    }
}
