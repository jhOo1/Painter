package Painter;
import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configuration {
    private Properties config;
    private String position;
    public Configuration(String position) {
        // 创建Properties对象
        config = new Properties();
        this.position =position;
        // 加载配置文件
        try {
            config.load(new FileInputStream(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getValue(String key) {
        // 从配置文件中获取指定key的值
        return config.getProperty(key);
    }
    public String getValue(String key,String section) throws IOException {
        String strLine, value = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(position));
        boolean isInSection = false;
        try {
            while ((strLine = bufferedReader.readLine()) != null) {
                strLine = strLine.trim();
                //strLine = strLine.split("[;]")[0];
                Pattern p;
                Matcher m;
                p = Pattern.compile("\\[\\s*.*\\s*\\]");
                m = p.matcher((strLine));
                if (m.matches()) {
                    p = Pattern.compile("\\[\\s*" + section + "\\s*\\]");
                    m = p.matcher(strLine);
                    if (m.matches()) {
                        isInSection = true;
                    } else {
                        isInSection = false;
                    }
                }
                if (isInSection == true) {
                    strLine = strLine.trim();
                    String[] strArray = strLine.split("=");
                    if (strArray.length == 1) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(key)) {
                            value = "";
                            return value;
                        }
                    } else if (strArray.length == 2) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(key)) {
                            value = strArray[1].trim();
                            return value;
                        }
                    } else if (strArray.length > 2) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(key)) {
                            value = strLine.substring(strLine.indexOf("=") + 1).trim();
                            return value;
                        }
                    }
                }
            }
        } finally {
            bufferedReader.close();
        }
        return "";
    }
}

