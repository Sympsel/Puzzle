package priv.sympsel.ui;

import priv.sympsel.util.Util;
import priv.sympsel.Config;

import javax.swing.*;

public class RegisterJFrame extends JFrame {
    public RegisterJFrame() {
        // 设置窗口属性
        Util.settingsJFrame(this, "注册",
                Config.REGISTER_WINDOW_WIDTH, Config.REGISTER_WINDOW_HEIGHT);

        this.setVisible(true);
    }
}
