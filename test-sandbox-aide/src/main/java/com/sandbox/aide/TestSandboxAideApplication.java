package com.sandbox.aide;

import com.sandbox.aide.controller.PullData;
import com.sandbox.aide.controller.UploadData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class TestSandboxAideApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(TestSandboxAideApplication.class, args);
		//初始化sandbox，如果初始化失败，服务停止
		if(!SandboxMgr.getInstance().initSandbox()){
			ctx.close();
		}
		//初始化core-services配置文件
		if(!FileHandle.copyCoreConfigFile()){
			ctx.close();
		}
//		//上报端口
		new UploadData().upload_port(ctx);

//		//拉取当前服务器上已启动的所有sandbox数据
		new PullData().pullSandboxList(ctx);

		//设置属性值
		SandboxConst.getInstance().setProperties(ctx);
	}

}
