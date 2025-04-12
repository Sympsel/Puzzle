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
        Mv.UsernameTextFieldInLogin = Util.createTextField(this, new JTextField(), Config.LOGIN_X_OFFSET, Config.LOGIN_Y1_OFFSET, Config.TEXT_FIELD_WIDTH, Config.TEXT_FIELD_HEIGHT);
        Mv.PasswordTextFieldInLogin = Util.createTextField(this, new JTextField(), Config.LOGIN_X_OFFSET, Config.LOGIN_Y2_OFFSET, Config.TEXT_FIELD_WIDTH, Config.TEXT_FIELD_HEIGHT);
        Mv.CodeTextFieldInLogin = Util.createTextField(this, new JTextField(), Config.LOGIN_X_OFFSET, Config.LOGIN_Y3_OFFSET, Config.LOGIN_CODE_OFFSET_WIDTH, Config.TEXT_FIELD_HEIGHT);
    }

    public void setBack() {
        addPicture(this, ImagePath.login,
                Config.LOGIN_OFFSET_X, Config.LOGIN_OFFSET_Y,
                Config.LOGIN_WIDTH, Config.LOGIN_HEIGHT);
    }

    private void setCode() {
        JLabel codeLabel = new JLabel(trueCode);
        codeLabel.setBounds(Config.LOGIN_CODE_OFFSET_X, Config.LOGIN_CODE_OFFSET_Y,
                Config.REGISTER_CODE_WIDTH, Config.REGISTER_CODE_HEIGHT);
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
        Mv.loginJB = Util.createButton(this, new JButton(), Config.LOGIN_TEXT_OFFSET_X, Config.BUTTON_TEXT_OFFSET_Y, Config.BUTTON_TEXT_WIDTH, Config.BUTTON_TEXT_HEIGHT, false, false);
        Mv.registerJB = Util.createButton(this, new JButton(), Config.REGISTER_TEXT_OFFSET_X, Config.BUTTON_TEXT_OFFSET_Y, Config.BUTTON_TEXT_WIDTH, Config.BUTTON_TEXT_HEIGHT, false, false);
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
