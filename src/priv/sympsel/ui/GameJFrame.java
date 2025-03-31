package priv.sympsel.ui;

import javax.swing.*;

import priv.sympsel.Config;
import priv.sympsel.resource.*;
import priv.sympsel.resource.Mv;
import priv.sympsel.util.AddImage;
import priv.sympsel.util.NonConfigurableVariables;
import priv.sympsel.util.Util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;

import static priv.sympsel.resource.PictureArray.data;
import static priv.sympsel.util.Util.*;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    public GameJFrame() {

        settingsJFrame(this, "拼图游戏v3.0",
                Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);

        // 初始化菜单栏
        initJFrame();

        data = PictureArray.disrupt_2();
        Util.setStep(0);

        // 初始化图片
        initImage(data);

        this.setVisible(true);
    }

    private void initImage(int[][] data) {
        this.getContentPane().removeAll();

        setStepCountAvailable(Config.SHOW_STEP);

        if (isWin()) {
            gameOver();
        }

        addPicture(this, data);
        addPicture(this, ImagePath.back,
                Config.BACKGROUND_OFFSET_X, Config.BACKGROUND_OFFSET_Y,
                Config.BACKGROUND_WIDTH, Config.BACKGROUND_HEIGHT);

        this.getContentPane().repaint();
    }

    private void initImage(int[] data) {
        addPicture(this, data);
        addPicture(this, ImagePath.back,
                Config.BACKGROUND_OFFSET_X, Config.BACKGROUND_OFFSET_Y,
                Config.BACKGROUND_WIDTH, Config.BACKGROUND_HEIGHT);
    }

    public void initJFrame() {
        // 添加菜单: 功能、帮助、关于
        // 功能： 添加图片、随机图片、成图预览、一键通关、重新开始、选择图片、重新登录、退出游戏
        // 帮助： 提示
        // 关于： 测试用户、乞讨、文本
        addAll(Mv.jMenuBar, Mv.functionJMenu, Mv.helpJMenu, Mv.aboutJMenu);
        addAll(Mv.functionJMenu, Mv.addImageItem, Mv.randomImageItem,
                Mv.showItem, Mv.fastWinItem, Mv.replayItem,
                Mv.chooseImageJMenu, Mv.reLoginItem, Mv.closeItem);
        addAll(Mv.helpJMenu, Mv.tipsItem);
        addAll(Mv.aboutJMenu, Mv.testUserItem, Mv.accountItem, Mv.textItem);

        this.setJMenuBar(Mv.jMenuBar);

        this.addKeyListener(this);

        /*绑定动作监听: 添加图片、随机图片、成图预览、一键通关、重新开始、选择图片、重新登录、退出游戏
                      提示、
                      测试用户、乞讨、文本*/

        Util.addActionListenerAll(this, Mv.randomImageItem, Mv.showItem,
                Mv.fastWinItem, Mv.replayItem, Mv.chooseImageJMenu,
                Mv.reLoginItem, Mv.closeItem, Mv.tipsItem,Mv.addImageItem,
                Mv.testUserItem, Mv.accountItem, Mv.textItem);

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
            String path = ImagePath.pathPri + Config.DEFAULT_IMAGE + "/whole" + ImagePath.type;
            addPicture(this, path,
                    Config.BACKGROUND_OFFSET_X, Config.BACKGROUND_OFFSET_Y,
                    Config.BACKGROUND_WIDTH, Config.BACKGROUND_HEIGHT);
            addPicture(this, ImagePath.back,
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
        setStep(0);
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

        addPicture(this, ImagePath.win,
                Config.WIN_OFFSET_X, Config.WIN_OFFSET_Y,
                Config.WIN_WIDTH, Config.WIN_HEIGHT);

        addPicture(this, data);

        addPicture(this, ImagePath.back,
                Config.BACKGROUND_OFFSET_X, Config.BACKGROUND_OFFSET_Y,
                Config.BACKGROUND_WIDTH, Config.BACKGROUND_HEIGHT);
        this.getContentPane().repaint();
    }

    private void setStepCountAvailable(boolean b) {
        if (!b) return;
        JLabel stepCountLabel = new JLabel("步数：" + Util.getStep());
        stepCountLabel.setBounds(
                Config.STEP_OFFSET_X, Config.STEP_OFFSET_Y,
                Config.STEP_WIDTH, Config.STEP_HEIGHT);
        this.getContentPane().add(stepCountLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == Mv.reLoginItem) {
            new LoginFrame();
        } else if (o == Mv.closeItem) {
            System.exit(0);
        } else if (o == Mv.fastWinItem) {
            gameOver();
            data = PictureArray.getArr_2();
        } else if (o == Mv.showItem) {
//            createWindow();
        } else if (o == Mv.randomImageItem) {
            randomPicture();
        } else if (o == Mv.replayItem) {
            reStart();
        } else if (o == Mv.tipsItem) {
            createWindow(ImagePath.tips);
        } else if (o == Mv.testUserItem) {
            createWindow(ImagePath.testUser);
        } else if (o == Mv.accountItem) {
            createWindow(ImagePath.moneyReceivingCode);
        } else if (o == Mv.textItem) {
            createWindow(ImagePath.readMe);
        } else if(o == Mv.addImageItem) {
            // todo 添加图片
            try {
                AddImage.appendPictureALL();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void addGamePicture() {
        for (int i = 1; i <= NonConfigurableVariables.MAX_PICTURE_COUNT; i++) {
            JMenuItem p = new JMenuItem("p" + i);
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
}
