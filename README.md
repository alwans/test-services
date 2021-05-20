# test-services
**用于管理sandbox && repeater回放配置**  

*pom.xml文件中引用了[repeater-plugin-api](https://github.com/alwans/jvm-sandbox-repeater/tree/master/repeater-plugin-api)
和 [repeater-plugin-core](https://github.com/alwans/jvm-sandbox-repeater/tree/master/repeater-plugin-core) jar包, 需要先打包这2个jar上传 maven私服*  


*打包前需要先修改 [com.alibaba.jvm.sandbox.repeater.module.RepeaterMoudle.java](https://github.com/alwans/jvm-sandbox-repeater/blob/master/repeater-module/src/main/java/com/alibaba/jvm/sandbox/repeater/module/RepeaterModule.java)*

````
/**
* 添加一个方法
*/
private void setSystemProp(){
        //偷个懒，把app.name 设置为当前应用服务的进程id；这样就不用启动的时候额外的设置了
        System.out.println(">>>> repeater module 开始加载, 设置属性app.name && app.env <<<<");
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.setProperty("app.name", runtimeMXBean.getName().split("@")[0]);
        System.setProperty("app.env", String.valueOf(configInfo.getServerAddress().getPort()));
 }
 
````

````
/**
* 在这里调用添加的方法
*/
 @Override
    public void onLoad() throws Throwable {
        // 初始化日志框架
        LogbackUtils.init(PathUtils.getConfigPath() + "/repeater-logback.xml");
        setSystemProp(); //这里我先设置app.name：应用服务的进程id 和 app.env：sandbox的server端口
        Mode mode = configInfo.getMode();
        log.info("module on loaded,id={},version={},mode={}", com.alibaba.jvm.sandbox.repeater.module.Constants.MODULE_ID, com.alibaba.jvm.sandbox.repeater.module.Constants.VERSION, mode);
        /* agent方式启动 */
        if (mode == Mode.AGENT && Boolean.valueOf(PropertyUtil.getPropertyOrDefault(REPEAT_SPRING_ADVICE_SWITCH, ""))) {
            log.info("agent launch mode,use Spring Instantiate Advice to register bean.");
            SpringContextInnerContainer.setAgentLaunch(true);
            SpringInstantiateAdvice.watcher(this.eventWatcher).watch();
            moduleController.active();
        }
    }
````


## test-sandbox-aide
>*部署在每台应用服务器上,通过aide服务attach application server && sandbox 进行交互*  

>***配置文件: coreServices.properties***  *[path: user.home]*  
> *用于配置core-serices服务的 ip:port (默认:127.0.0.1:12309)*  

## test-core-services
>*完成 website交互 && aide服务交互*  
>*默认端口 : 12309*

## 部署流程
> *1.启动被测Demo应用：jvmTest path : [test-core-services/src/main/resource/jvmTestxxx.jar](https://github.com/alwans/test-services/blob/master/test-services-core/src/main/resources/jvmTest-0.0.1-SNAPSHOT.jar)* *java -jar jvmTestxxx.jar*

> *2.通过nginx部署website; dist.zip -> path : [test-core-service/src/resource/dis.zip](https://github.com/alwans/test-services/blob/master/test-services-core/src/main/resources/dist.zip)*  

> *3.启动 test-core-services服务*  *java -jar test-services-core-1.0.0.jar*  

> *4.web页面新增sandbox配置*  

> *4.启动aide服务*  ***java -jar -Djava.ext.dirs=${JAVA_HOME}/jre/lib/ext:${JAVA_HOME}/lib/ test-sandbox-aide-1.0.0.jar***  
