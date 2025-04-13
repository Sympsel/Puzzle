package priv.sympsel.ui;

import javax.swing.*;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import priv.sympsel.Config;
import priv.sympsel.resource.*;
import priv.sympsel.resource.Mv;
import priv.sympsel.util.AddImage;
import priv.sympsel.util.NonConfigurableVariables;
import priv.sympsel.util.Util;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static priv.sympsel.resource.PictureArray.data;
import static priv.sympsel.util.Util.*;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    public GameJFrame() {

        settingsJFrame(this, "拼图游戏v4.2",
                Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);

        // 初始化菜单栏
        initJFrame();

        data = PictureArray.disrupt_2();
        NonConfigurableVariables.setStep(0);

        // 初始化图片
        initImage(data);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            FileUtil.del(Path.onlineUser);
        }));

        this.setVisible(true);
    }

    private void initImage(int[][] data) {
        this.getContentPane().removeAll();

        setStepCountAvailable(Config.SHOW_STEP);

        if (isWin()) {
            if (NonConfigurableVariables.getStatus() == 0) {
                NonConfigurableVariables.setStatus(1);
                save();
            }
            gameOver();
        }

        addPicture(this, data);
        addPicture(this, Path.back,
                Config.BACKGROUND_OFFSET_X, Config.BACKGROUND_OFFSET_Y,
                Config.BACKGROUND_WIDTH, Config.BACKGROUND_HEIGHT);

        this.getContentPane().repaint();
    }

    public void initJFrame() {
        // 添加菜单: 功能、帮助、关于
        // 功能： 添加图片、随机图片、成图预览、一键通关、重新开始、选择图片、重新登录、退出游戏
        // 帮助： 提示
        // 关于： 测试用户、乞讨、文本
        addAll(Mv.jMenuBar, Mv.functionJMenu, Mv.helpJMenu, Mv.aboutJMenu);
        addAll(Mv.functionJMenu, Mv.addImageItem, Mv.randomImageItem,
                Mv.showItem, Mv.fastWinItem, Mv.replayItem, Mv.clearHistoryItem,
                Mv.chooseImageJMenu, Mv.history, Mv.reLoginItem,/* Mv.deleteItem,*/ Mv.closeItem);
        addAll(Mv.helpJMenu, Mv.tipsItem);
        addAll(Mv.aboutJMenu, Mv.testUserItem, Mv.accountItem, Mv.textItem);

        this.setJMenuBar(Mv.jMenuBar);

        this.addKeyListener(this);

        /*绑定动作监听: 添加图片、随机图片、成图预览、一键通关、重新开始、选择图片、重新登录、退出游戏
                      提示、
                      测试用户、乞讨、文本*/

        Util.addActionListenerAll(this, Mv.randomImageItem, Mv.showItem,
                Mv.fastWinItem, Mv.replayItem, Mv.chooseImageJMenu, Mv.history,
                Mv.reLoginItem, Mv.closeItem, Mv.tipsItem, Mv.addImageItem, /*Mv.deleteItem,*/
                Mv.testUserItem, Mv.accountItem, Mv.textItem, Mv.clearHistoryItem);

        addGamePicture();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int kc = e.getKeyCode();
        if (kc == 81) {
            this.getContentPane().removeAll();
            String path = Path.pathPri + Config.DEFAULT_IMAGE + "/whole" + Config.type;
            addPicture(this, path,
                    Config.BACKGROUND_OFFSET_X, Config.BACKGROUND_OFFSET_Y,
                    Config.BACKGROUND_WIDTH, Config.BACKGROUND_HEIGHT);
            addPicture(this, Path.back,
                    Config.BACKGROUND_OFFSET_X, Config.BACKGROUND_OFFSET_Y,
                    Config.BACKGROUND_WIDTH, Config.BACKGROUND_HEIGHT);
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int kc = e.getKeyCode();
        if (kc == 32) {
            reStart();
        }

        if (isWin()) {
            gameOver();
            return;
        }

        switch (kc) {
            case 87:
                // 一键通关不计入历史
                NonConfigurableVariables.setStatus(1);
                gameOver();
                data = PictureArray.getArr_2();
                break;
            case 78:
                randomPicture();
                break;
            case 88:
                System.exit(0);
                break;
        }
        PictureArray.move(data, kc);
        initImage(data);
    }

    private void randomPicture() {
        Random r = new Random();
        Config.DEFAULT_IMAGE = r.nextInt(NonConfigurableVariables.MAX_PICTURE_COUNT) + 1;
        reStart();
    }

    private void reStart() {
        initImage(data);
        data = PictureArray.disrupt_2();
        NonConfigurableVariables.setStep(0);
        NonConfigurableVariables.setStatus(0);
    }

    private boolean isWin() {
        for (int i = 0; i < data.length * data[0].length; i++) {
            if (data[i / data.length][i % data.length] !=
                    PictureArray.getArr_2()[i / data.length][i % data.length]) {
                return false;
            }
        }
        return true;
    }

    private void gameOver() {
        this.getContentPane().removeAll();

        setStepCountAvailable(Config.SHOW_STEP);

        addPicture(this, Path.win,
                Config.WIN_OFFSET_X, Config.WIN_OFFSET_Y,
                Config.WIN_WIDTH, Config.WIN_HEIGHT);

        addPicture(this, data);

        addPicture(this, Path.back,
                Config.BACKGROUND_OFFSET_X, Config.BACKGROUND_OFFSET_Y,
                Config.BACKGROUND_WIDTH, Config.BACKGROUND_HEIGHT);
        this.getContentPane().repaint();
    }

    private void save() {
        int step = NonConfigurableVariables.getStep();
        File onLineUser = new File(Path.onlineUser);
        List<String> online = FileUtil.readUtf8Lines(onLineUser);
        File f = new File(Path.gameSave, String.format("%s%s", online.getFirst(), ".txt"));
        if (!f.exists()) FileUtil.touch(f.getAbsolutePath());
        List<String> list = FileUtil.readUtf8Lines(f);
        DateTime date = DateUtil.date(System.currentTimeMillis());
        String str = date.toString("yyyy-MM-dd_HH:mm:ss");
        String line = String.format("%s_step=%d", str, step);
        list.add(line);
        FileUtil.writeUtf8Lines(list, f.getAbsolutePath());
    }

    private void setStepCountAvailable(boolean b) {
        if (!b) return;
        JLabel stepCountLabel = new JLabel(String.format("步数：%d", NonConfigurableVariables.getStep()));
        stepCountLabel.setBounds(
                Config.STEP_OFFSET_X, Config.STEP_OFFSET_Y,
                Config.STEP_WIDTH, Config.STEP_HEIGHT);
        this.getContentPane().add(stepCountLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == Mv.reLoginItem) {
            this.dispose();
            new LoginJFrame();
        } else if (o == Mv.closeItem) {
            System.exit(0);
        } else if (o == Mv.fastWinItem) {
            // 一键通关不计入历史
            NonConfigurableVariables.setStatus(1);
            gameOver();
            data = PictureArray.getArr_2();
        } else if (o == Mv.randomImageItem) {
            randomPicture();
        } else if (o == Mv.replayItem) {
            reStart();
        } else if (o == Mv.tipsItem) {
            createWindow(Path.tips);
        } else if (o == Mv.testUserItem) {
            createWindow(Path.testUser);
        } else if (o == Mv.accountItem) {
            createWindow(Path.moneyReceivingCode);
        } else if (o == Mv.textItem) {
            createWindow(Path.readMe);
        } else if (o == Mv.addImageItem) {
            addImage();
        } else if (o == Mv.clearHistoryItem) {
            List<String> user = FileUtil.readUtf8Lines(new File(Path.onlineUser));
            File f = new File(Path.gameSave, String.format("%s%s", user.getFirst(), ".txt"));
            FileUtil.del(f);
        } else if (o == Mv.history) {
            List<String> userList = FileUtil.readUtf8Lines(new File(Path.onlineUser));
            String user = userList.getFirst();
            File f = new File(Path.gameSave, String.format("%s%s", user, ".txt"));
            if (!f.exists() || f.length() == 0) {
                JOptionPane.showMessageDialog(this, "无历史记录", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            List<String> list = FileUtil.readUtf8Lines(f);
            StringBuilder sb = new StringBuilder();
            list.forEach(s -> sb.append(s).append("\n"));
            JOptionPane.showMessageDialog(this, sb.toString(), "历史记录", JOptionPane.INFORMATION_MESSAGE);
        } /*else if (o == Mv.deleteItem) {
            List<String> list = FileUtil.readUtf8Lines(new File(Path.userInfo));
            List<String> newList = new ArrayList<>();
            for (String s : list) if (s.equals(NonConfigurableVariables.getUserToSave())) newList.add(s);
            FileUtil.writeUtf8Lines(newList, Path.userInfo);
        }*/
    }

    private void addGamePicture() {
        for (int i = 1; i <= NonConfigurableVariables.MAX_PICTURE_COUNT; i++) {
            JMenuItem p = new JMenuItem(String.format("p%d", i));
            Mv.chooseImageJMenu.add(p);
            int finalI = i;
            p.addActionListener(e -> {
                Object o = e.getSource();
                if (o == p) {
                    Config.DEFAULT_IMAGE = finalI;
                    reStart();
                }
            });
        }
    }

    private void addImage() {
        JFrame f = new JFrame();
        f.setSize(215, 60);
        f.setAlwaysOnTop(true);
        f.setLocationRelativeTo(null);

        Mv.AddImageTextField = createTextFieldToAddImage(f, new JTextField("请输入或粘贴文件路径(回车继续)"), 20, 1, 160, 20);
        f.setVisible(true);
        Mv.AddImageTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int kc = e.getKeyCode();
                if (kc == 10) { // 回车键
                    String path = Mv.AddImageTextField.getText().trim();
                    if (path.isEmpty()) {
                        JOptionPane.showMessageDialog(f, "请输入有效的文件路径！");
                        return;
                    }
                    boolean flag;
                    try {
                        flag = AddImage.appendPicture(path);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(f, String.format("%s%s", "添加图片失败：", ex.getMessage()));
                        return;
                    }
                    if (flag) {
                        f.dispose();
                        createWindow(Path.pleaseRestart);
                    } else JOptionPane.showMessageDialog(f, "添加图片失败，请检查路径是否正确！");

                }
            }
        });
    }

    public static JTextField createTextFieldToAddImage(JFrame jFrame, JTextField jtextField, int x, int y, int width, int height) {
        jFrame.setLayout(null);
        jtextField.setBounds(x, y, width, height);
        jFrame.getContentPane().add(jtextField);
        return jtextField;
    }
}

