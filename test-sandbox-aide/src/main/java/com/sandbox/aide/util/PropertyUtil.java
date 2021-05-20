package com.sandbox.aide.util;

import com.sandbox.aide.FileHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * {@link PropertyUtil} 属性操作
 * <p>
 *
 * @author zhaoyb1990
 */
public class PropertyUtil {

    private static Logger log = LoggerFactory.getLogger(PropertyUtil.class);

    /**
     * 获取系统属性带默认值
     *
     * @param key          属性key
     * @param defaultValue 默认值
     * @return 属性值 or 默认值
     */
    public static String getSystemPropertyOrDefault(String key, String defaultValue) {
        String property = System.getProperty(key);
        return StringUtils.isEmpty(property) ? defaultValue : property;
    }

    private static Properties properties = new Properties();
    private static Properties repeatProperties = new Properties();

    static {
        try {
            InputStream is = new FileInputStream(new File(FileHandle.CORE_CONFIG_FILE_PATH));
            properties.load(is);
        } catch (Exception e) {
            log.error("Failed to load {} file",FileHandle.CORE_CONFIG_FILE_PATH, e);
            // cause this class will be load in repeater console, use this hard code mode to solve compatibility problem.
        }
    }


    /**
     * 获取coreServices.properties的配置信息
     *
     * @param key          属性key
     * @param defaultValue 默认值
     * @return 属性值 or 默认值
     */
    public static String getPropertyOrDefault(String key, String defaultValue) {
        String property = properties.getProperty(key);
        return StringUtils.isEmpty(property) ? defaultValue : property;
    }


}
