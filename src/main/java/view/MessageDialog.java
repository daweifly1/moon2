package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

public class MessageDialog
  extends JDialog
{
  private static final long serialVersionUID = 1L;
  private final JPanel contentPanel = new JPanel();
  private JDialog dialog;
  private JLabel JLableMessage;
  private String JLableMessageText = "no message";
  private JButton ButtonOK;
  private JButton ButtonCancel;
  
  public void Alert(String message)
  {
    this.JLableMessage.setText(message);
    this.dialog.setVisible(true);
  }
  
  public MessageDialog()
  {
    this.dialog = new JDialog();
    this.dialog.setBounds(100, 100, 283, 202);
    this.dialog.getContentPane().setLayout(new BorderLayout());
    this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.dialog.getContentPane().add(this.contentPanel, "Center");
    this.contentPanel.setLayout(null);
    
    this.JLableMessage = new JLabel(this.JLableMessageText);
    this.JLableMessage.setBounds(37, 53, 198, 16);
    this.contentPanel.add(this.JLableMessage);
    
    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new FlowLayout(2));
    this.dialog.getContentPane().add(buttonPane, "South");
    
    this.ButtonOK = new JButton("OK");
    this.ButtonOK.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MessageDialog.this.dialog.setVisible(false);
      }
    });
    this.ButtonOK.setActionCommand("OK");
    buttonPane.add(this.ButtonOK);
    getRootPane().setDefaultButton(this.ButtonOK);
    
    this.ButtonCancel = new JButton("Cancel");
    this.ButtonCancel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        MessageDialog.this.dialog.setVisible(false);
      }
    });
    this.ButtonCancel.setActionCommand("Cancel");
    buttonPane.add(this.ButtonCancel);
  }
}
