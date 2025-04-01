package priv.sympsel.util;

import priv.sympsel.resource.ImagePath;

public class NonConfigurableVariables {
    // 图片数量(用于随机图片索引)
    public static int MAX_PICTURE_COUNT = Util.getImageGroupNumber(ImagePath.imageGroupPath);

    // 计数器变量
    private static int step = 0;

    public static int getStep() {
        return step;
    }

    public static void setStep(int step) {
        NonConfigurableVariables.step = step;
    }
}
