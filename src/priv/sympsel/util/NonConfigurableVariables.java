package priv.sympsel.util;

import lombok.Data;
import lombok.Getter;
import priv.sympsel.resource.Path;

@Data
public class NonConfigurableVariables {
    // 图片数量(用于随机图片索引)
    public static int MAX_PICTURE_COUNT = Util.getImageGroupNumber(Path.imageGroupPath);

    // 计数器变量
    @Getter
    private static int step = 0;
    // 记录
    @Getter
    private static String userToSave = "test";
    @Getter
    private static int status = 0;

    public static void setStep(int step) {
        NonConfigurableVariables.step = step;
    }

    public static void setStatus(int status) {
        NonConfigurableVariables.status = status;
    }

    public static void setUserToSave(String userToSave) {
        NonConfigurableVariables.userToSave = userToSave;
    }

}
