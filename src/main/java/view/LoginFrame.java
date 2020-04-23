package view;

import model.Const;
import model.State;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame
        extends JFrame {
    public static LoginFrame instance = new LoginFrame();
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private JLabel LabelUsername;
    private JLabel LabelPassword;
    private JTextField TextFieldUsername;
    private JTextField TextFieldPassword;
    private JButton ButtonLogin;
    private JButton ButtonRegister;

    public static LoginFrame getInstance() {
        return instance;
    }

    private LoginFrame() {
        initialize();

        this.frame.setVisible(true);
    }

    private void initialize() {
        this.frame = new JFrame();
        this.frame.setResizable(false);
        this.frame.setTitle(Const.site + "临时体验版");
        this.frame.setBounds(100, 100, 450, 300);
        this.frame.setDefaultCloseOperation(3);
        this.frame.getContentPane().setLayout(null);

        this.LabelUsername = new JLabel("用户名:");
        this.LabelUsername.setBounds(90, 66, 100, 16);
        this.frame.getContentPane().add(this.LabelUsername);

        this.LabelPassword = new JLabel("密 码:");
        this.LabelPassword.setBounds(90, 113, 100, 16);
        this.frame.getContentPane().add(this.LabelPassword);

        this.TextFieldUsername = new JTextField();
        this.TextFieldUsername.setBounds(184, 61, 130, 26);
        this.frame.getContentPane().add(this.TextFieldUsername);
        this.TextFieldUsername.setColumns(10);

//        this.TextFieldUsername.setText("703350556@qq.com");
        this.TextFieldUsername.setText("tigerwer@126.com");

        this.TextFieldPassword = new JTextField();
        this.TextFieldPassword.setBounds(184, 108, 130, 26);
        this.frame.getContentPane().add(this.TextFieldPassword);
        this.TextFieldPassword.setColumns(10);
        this.TextFieldPassword.setText("w123456");

        this.ButtonLogin = new JButton("登陆");
        this.ButtonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = LoginFrame.this.TextFieldUsername.getText();
                String password = LoginFrame.this.TextFieldPassword.getText();
                User user = User.getInstance();
                user.setUsername(username);
                user.setPassword(password);
                boolean result = user.login().booleanValue();
                if (result) {
                    LoginFrame.this.frame.dispose();
                    State state = State.getInstance();
                    state.setUser(user);
                    MainFrame.getInstance(state);
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this.frame, "登陆失败，用户名或密码错误", null, 1);
                }
            }
        });
        this.ButtonLogin.setBounds(148, 170, 117, 29);
        this.frame.getContentPane().add(this.ButtonLogin);

        this.ButtonRegister = new JButton("注册");
        this.ButtonRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LoginFrame.this.frame, "请在手机端注册", null, 1);
            }
        });
        this.ButtonRegister.setBounds(148, 210, 117, 29);
        this.frame.getContentPane().add(this.ButtonRegister);
    }
}
