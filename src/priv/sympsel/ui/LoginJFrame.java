package priv.sympsel.ui;

import priv.sympsel.Config;
import priv.sympsel.resource.ImagePath;
import priv.sympsel.resource.Mv;
import priv.sympsel.userinfo.User;
import priv.sympsel.util.Util;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import static priv.sympsel.util.Util.*;

public class LoginJFrame extends JFrame implements KeyListener, ActionListener, MouseListener {

    ArrayList<User> userList = new ArrayList<>();
    static String trueCode = getCode();
    public LoginJFrame() {
        User.readUserInfoFormated(userList);

        initJFrame();

        setBack();

        setButton();

        this.setVisible(true);
    }

    private void setTextField() {
        Mv.UsernameTextFieldInLogin = Util.createTextField(this, new JTextField(), 108, 71, 167, 38);
        Mv.PasswordTextFieldInLogin = Util.createTextField(this, new JTextField(), 108, 124, 167, 38);
        Mv.CodeTextFieldInLogin = Util.createTextField(this, new JTextField(), 108, 177, 84, 38);
    }

    public void setBack() {
        addPicture(this, ImagePath.login,
                Config.LOGIN_OFFSET_X, Config.LOGIN_OFFSET_Y,
                Config.LOGIN_WIDTH, Config.LOGIN_HEIGHT);
    }

    private void setCode() {
        JLabel codeLabel = new JLabel(trueCode);
        codeLabel.setBounds(200, 185, 100, 20);
        this.add(codeLabel);
    }

    private void toLogin() {
        String username = Mv.UsernameTextFieldInLogin.getText();
        String password = Mv.PasswordTextFieldInLogin.getText();
        String code = Mv.CodeTextFieldInLogin.getText();

        for (User user : userList) {
            if (username.equals(user.getUsername())) {
                if (!password.equals(user.getPassword())) {
                    createWindow(ImagePath.errorPassword);
                    return;
                } else if (!code.equals(trueCode)) {
                    createWindow(ImagePath.errorCode);
                    return;
                } else {
                    this.dispose();
                    new GameJFrame();
                    return;
                }
            }
        }
        createWindow(ImagePath.noUser);
    }

    public void initJFrame() {
        // 设置窗口属性
        Util.settingsJFrame(this, "登录",
                Config.LOGIN_WINDOW_WIDTH, Config.LOGIN_WINDOW_HEIGHT);
        Util.addAll(Mv.jMenuBar, Mv.functionJMenu, Mv.helpJMenu, Mv.aboutJMenu);
        Util.addAll(Mv.functionJMenu, Mv.closeItem);
        Util.addAll(Mv.helpJMenu, Mv.tipsItem);
        Util.addAll(Mv.aboutJMenu, Mv.testUserItem, Mv.accountItem, Mv.textItem);
        this.setJMenuBar(Mv.jMenuBar);

        this.addKeyListener(this);

        Util.addActionListenerAll(this, Mv.closeItem, Mv.tipsItem,
                Mv.testUserItem, Mv.accountItem, Mv.textItem);

        setTextField();

        setCode();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == Mv.closeItem) {
            System.exit(0);
        } else if (o == Mv.tipsItem) {
            Util.createWindow(ImagePath.tips);
        } else if (o == Mv.testUserItem) {
            Util.createWindow(ImagePath.testUser);
        } else if (o == Mv.accountItem) {
            Util.createWindow(ImagePath.moneyReceivingCode);
        } else if (o == Mv.textItem) {
            Util.createWindow(ImagePath.readMe);
        }
    }

    public void setButton() {
        Mv.loginJB = Util.createButton(this, new JButton(), 14, 242, 131, 45, false, false);
        Mv.registerJB = Util.createButton(this, new JButton(), 158, 242, 131, 45, false, false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o == Mv.loginJB) {
            toLogin();
        } else if (o == Mv.registerJB) {
            this.dispose();
            new RegisterJFrame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object o = e.getSource();
        if (o == Mv.loginJB) {
            System.out.println("login");
            // todo 添加悬停时登录按钮
        } else if (o == Mv.registerJB) {
            System.out.println("register");
            // todo 添加悬停时注册按钮
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o == Mv.loginJB) {
            System.out.println("login");
            // todo 替换回图片
        } else if (o == Mv.registerJB) {
            System.out.println("register");
            // todo 替换回图片
        }
    }
}
