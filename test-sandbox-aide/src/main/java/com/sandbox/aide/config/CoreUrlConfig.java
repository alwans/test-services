package com.sandbox.aide.config;

import com.sandbox.aide.FileHandle;
import com.sandbox.aide.util.PropertyUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoreUrlConfig {

    private static Logger log = LoggerFactory.getLogger(CoreUrlConfig.class);

    //默认的service-core 服务地址
    private static String base_url = "127.0.0.1:12309";


    static {
        //set core-services url
        base_url = PropertyUtil.getPropertyOrDefault("coreServicesUrl", "127.0.0.1:12309");
        log.info("更新coreServicesUrl: {}", base_url);
        //set repeat rul
        try {
            List<String> lines = FileUtils.readLines(new File(FileHandle.REPEAT_PROPERTY_FILE_PATH), "UTF-8");
            List<String> new_lines = new ArrayList<>(lines.size());
            for(String line: lines){
                if(line.contains("127.0.0.1:8001")){
                    line = line.replace("127.0.0.1:8001", base_url);
                }
                new_lines.add(line);
            }
            FileUtils.writeLines(new File(FileHandle.REPEAT_PROPERTY_FILE_PATH), new_lines, false);
        } catch (IOException e) {
            log.error("读取{} 文件失败", FileHandle.REPEAT_PROPERTY_FILE_PATH);
        }
    }


    public static String upload_port_url = String.format("http://%s/api/upload/port", base_url);

    public static String upload_sandbox_url = String.format("http://%s/api/upload/sandbox/removeList", base_url);

    public static String pullSandboxUrl = String.format("http://%s/api/pull/sandbox/list", base_url);


}
