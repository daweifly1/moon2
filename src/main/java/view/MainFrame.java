package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import model.Const;
import model.Native;
import model.Node;
import model.Nodes;
import model.State;
import model.User;
import util.StringUtil;

public class MainFrame
  extends JFrame
{
  private static MainFrame instance = null;
  private static final long serialVersionUID = 1L;
  private JFrame frame;
  private JPanel contentPane;
  private JLabel LableFlow;
  private JLabel LableTime;
  private JLabel LableNode;
  private JLabel LableState;
  private JLabel LableUserName;
  private JRadioButton RadioAuto;
  private JRadioButton RadioGlobal;
  private ButtonGroup buttonGroup;
  private JButton ButtonConnect;
  private JButton Button2Node;
  private JButton Button2Help;
  private String LableFlowText = "剩余流量:";
  private String LableTimeText = "到期时间:";
  private String LableNodeText = "当前节点:";
  private String LableStateText = "";
  private String ButtonConnectText = "点击连接";
  private String LableUserNameText = "用户名:";
  private Nodes nodes;
  private State state;
  
  public static MainFrame getInstance(State state)
  {
    if (instance == null) {
      instance = new MainFrame(state);
    }
    return instance;
  }
  
  public void setVisible(boolean on)
  {
    if (this.nodes == null)
    {
      this.nodes = Nodes.getInstance();
      boolean nb = this.nodes.getNodes().booleanValue();
      if (nb)
      {
        List<Node> fList = this.nodes.getFreelist();
        List<Node> vList = this.nodes.getViplist();
        int weight = this.state.getWeight();
        if (weight >= 50)
        {
          int size = fList.size();
          Random r = new Random(1L);
          int ran1 = r.nextInt(size - 1);
          this.nodes.setCustomerNode((Node)fList.get(ran1));
        }
        else
        {
          int size = vList.size();
          Random r = new Random(1L);
          int ran1 = r.nextInt(size - 1);
          this.nodes.setCustomerNode((Node)vList.get(ran1));
        }
      }
    }
    this.LableNodeText = ("当前节点:" + this.nodes.getCustomerNode().toString());
    this.LableNode.setText(this.LableNodeText);
    this.frame.setVisible(on);
  }
  
  private MainFrame(State state)
  {
    boolean b = state.getStatus().booleanValue();
    this.state = state;
    if (b)
    {
      this.LableFlowText += StringUtil.FlowString(state.getFlow());
      this.LableTimeText += state.getEnd().split(" ")[0];
      User user = User.getInstance();
      this.LableUserNameText += user.getUsername();
    }
    if (this.nodes == null)
    {
      this.nodes = Nodes.getInstance();
      boolean nb = this.nodes.getNodes().booleanValue();
      if (nb)
      {
        List<Node> fList = this.nodes.getFreelist();
        List<Node> vList = this.nodes.getViplist();
        int weight = state.getWeight();
        if (weight >= 50)
        {
          int size = fList.size();
          Random r = new Random(1L);
          int ran1 = r.nextInt(size - 1);
          this.nodes.setCustomerNode((Node)fList.get(ran1));
        }
        else
        {
          int size = vList.size();
          Random r = new Random(1L);
          int ran1 = r.nextInt(size - 1);
          this.nodes.setCustomerNode((Node)vList.get(ran1));
        }
      }
    }
    this.LableNodeText += this.nodes.getCustomerNode().toString();
    initialize();
    this.frame.setVisible(true);
  }
  
  private void initialize()
  {
    this.frame = new JFrame();
    this.frame.setTitle(Const.site + "临时体验版");
    this.frame.setResizable(false);
    this.frame.setDefaultCloseOperation(3);
    this.frame.setBounds(100, 100, 450, 300);
    this.contentPane = new JPanel();
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.frame.setContentPane(this.contentPane);
    this.contentPane.setLayout(null);
    
    this.LableUserName = new JLabel(this.LableUserNameText);
    this.LableUserName.setBounds(70, 20, 150, 30);
    this.contentPane.add(this.LableUserName);
    
    this.LableFlow = new JLabel(this.LableFlowText);
    this.LableFlow.setBounds(70, 60, 150, 30);
    this.contentPane.add(this.LableFlow);
    
    this.LableTime = new JLabel(this.LableTimeText);
    this.LableTime.setBounds(250, 60, 150, 30);
    this.contentPane.add(this.LableTime);
    
    this.LableNode = new JLabel(this.LableNodeText);
    this.LableNode.setBounds(250, 180, 150, 30);
    this.contentPane.add(this.LableNode);
    
    this.LableState = new JLabel(this.LableStateText);
    this.LableState.setBounds(176, 101, 61, 16);
    this.contentPane.add(this.LableState);
    
    this.ButtonConnect = new JButton(this.ButtonConnectText);
    this.ButtonConnect.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (MainFrame.this.ButtonConnectText.equals("点击连接"))
        {
          String reason = State.getInstance().getReason();
          if (reason.equals(""))
          {
            MainFrame.this.ButtonConnectText = "断开连接";
            Native.Proxy(Boolean.valueOf(true));
            Node n = MainFrame.this.nodes.getCustomerNode();
            Native.Connect(n);
          }
          else
          {
            JOptionPane.showMessageDialog(MainFrame.this.frame, Const.toChinese(reason), null, 1);
          }
        }
        else
        {
          MainFrame.this.ButtonConnectText = "点击连接";
          Native.Proxy(Boolean.valueOf(false));
        }
        MainFrame.this.ButtonConnect.setText(MainFrame.this.ButtonConnectText);
      }
    });
    this.ButtonConnect.setBounds(150, 110, 150, 40);
    this.contentPane.add(this.ButtonConnect);
    
    this.Button2Node = new JButton("选择节点");
    this.Button2Node.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        NodeFrame.getInstance().setVisible(true);
        MainFrame.this.frame.setVisible(false);
      }
    });
    this.Button2Node.setBounds(70, 180, 150, 30);
    this.contentPane.add(this.Button2Node);
    
    this.Button2Help = new JButton("点击签到");
    this.Button2Help.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        int weight = MainFrame.this.state.getWeight();
        if (weight >= 50)
        {
          User user = User.getInstance();
          boolean result = user.sign().booleanValue();
          String reason = user.getReason();
          if (result) {
            JOptionPane.showMessageDialog(MainFrame.this.frame, "签到成功,请重新登陆使用", null, 1);
          } else {
            JOptionPane.showMessageDialog(MainFrame.this.frame, Const.toChinese(reason), null, 1);
          }
        }
        else
        {
          JOptionPane.showMessageDialog(MainFrame.this.frame, "付费用户不能签到", null, 1);
        }
      }
    });
    this.Button2Help.setBounds(240, 20, 150, 30);
    this.contentPane.add(this.Button2Help);
    
    this.RadioAuto = new JRadioButton("智能模式");
    this.RadioAuto.setSelected(true);
    this.RadioAuto.setBounds(70, 220, 150, 30);
    this.RadioAuto.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (MainFrame.this.RadioAuto.isSelected())
        {
          System.out.println("auto");
          Native.Force(Boolean.valueOf(false));
        }
      }
    });
    this.contentPane.add(this.RadioAuto);
    
    this.RadioGlobal = new JRadioButton("全局模式");
    this.RadioGlobal.setBounds(240, 220, 150, 30);
    this.RadioGlobal.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (MainFrame.this.RadioGlobal.isSelected())
        {
          System.out.println("global");
          Native.Force(Boolean.valueOf(true));
        }
      }
    });
    this.contentPane.add(this.RadioGlobal);
    
    this.buttonGroup = new ButtonGroup();
    this.buttonGroup.add(this.RadioAuto);
    this.buttonGroup.add(this.RadioGlobal);
  }
}
