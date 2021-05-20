# test-services
**用于管理sandbox && repeater回放配置**  

*pom.xml文件中引用了[repeater-plugin-api](https://github.com/alwans/jvm-sandbox-repeater/tree/master/repeater-plugin-api)
和 [repeater-plugin-core](https://github.com/alwans/jvm-sandbox-repeater/tree/master/repeater-plugin-core) jar包, 需要先打包这2个jar上传 maven私服*

## test-sandbox-aide
>*部署在每台应用服务器上,通过aide服务attach application server && sandbox 进行交互*  

>***配置文件: coreServices.properties***  *[path: user.home]*  
> *用于配置core-serices服务的 ip:port (默认:127.0.0.1:12309)*  

## test-core-services
>*完成 website交互 && aide服务交互*  
>*默认端口 : 12309*

## 部署流程
> *1.通过nginx部署website; dist.zip -> path : [test-core-service/src/resource/dis.zip](https://github.com/alwans/test-services/blob/master/test-services-core/src/main/resources/application.properties)*  

> *2.启动 test-core-services服务*  *java -jar test-services-core-1.0.0.jar*  

> *3.web页面新增sandbox配置*  

> *4.启动aide服务*  ***java -jar -Djava.ext.dirs=${JAVA_HOME}/jre/lib/ext:${JAVA_HOME}/lib/ test-sandbox-aide-1.0.0.jar***  
