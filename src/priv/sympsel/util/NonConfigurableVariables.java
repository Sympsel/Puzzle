package priv.sympsel.util;

import priv.sympsel.resource.ImagePath;

public class NonConfigurableVariables {
    // 图片数量(用于随机图片索引)
    public static int MAX_PICTURE_COUNT = Util.getImageGroupNumber(ImagePath.imageGroupPath);
}
