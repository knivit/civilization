package com.tsoft.civilization.util;

import com.tsoft.civilization.Main;

import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

public class SystemProperties {
    public static final String SERVER_PORT = "server.port";

    public void init() {
        init("application.properties");
    }

    public void init(String fileName) {
        Properties props = readSystemProperties(fileName);
        setSystemProperties(props);
    }

    private Properties readSystemProperties(String fileName) {
        Properties props = new Properties();

        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is != null) {
                props.load(is);
            }
        } catch (Exception ex) {
            System.err.println("Can't read " + fileName);
            ex.printStackTrace();
        }

        return props;
    }

    private void setSystemProperties(Properties props) {
        for (Object key : Collections.list(props.keys())) {
            Object value = props.get(key);
            if (value != null) {
                System.setProperty(key.toString(), value.toString());
            }
        }
    }
}
