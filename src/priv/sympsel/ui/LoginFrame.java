package priv.sympsel.ui;

import priv.sympsel.Config;
import priv.sympsel.resource.ImagePath;
import priv.sympsel.resource.Mv;
import priv.sympsel.util.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static priv.sympsel.util.Util.addPicture;

public class LoginFrame extends JFrame implements KeyListener, ActionListener {
    public LoginFrame() {
        initJFrame();

        setBack();


        this.setVisible(true);

    }

    public void setBack() {
        addPicture(this, ImagePath.login,
                Config.LOGIN_OFFSET_X, Config.LOGIN_OFFSET_Y,
                Config.LOGIN_WIDTH, Config.LOGIN_HEIGHT);
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
}
