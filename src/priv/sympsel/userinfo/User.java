package priv.sympsel.userinfo;

import cn.hutool.core.io.FileUtil;
import lombok.Data;

import java.io.File;
import java.io.Serial;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class User implements java.io.Serializable {
    private String username;
    private String password;
    @Serial
    private static final long serialVersionUID = 1L;

    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }

    public User(String line) {
        String regex = "username=([a-zA-Z0-9]+)&password=([a-zA-Z0-9]+)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        if(!m.matches()) {
            System.err.println("Invalid regex");
            return;
        }
        String[] str = line.split("[&=]");
        this.username = str[1];
        this.password = str[3];
    }

    @Override
    public String toString() {
        return String.format("username=%s&password=%s", username, password);
    }

    public static void readUserInfoFormated(List<User> userList) {
        String path = "src/priv/sympsel/userinfo/userinfo.txt";
        // 备份
        String copied = "src/priv/sympsel/userinfo/userinfocopy.txt";
        File f = new File(path);
        File f_copy = new File(copied);
        FileUtil.copy(f.getAbsolutePath(),f_copy.getAbsolutePath(), true);
        List<String> userInfoFormated = FileUtil.readUtf8Lines(f.getAbsolutePath());
        for (String s : userInfoFormated) {
            userList.add(new User(s));
        }
    }
}
