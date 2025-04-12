package priv.sympsel.ui;

import cn.hutool.core.io.FileUtil;
import priv.sympsel.resource.Path;
import priv.sympsel.resource.Mv;
import priv.sympsel.userinfo.User;
import priv.sympsel.util.Util;
import priv.sympsel.Config;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static priv.sympsel.util.Util.addAll;
import static priv.sympsel.util.Util.createWindow;

public class RegisterJFrame extends JFrame implements KeyListener, ActionListener, MouseListener {
    static ArrayList<User> userList = new ArrayList<>();
    static String trueCode = Util.getCode();

    public RegisterJFrame() {

        User.readUserInfoFormated(userList);

        Util.settingsJFrame(this, "注册",
                Config.REGISTER_WINDOW_WIDTH, Config.REGISTER_WINDOW_HEIGHT);

        initJFrame();

        setBack();

        this.setVisible(true);
    }

    private void toRegister() {
        String username = Mv.UsernameTextFieldInRegister.getText();
        String password = Mv.PasswordTextFieldInRegister.getText();
        String ensurePassword = Mv.EnsurePasswordTextField.getText();
        String code = Mv.CodeTextFieldInRegister.getText();

        if (username.isEmpty()) {
            createWindow(Path.usernameEmpty);
            return;
        } else if (username.contains(" ")) {
            createWindow(Path.usernameHaveEmpty);
            return;
        } else if (username.length() > 10) {
            createWindow(Path.usernameTooLong);
            return;
        }
        for (User user : userList) {
            if (username.equals(user.getUsername())) {
                createWindow(Path.usernameOccupied);
                return;
            }
        }
        if (password.isEmpty()) {
            createWindow(Path.passwordEmpty);
        } else if (!password.equals(ensurePassword)) {
            createWindow(Path.passwordDiffer);
        } else if (!code.equals(trueCode)) {
            createWindow(Path.errorCode);
        } else {
            String path = "src/priv/sympsel/userinfo/userinfo.txt";
            List<String> userInfoFormated = FileUtil.readUtf8Lines(new File(path).getAbsolutePath());
            userInfoFormated.add(new User(username, password).toString());
            FileUtil.writeUtf8Lines(userInfoFormated, new File(path).getAbsolutePath());
            File f = new File(Path.gameSave, username);
            FileUtil.mkdir(f.getAbsolutePath());
            this.dispose();
            new LoginJFrame();
        }
    }


    public void initJFrame() {
        // 设置窗口属性
        addAll(Mv.jMenuBar, Mv.functionJMenu, Mv.helpJMenu, Mv.aboutJMenu);
        addAll(Mv.functionJMenu, Mv.reLoginItem, Mv.closeItem);
        addAll(Mv.helpJMenu, Mv.tipsItem);
        addAll(Mv.aboutJMenu, Mv.testUserItem, Mv.accountItem, Mv.textItem);

        this.setJMenuBar(Mv.jMenuBar);

        this.addKeyListener(this);

        Util.addActionListenerAll(this, Mv.functionJMenu, Mv.helpJMenu, Mv.aboutJMenu,
                Mv.reLoginItem, Mv.closeItem, Mv.testUserItem, Mv.accountItem,
                Mv.textItem, Mv.tipsItem, Mv.reLoginItem);

        setButton();

        setTextField();

        setCode();
    }

    private void setCode() {
        JLabel codeLabel = new JLabel(trueCode);
        codeLabel.setBounds(Config.REGISTER_CODE_OFFSET_X, Config.REGISTER_CODE_OFFSET_Y, Config.REGISTER_CODE_WIDTH, Config.REGISTER_CODE_HEIGHT);
        this.add(codeLabel);
    }

    private void setTextField() {
        Mv.UsernameTextFieldInRegister = Util.createTextField(this, new JTextField(), Config.REGISTER_X_OFFSET, Config.REGISTER_Y1_OFFSET, Config.REGISTER_TEXT_FIELD_WIDTH, Config.REGISTER_TEXT_FIELD_HEIGHT);
        Mv.PasswordTextFieldInRegister = Util.createTextField(this, new JTextField(), Config.REGISTER_X_OFFSET, Config.REGISTER_Y2_OFFSET, Config.REGISTER_TEXT_FIELD_WIDTH, Config.REGISTER_TEXT_FIELD_HEIGHT);
        Mv.EnsurePasswordTextField = Util.createTextField(this, new JTextField(), Config.REGISTER_X_OFFSET, Config.REGISTER_Y3_OFFSET, Config.REGISTER_TEXT_FIELD_WIDTH, Config.REGISTER_TEXT_FIELD_HEIGHT);
        Mv.CodeTextFieldInRegister = Util.createTextField(this, new JTextField(), Config.REGISTER_X_OFFSET, Config.REGISTER_Y4_OFFSET, Config.REGISTER_TEXT_FIELD_WIDTH_CODE, Config.REGISTER_TEXT_FIELD_HEIGHT);
    }

    public void setButton() {
        Mv.finishJB = Util.createButton(this, new JButton(), Config.FINISH_OFFSET_X, Config.FINISH_OFFSET_Y, Config.FINISH_WIDTH, Config.FINISH_HEIGHT, false, false);
    }

    public void setBack() {
        Util.addPicture(this, Path.register,
                Config.REGISTER_OFFSET_X, Config.REGISTER_OFFSET_Y,
                Config.REGISTER_WIDTH, Config.REGISTER_HEIGHT);
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
        if (o == Mv.reLoginItem) {
            this.dispose();
            new LoginJFrame();
        } else if (o == Mv.closeItem) {
            System.exit(0);
        } else if (o == Mv.accountItem) {
            Util.createWindow(Path.moneyReceivingCode);
        } else if (o == Mv.testUserItem) {
            Util.createWindow(Path.testUser);
        } else if (o == Mv.textItem) {
            Util.createWindow(Path.readMe);
        } else if (o == Mv.tipsItem) {
            Util.createWindow(Path.tips);
        } else if (o == Mv.finishJB) {
            toRegister();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
        if (o == Mv.finishJB) {
            // todo 添加悬停时完成按钮
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o == Mv.finishJB) {
            // todo 替换回图片
        }
    }
}
