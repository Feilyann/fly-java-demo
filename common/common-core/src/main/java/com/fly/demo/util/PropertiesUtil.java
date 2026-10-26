package com.fly.demo.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
public class PropertiesUtil {

    public static Properties loadProperties(String name, String systemParam) {
        Properties properties = new Properties();
        try {
            String configPath = System.getProperty(systemParam);
            log.info("加载配置文件: {} = {}", systemParam, configPath);
            if (StrUtil.isBlankIfStr(configPath)) {
                properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(name));
            } else {
                if (configPath.startsWith("file:../")) {
                    String userDir = System.getProperty("user.dir").replaceAll("\\\\", "/");
                    File userDirFile = new File(userDir);
                    String parentFile = userDirFile.getParent();
                    configPath = parentFile + "/" + configPath.replaceFirst("file:\\.\\./", "");
                } else if (configPath.startsWith("file:/")) {
                    configPath = configPath.replaceFirst("file:", "");
                }
                configPath = configPath.replaceAll("\\\\", "/");
                if (!Files.exists(Paths.get(configPath))) {
                    throw new FileNotFoundException("未找到文件: " + configPath);
                }
                properties.load(Files.newInputStream(Paths.get(configPath)));
            }
            log.info("配置文件 [{}] 加载完成: {}", name, properties);
            return properties;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static Properties loadProperties() {
        return loadProperties("application.properties", "application.config");
    }
}
