package com.sandbox.aide;


import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Collections;
import java.util.List;

/**
 * 文件处理
 * Created by wl on 2021/4/20.
 */
public class FileHandle {
    private static final Logger log = LoggerFactory.getLogger(FileHandle.class);

    public final static String USER_HOME = System.getProperties().getProperty("user.home");
    public final static String HOME_SANDBOX_PATH = USER_HOME + File.separator + "sandbox";
    public final static String AGENT_JAR_PATH = HOME_SANDBOX_PATH+ File.separator + "lib"
            + File.separator + "sandbox-agent.jar";
    public final static String USER_MODULE_PATH = USER_HOME + File.separator + ".sandbox-module";
    public final static String TOKEN_FILE_PATH = USER_HOME + File.separator + ".sandbox.token";
    public final static String CORE_CONFIG_FILE_PATH = USER_HOME + File.separator + "coreServices.properties";
    public final static String REPEAT_PROPERTY_FILE_PATH = USER_MODULE_PATH + File.separator
            + "cfg/repeater.properties";

    /**
     * resource的中的sandbox.zip解压到user.home目录下
     * @return 成功：true；失败：false
     */
    public static boolean copySandbox(){
        String sandboxZipPath = HOME_SANDBOX_PATH + ".zip";
        if(copyZipFile("sandbox.zip", sandboxZipPath)){
            return unzipFile(sandboxZipPath, HOME_SANDBOX_PATH);
        }
        return false;
    }

    public static boolean copyCoreConfigFile(){
        return copyFile("coreServices.properties", CORE_CONFIG_FILE_PATH);
    }

    public static boolean copyUserModule(){
        String userModuleZipPath = USER_MODULE_PATH + ".zip";
        if(copyZipFile("sandbox-module.zip", userModuleZipPath)){
            return unzipFile(userModuleZipPath, USER_MODULE_PATH);
        }
        return false;
    }

    public static boolean copyZipFile(String resourceName, String zipFilePath){
        return copyFile(resourceName, zipFilePath);
    }

    public static List<String> readLineFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            log.info("{} not exists", file);
            return Collections.emptyList();
        }
        try {
            return FileUtils.readLines(file, "UTF-8");
        } catch (IOException e) {
            log.error("read file: {} failed", file, e);
            return Collections.emptyList();
        }
    }

    public static boolean unzipFile(String filePath, String dirPath){
        File zipFile = new File(filePath);
        File targetDir = new File(dirPath);
        if(!zipFile.exists()){
            log.error("{} not exists", zipFile);
            return false;
        }else if(targetDir.exists()){
            log.info("{} already exists, stopping unzip", targetDir);
            return true;
        }else if(!zipFile.getName().endsWith(".zip")){
            log.error("{} not zip file", zipFile);
            return false;
        }
        ZipFile zf = new ZipFile(filePath);
        try {
            zf.extractAll(dirPath);
            log.info("{} unzip successfully", zipFile.getName());
            File[] files = targetDir.listFiles();
            if(files.length==1 && targetDir.getName().equals(files[0].getName())){
                //去掉重复的文件夹，例：sandbox/sandbox/...
                for(File file: files[0].listFiles()){
                    if(file.isDirectory()){
                        FileUtils.moveDirectoryToDirectory(file, targetDir, false);
                    }else{
                        FileUtils.moveFileToDirectory(file, targetDir,false);
                    }
                }
                FileUtils.deleteDirectory(files[0]);
            }
        } catch (ZipException e) {
            log.error("{} unzip error", zipFile, e);
            return false;
        } catch (IOException e) {
            log.error("checkFile: {} failed!",targetDir, e);
            return false;
        }
        return true;
    }

    public static boolean isExists(File file){
        if(file.exists()){
            log.info("{} dir already exists", file);
            return true;
        }
        log.info("{} not exists", file);
        return false;
    }

    /**
     * copy resource文件夹内的文件
     * @param resourceName  源文件名(仅仅是存在resource目录下的文件)
     * @param newFilePath  目标文件路径(包括文件名)
     * @return
     */
    public static boolean copyFile(String resourceName, String newFilePath){
        Resource resource = new ClassPathResource(resourceName);
        File targetFile = new File(newFilePath);
        BufferedInputStream bis = null;
        if(targetFile.exists()){
            log.info("{} already exists, stopping copy", targetFile);
            return true;
        }
//        if(!targetFile.canWrite()){
//            log.error("{} No write permission, stopping copy", targetFile);
//            return false;
//        }
        try {
            InputStream inputStream = resource.getInputStream();
            bis = new BufferedInputStream(inputStream);
//            targetFile.mkdir();
            targetFile.createNewFile();

            /*通过这种方式copy的文件解压会失败*/
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
//            IOUtils.copyLarge(bis, bos);

            byte[] buf =IOUtils.toByteArray(bis);
            FileUtils.writeByteArrayToFile(targetFile, buf);
            log.info("{} copy successfully!", targetFile.getName());
//            FileUtils.copyDirectory(oldFile, targetFile);
        } catch (IOException e) {
            log.error("{} copy error! ", resourceName, e);
            return false;
        }finally {
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
        return true;
    }

}
