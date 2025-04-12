package priv.sympsel.resource;

import javax.swing.*;
public class Mv {

    // 创建菜单
    public static final JMenuBar jMenuBar = new JMenuBar();
    // 创建菜单功能(功能、关于我们)
    public static final JMenu functionJMenu = new JMenu("功能");
    public static final  JMenu helpJMenu = new JMenu("帮助");
    public static final JMenu aboutJMenu = new JMenu("关于");
    public static final  JMenu chooseImageJMenu = new JMenu("选择图片");

    // 创建菜单项
    public static final JMenuItem addImageItem = new JMenuItem("添加图片");
    public static final JMenuItem closeItem = new JMenuItem("退出游戏：X");

    public static final JMenuItem tipsItem = new JMenuItem("补习");

    public static final JMenuItem accountItem = new JMenuItem("乞讨");
    public static final JMenuItem textItem = new JMenuItem("文本");
    public static final JMenuItem replayItem = new JMenuItem("重新开始：空格");
    public static final JMenuItem reLoginItem = new JMenuItem("重新登录：Z");

    public static final JMenuItem fastWinItem = new JMenuItem("一键通关：W");
    public static final JMenuItem showItem = new JMenuItem("成图预览：Q");
    public static final JMenuItem randomImageItem = new JMenuItem("随机图片：N");
    public static final JMenuItem clearHistoryItem = new JMenuItem("清空历史记录");
    public static final JMenuItem testUserItem = new JMenuItem("测试用户");
    public static final JMenuItem history = new JMenuItem("历史记录");

    // 创建按钮
    public static JButton loginJB;
    public static JButton registerJB;
    public static JButton finishJB;

    // 背景图片
    public static ImageIcon backgroundImage;
    public static JLabel background = new JLabel();

    // 文本输入框
    public static JTextField UsernameTextFieldInRegister;
    public static JTextField PasswordTextFieldInRegister;
    public static JTextField EnsurePasswordTextField;
    public static JTextField PasswordTextFieldInLogin;
    public static JTextField UsernameTextFieldInLogin;
    public static JTextField CodeTextFieldInRegister;
    public static JTextField CodeTextFieldInLogin;
    public static JTextField AddImageTextField;
}