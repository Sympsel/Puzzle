package priv.sympsel.ui;

import priv.sympsel.Config;
import priv.sympsel.util.Util;

import javax.swing.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        // 设置窗口属性
        Util.settingsJFrame(this, "登录",
                Config.LOGIN_WINDOW_WIDTH, Config.LOGIN_WINDOW_HEIGHT);

        this.setVisible(true);
    }
}
