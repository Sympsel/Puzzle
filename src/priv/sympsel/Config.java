package priv.sympsel;

public class Config {
    // ==================游戏设置====================> 可动调整
// 选择初始图片
    public static int DEFAULT_IMAGE = 2;

    // 可视计数器
    public static boolean SHOW_STEP = true;

    // 逆向打乱次数
    public static int disruptStep = 1000;

    // 序列图及整图修改后类型
    public static final String type = ".jpg";
    public static final String format = "jpg";

    // 设置小图片边框样式：凸起: 0、凹陷: 1
    public static int STYLE = 0;

// ===================界面参数===================> 一般不动
// 如果有显示错位，根据界面调整数据

    /*
       +-----------------> x
       |
       |    图形界面坐标系
       |
       v
       y                    */
// 游戏窗口的宽高(默认与内置图片匹配)
    public static int GAME_WINDOW_WIDTH = 244;
    public static int GAME_WINDOW_HEIGHT = 300;
    // 登录窗口的宽高（默认与内置图片匹配）
    public static int LOGIN_WINDOW_WIDTH = 315;
    public static int LOGIN_WINDOW_HEIGHT = 362;
    // 登录窗口的宽高（默认与内置图片匹配）
    public static int REGISTER_WINDOW_WIDTH = 315;
    public static int REGISTER_WINDOW_HEIGHT = 362;
    // 计步偏移量
    public static int STEP_OFFSET_X = 92;
    public static int STEP_OFFSET_Y = 13;
    // 计步器宽高
    public static int STEP_WIDTH = 100;
    public static int STEP_HEIGHT = 20;
    // 左上角小图片的偏移量（用于对齐）
    public static int LEFT_TOP_OFFSET_X = 24;
    public static int LEFT_TOP_OFFSET_Y = 38;
    // 背景图偏移量
    public static int BACKGROUND_OFFSET_X = -8;
    public static int BACKGROUND_OFFSET_Y = -22;
    // 获胜图片偏移量
    public static int WIN_OFFSET_X = 61;
    public static int WIN_OFFSET_Y = 150;
    // 获胜图片宽高
    public static int WIN_WIDTH = 100;
    public static int WIN_HEIGHT = 50;
    // 游戏界面背景图片宽高
    public static int BACKGROUND_WIDTH = 244;
    public static int BACKGROUND_HEIGHT = 300;
    // 单张小图片的宽高 (内置图片大小为45 * 45)
// 将新增切片图及整图按照内置图片的命名规则命名
    public static int WIDTH = 45;
    public static int HEIGHT = 45;
    // 登录界面偏移
    public static int LOGIN_OFFSET_X = 1;
    public static int LOGIN_OFFSET_Y = 1;
    // 登录界面宽高
    public static int LOGIN_WIDTH = 300;
    public static int LOGIN_HEIGHT = 300;
    // 注册界面偏移
    public static int REGISTER_OFFSET_X = 1;
    public static int REGISTER_OFFSET_Y = 1;

    // 注册界面宽高
    public static int REGISTER_WIDTH = 300;
    public static int REGISTER_HEIGHT = 300;
    // 注册界面文本框偏移
    public static int REGISTER_X_OFFSET = 108;
    public static int REGISTER_Y1_OFFSET = 66;
    public static int REGISTER_Y2_OFFSET = 110;
    public static int REGISTER_Y3_OFFSET = 154;
    public static int REGISTER_Y4_OFFSET = 198;
    // 注册界面文本框宽高
    public static int REGISTER_TEXT_FIELD_WIDTH = 167;
    public static int REGISTER_TEXT_FIELD_WIDTH_CODE = 84;
    public static int REGISTER_TEXT_FIELD_HEIGHT = 38;
    // 完成按钮偏移
    public static int FINISH_OFFSET_X = 82;
    public static int FINISH_OFFSET_Y = 246;
    // 完成按钮宽高
    public static int FINISH_WIDTH = 135;
    public static int FINISH_HEIGHT = 45;
    // 注册界面验证码框偏移
    public static int REGISTER_CODE_OFFSET_X = 200;
    public static int REGISTER_CODE_OFFSET_Y = 208;
    // 验证码框宽高
    public static int REGISTER_CODE_WIDTH = 100;
    public static int REGISTER_CODE_HEIGHT = 20;
    // 登录界面文本框偏移
    public static int LOGIN_X_OFFSET = 108;
    public static int LOGIN_Y1_OFFSET = 71;
    public static int LOGIN_Y2_OFFSET = 124;
    public static int LOGIN_Y3_OFFSET = 177;
    // 登录界面文本框宽高
    public static int TEXT_FIELD_WIDTH = 167;
    public static int TEXT_FIELD_HEIGHT = 38;
    // 登录界面验证码框偏移
    public static int LOGIN_CODE_OFFSET_X = 200;
    public static int LOGIN_CODE_OFFSET_Y = 185;
    // 登录界面验证码框宽高
    public static int LOGIN_CODE_OFFSET_WIDTH = 84;
    // 登录，注册按钮偏移
    public static int LOGIN_TEXT_OFFSET_X = 14;
    public static int REGISTER_TEXT_OFFSET_X = 158;
    public static int BUTTON_TEXT_OFFSET_Y = 242;
    // 登录，注册按钮宽高
    public static int BUTTON_TEXT_WIDTH = 131;
    public static int BUTTON_TEXT_HEIGHT = 45;
}
