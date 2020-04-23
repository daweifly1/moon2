//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package view;

import model.Native;
import model.Node;
import model.Nodes;
import model.State;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NodeFrame extends JFrame {
    private static NodeFrame instance = new NodeFrame();
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private JPanel contentPane;
    private JButton button;
    private JLabel freeLabel;
    private JLabel vipLabel;
    private JScrollPane freeScrollPane;
    private JScrollPane vipScrollPane;
    private JList<Node> freeList;
    private JList<Node> vipList;
    private Nodes nodes = Nodes.getInstance();
    private State state = State.getInstance();

    public static NodeFrame getInstance() {
        return instance;
    }

    private NodeFrame() {
        this.initialize();
        this.frame.setVisible(true);
    }

    public void setVisible(boolean on) {
        this.frame.setVisible(on);
    }

    private void initialize() {
        this.frame = new JFrame();
        this.frame.setTitle("节点选择");
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(3);
        this.frame.setBounds(100, 100, 450, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.contentPane.setLayout((LayoutManager) null);
        this.frame.setContentPane(this.contentPane);
        this.button = new JButton("返回主页");
        this.button.setBounds(150, 0, 150, 30);
        this.contentPane.add(this.button);
        this.freeLabel = new JLabel("免费节点");
        this.freeLabel.setBounds(200, 20, 150, 30);
        this.contentPane.add(this.freeLabel);
        this.freeScrollPane = new JScrollPane();
        this.freeScrollPane.setBounds(0, 50, 450, 90);
        this.contentPane.add(this.freeScrollPane);
        this.vipLabel = new JLabel("VIP节点");
        this.vipLabel.setBounds(200, 140, 150, 30);
        this.contentPane.add(this.vipLabel);
        this.vipScrollPane = new JScrollPane();
        this.vipScrollPane.setBounds(0, 170, 450, 100);
        this.contentPane.add(this.vipScrollPane);
        this.button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NodeFrame.this.frame.setVisible(false);
                State state = State.getInstance();
                MainFrame.getInstance(state).setVisible(true);
            }
        });
        this.freeList = new JList();
        this.freeList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(e);
                NodeFrame.this.vipList.clearSelection();
                if (!NodeFrame.this.freeList.isSelectionEmpty()) {
                    String status = NodeFrame.this.state.getState();
                    if (status.equals(model.Const.State.OK)) {
                        Node n = (Node) NodeFrame.this.freeList.getSelectedValue();
                        NodeFrame.this.nodes.setCustomerNode(n);
                        Native.Connect(n);
                        JOptionPane.showMessageDialog(NodeFrame.this.frame, "当前节点:" + NodeFrame.this.freeList.getSelectedValue(), (String) null, 1);
                    } else {
                        JOptionPane.showMessageDialog(NodeFrame.this.frame, "没有流量或时间", (String) null, 1);
                    }
                }

            }
        });
        this.freeScrollPane.setViewportView(this.freeList);
        List<Node> freeNodes = this.nodes.getFreelist();
        Node[] freeData = new Node[freeNodes.size()];

        for (int i = 0; i < freeNodes.size(); ++i) {
            freeData[i] = (Node) freeNodes.get(i);
        }

        this.freeList.setListData(freeData);
        this.vipList = new JList();
        this.vipList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(e);
                NodeFrame.this.freeList.clearSelection();
                if (!NodeFrame.this.vipList.isSelectionEmpty()) {
                    String status = NodeFrame.this.state.getState();
                    if (status.equals(model.Const.State.OK)) {
//            int weight = NodeFrame.this.state.getWeight();
//            if (weight >= 50) {
//              JOptionPane.showMessageDialog(NodeFrame.this.frame, "免费用户不能选择付费节点", (String)null, 1);
//            } else {
                        Node n = (Node) NodeFrame.this.vipList.getSelectedValue();
                        NodeFrame.this.nodes.setCustomerNode(n);
                        Native.Connect(n);
                        JOptionPane.showMessageDialog(NodeFrame.this.frame, "当前节点:" + NodeFrame.this.vipList.getSelectedValue(), (String) null, 1);
//            }
                    } else {
                        JOptionPane.showMessageDialog(NodeFrame.this.frame, "没有流量或时间", (String) null, 1);
                    }
                }

            }
        });
        this.vipScrollPane.setViewportView(this.vipList);
        List<Node> vipNodes = this.nodes.getViplist();
        Node[] vipData = new Node[vipNodes.size()];

        for (int i = 0; i < vipNodes.size(); ++i) {
            vipData[i] = (Node) vipNodes.get(i);
            System.out.println(vipNodes.get(i));
        }

        this.vipList.setListData(vipData);
    }
}
