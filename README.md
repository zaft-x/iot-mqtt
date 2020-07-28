# my iot-mqtt  


# 项目目录
* [mqtt简介](#1)
* [功能](#2)
 * [如何使用](#3)
## <a name="1">mqtt简介</a>
 MQTT 协议是 IBM 开发的即时通讯协议，相对于 IM 的实际上的准标准协议 XMPP 来说，MQTT 更小，更快，更轻量。MQTT 适合于任何计算能力有限，工作在低带宽、不可靠的网络中的设备，包括手机，传感器等等。
## <a name="2">功能</a>
**## 服务端  example(mqttConsumer)**
### 已实现
* 订阅功能
 #### <a name="3">如何使用</a>
* 安装lombok插件  
* 下载源码
* springboot
* jdk8
* 导入IDE
* 配置yml 或者properties 文件 [yml](src/main/resources/application.yaml)  
* 简单测试：运行包 test 下的 测试 文件，即可开启测试客户端。
* 压力测试：推荐使用jmeter 的mqtt插件 [插件](https://github.com/tuanhiep/mqtt-jmeter)
 
init my fist iot-mqtt
一个springboot project
include subscription



