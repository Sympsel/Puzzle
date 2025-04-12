package priv.sympsel.util;

import priv.sympsel.Config;
import priv.sympsel.resource.ImagePath;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Random;

public class Util {
    public static void addPicture(
            JFrame jFrame, String path,
            int x, int y, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        JLabel jLabel = new JLabel(icon);
        jLabel.setBounds(x, y, width, height);
        jLabel.setBorder(new BevelBorder(Config.STYLE));
        jFrame.getContentPane().add(jLabel);
    }

    public static void addPicture(JFrame jFrame, String path) {
        ImageIcon icon = new ImageIcon(path);
        JLabel jLabel = new JLabel(icon);
        jFrame.add(jLabel);
    }

    public static void addPicture(JFrame jFrame, int[][] data) {
        String path = ImagePath.pathPri + Config.DEFAULT_IMAGE + "/";
        for (int i = 0; i < 16; i++) {
            addPicture(jFrame, path + data[i / 4][i % 4] + Config.type,
                    Config.LEFT_TOP_OFFSET_X + i % 4 * Config.WIDTH,
                    Config.LEFT_TOP_OFFSET_Y + i / 4 * Config.HEIGHT,
                    Config.WIDTH, Config.HEIGHT);
        }
    }

    public static void addActionListenerAll(
            JFrame jFrame, JMenuItem... items) {
        for (JMenuItem item : items) {
            item.addActionListener((ActionListener) jFrame);
        }
    }

    public static void addActionListenerAll(JButton... jbs) {
        for (JButton jButton : jbs) {
            jButton.addActionListener((ActionListener) jButton);
        }
    }

    public static void addPicture(JFrame jFrame, int[] data) {
        for (int i = 0; i < 15; i++) {
            addPicture(jFrame, ImagePath.pathPri + data[i] + Config.type,
                    Config.LEFT_TOP_OFFSET_X + i % 4 * Config.WIDTH,
                    Config.LEFT_TOP_OFFSET_Y + i / 4 * Config.HEIGHT,
                    Config.WIDTH, Config.HEIGHT);
        }
    }

    public static void addAll(JMenu menu, JMenuItem... items) {
        for (JMenuItem item : items) {
            menu.add(item);
        }
    }

    public static void addAll(JMenuBar menuBar, JMenu... menus) {
        for (JMenu menu : menus) {
            menuBar.add(menu);
        }
    }

    public static void settingsJFrame(
            JFrame jFrame, String title, int width, int height) {
        jFrame.setSize(width, height);
        jFrame.setTitle(title);
        jFrame.setAlwaysOnTop(true);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(null);
    }

    public static void swap(int[][] data, int x, int y, int x_, int y_) {
        if (x == x_ && y == y_) {
            return;
        }
        NonConfigurableVariables.setStep(NonConfigurableVariables.getStep() + 1);
        int temp = data[x][y];
        data[x][y] = data[x_][y_];
        data[x_][y_] = temp;
    }

    public static void createWindow(String picture) {
        JDialog jDialog = new JDialog();
        //创建管理图片的容器
        JLabel jLabel = new JLabel(new ImageIcon(picture));
        jLabel.setBounds(0, 0, 180, 180);
        jDialog.getContentPane().add(jLabel);
        jDialog.setSize(200, 200);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setModal(true);
        jDialog.setVisible(true);
    }

    public static int getImageGroupNumber(String pathToFile) {
        File imgDir = new File(pathToFile);
        return countDirectSubfolders(imgDir);
    }

    public static int countDirectSubfolders(File dir) {
        if (!dir.isDirectory()) return 0;
        int count = 0;
        File[] files = dir.listFiles();
        if (files == null) return 0;
        for (File file : files) if (file.isDirectory()) count++;
        return count;
    }

    public static String reFormatPath(String path) {
        path = path.replaceAll("\"", "");
        return path.replaceAll("\\\\|\\\\\\\\", "/");
    }

    public static JButton createButton(JFrame jFrame, JButton jb,
                                       int x, int y, int width, int height,
                                       boolean borderPainted, boolean contentAreaFilled) {
        jb.setBounds(x, y, width, height);
        jb.setBorderPainted(borderPainted);
        jb.setContentAreaFilled(contentAreaFilled);

        jFrame.getContentPane().add(jb);
        jb.addActionListener((ActionListener) jFrame);
        jb.addMouseListener((MouseListener) jFrame);
        return jb;
    }

    public static JTextField createTextField(JFrame jFrame, JTextField jtextField, int x, int y, int width, int height) {
        jFrame.setLayout(null);
        jtextField.setBounds(x, y, width, height);
        jFrame.getContentPane().add(jtextField);
        jFrame.addKeyListener((KeyListener) jFrame);
        return jtextField;
    }

    public static JLabel createLabel(JFrame jFrame, JLabel jLabel, int x, int y, int width, int height) {
        jLabel.setBounds(x, y, width, height);
        jFrame.getContentPane().add(jLabel);
        return jLabel;
    }

    public static String getCode() {
        Random r = new Random();
        char[] ch = new char[5];
        for (int i = 0; i < 4; i++) {
            int choice = r.nextInt(2);
            if (choice == 0) ch[i] = (char) (r.nextInt(26) + 'a');
            else ch[i] = (char) (r.nextInt(26) + 'A');
        }
        ch[4] = (char) (r.nextInt(10) + '0');
        for (int i = 0; i < ch.length; i++) {
            int index = r.nextInt(ch.length);
            char temp = ch[i];
            ch[i] = ch[index];
            ch[index] = temp;
        }
        // 替换了易于混淆的字母
        return new String(ch).replace("I", "i").replace("l", "L");
    }
}
