package priv.sympsel.ui;

import cn.hutool.core.io.FileUtil;
import priv.sympsel.resource.ImagePath;
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
            createWindow(ImagePath.usernameEmpty);
            return;
        } else if (username.contains(" ")) {
            createWindow(ImagePath.usernameHaveEmpty);
            return;
        } else if (username.length() > 10) {
            createWindow(ImagePath.usernameTooLong);
            return;
        }
        for (User user : userList) {
            if (username.equals(user.getUsername())) {
                createWindow(ImagePath.usernameOccupied);
                return;
            }
        }
        if (password.isEmpty()) {
            createWindow(ImagePath.passwordEmpty);
        } else if (!password.equals(ensurePassword)) {
            createWindow(ImagePath.passwordDiffer);
        } else if (!code.equals(trueCode)) {
            createWindow(ImagePath.errorCode);
        } else {
            String path = "src/priv/sympsel/userinfo/userinfo.txt";
            List<String> userInfoFormated = FileUtil.readUtf8Lines(new File(path).getAbsolutePath());
            userInfoFormated.add(new User(username, password).toString());
            FileUtil.writeUtf8Lines(userInfoFormated, new File(path).getAbsolutePath());
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
                Mv.reLoginItem, Mv.closeItem, Mv.testUserItem, Mv.accountItem, Mv.textItem, Mv.tipsItem, Mv.reLoginItem);

        setButton();

        setTextField();

        setCode();
    }

    private void setCode() {
        JLabel codeLabel = new JLabel(trueCode);
        codeLabel.setBounds(200, 208, 100, 20);
        this.add(codeLabel);
    }

    private void setTextField() {
        Mv.UsernameTextFieldInRegister = Util.createTextField(this, new JTextField(), 108, 66, 167, 38);
        Mv.PasswordTextFieldInRegister = Util.createTextField(this, new JTextField(), 108, 110, 167, 38);
        Mv.EnsurePasswordTextField = Util.createTextField(this, new JTextField(), 108, 154, 167, 38);
        Mv.CodeTextFieldInRegister = Util.createTextField(this, new JTextField(), 108, 198, 84, 38);
    }

    public void setButton() {
        Mv.finishJB = Util.createButton(this, new JButton(), 82, 246, 135, 45, false, false);
    }

    public void setBack() {
        Util.addPicture(this, ImagePath.register,
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
            Util.createWindow(ImagePath.moneyReceivingCode);
        } else if (o == Mv.testUserItem) {
            Util.createWindow(ImagePath.testUser);
        } else if (o == Mv.textItem) {
            Util.createWindow(ImagePath.readMe);
        } else if (o == Mv.tipsItem) {
            Util.createWindow(ImagePath.tips);
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
            System.out.println("finish");
            // todo 添加悬停时完成按钮
//            this.removeAll();
//            this.getContentPane().removeAll();
//            Util.addPicture(this, ImagePath.finish_button_on, 83, 246, 133, 46);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object o = e.getSource();
        if (o == Mv.finishJB) {
            System.out.println("finish");
            // todo 替换回图片
        }
    }
}
