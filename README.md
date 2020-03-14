# 基于simple-robot与CQ HTTP API插件的Java开发框架
[![](https://img.shields.io/badge/simple--robot-core-green)](https://github.com/ForteScarlet/simple-robot-core)  [![img](https://camo.githubusercontent.com/f8464f5d605886b8369ab6daf28d7130a72fd80e/68747470733a2f2f696d672e736869656c64732e696f2f6d6176656e2d63656e7472616c2f762f696f2e6769746875622e466f727465536361726c65742f73696d706c652d726f626f742d636f7265)](https://search.maven.org/artifact/io.github.ForteScarlet/simple-robot-core) [![](https://img.shields.io/maven-central/v/io.github.ForteScarlet.simple-robot-core/component-forcoolqhttpapi)](https://search.maven.org/artifact/io.github.ForteScarlet.simple-robot-core/component-forcoolqhttpapi)  [![](https://img.shields.io/badge/%E7%9C%8B%E4%BA%91%E6%96%87%E6%A1%A3-doc-green)](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc)  [![](https://img.shields.io/badge/QQ%E7%BE%A4-782930037-blue)](https://jq.qq.com/?_wv=1027&k=57ynqB1)  

> 如果需要获得更好的阅读体验，请前往 [原文档](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc/) -> `组件-酷Q-CoolQ HTTP API(推荐)` -> `快速开始` 处阅读
> 或尝试直接进入[快速开始](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc/1519585)

**点击项目右上角的`star`展示完整文档**

# 快速开始

*****

<br>

- ## 入门级项目搭建视频教程： [av96117517](https://www.bilibili.com/video/av96117517)
网址：[https://www.bilibili.com/video/av96117517](https://www.bilibili.com/video/av96117517)

<iframe src="//player.bilibili.com/player.html?aid=96117517&cid=164081604&page=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true" height=600 width=700> </iframe>

## 一、**安装**

### 1\. **下载并安装 酷Q**

前往酷Q[官方下载地址](https://cqp.cc/t/23253)下载酷Q应用，并安装（启动一次），然后关闭。

<br>


### 2\. **下载并安装 CQ HTTP API插件**

**①.** 前往`CQ HTTP API`的 [releases ](https://github.com/richardchien/coolq-http-api/releases) 下载最新版本。
**②.** 将下载好的`.cpk`格式文件移动至`酷Q`根目录下的`/app`文件夹下。此时你**有可能**会发现此目录下已经存在一些`.cpk`文件了，那可能是酷Q应用下载的时候自带的。你可以选择保留它们，也可以选择删除它们。
**③.** 启动一次酷Q程序，右键酷Q标志，选择：`应用 > 应用管理`，如图所示：
![](https://i.vgy.me/QpgBpK.png)
然后将会出现应用管理界面，选择HTTP API插件并选择启用，如图所示：
![](https://i.vgy.me/bGIXVP.png)
默认情况下，插件会弹出一个黑窗口，并输出日志信息。此时如果你关闭黑窗口，酷Q可能会崩溃并提示错误信息。**不过没有关系**，可以先暂时关闭它并暂时关闭酷Q。安装完插件后的此次启动主要目的是为了让插件自动生成一次配置文件。

>[info] 如果你有信心，可以选择直接查看CQ HTTP API插件的 [配置文档](https://cqhttp.cc/docs/4.14/#/Configuration) 进行手动配置。

<br>


### 3\. **创建Java项目**

你可以使用一切支持的方式来自动构建项目，以下将会举几个例子：

>[info] 版本号请自行替换为 Maven仓库中的最新版本：[![](https://img.shields.io/maven-central/v/io.github.ForteScarlet.simple-robot-core/component-forcoolqhttpapi)](https://search.maven.org/artifact/io.github.ForteScarlet.simple-robot-core/component-forcoolqhttpapi)

#### **①. Maven**

```xml
<dependency>
    <groupId>io.github.ForteScarlet.simple-robot-core</groupId>
    <artifactId>component-forcoolqhttpapi</artifactId>
    <version>${version}</version>
</dependency>
```

#### **②. Gradle**

```
compile group: 'io.github.ForteScarlet.simple-robot-core', name: 'component-forcoolqhttpapi', version: '${version}'
```

#### **③. Grape**

```
@Grapes(
    @Grab(group='io.github.ForteScarlet.simple-robot-core', module='component-forcoolqhttpapi', version='${version}')
)
```

## 二、 **使用**
## **4\. 配置**

有两个地方需要你进行配置：

* **酷Q的`CQ HTTP API`**
* **你需要启动的Java程序**

OK，让我们一个一个来。

### **①. CQ HTTP API插件配置**

首先，这里给出`CQ HTTP API`插件配置的官方说明文档地址：[配置文档](https://cqhttp.cc/docs/4.14/#/Configuration)
如果你有能力根据文档自己进行配置，可以选择跳过此小节。
其次，上面提到了启动一次应用，可以自动生成插件的一个配置文件，此文件位于`{酷Q根目录}\data\app\io.github.richardchien.coolqhttpapi`下的`config.ini`文件。

>[warning] 如果没有，那么请参照官方配置文档进行寻找。

你需要将文件内容大致修改为如下内容：

>[warning] 其中标注${....}的参数请**根据描述自行修改为想要设置的值，且暂时记住它们**，后续的Java配置中会用到。

```ini
[general]
host = 0.0.0.0
post_url = http://${java程序所在IP}:${Java程序监听端口}${Java程序Http服务请求路径}
port = ${酷Q插件监听的端口}
```

>[info] 其余参数可查看文档并根据需求选填。框架**暂时不支持**请求头验证，所以请暂时不要开启`access_token`与`secret`参数，且`post_message_format`参数也请使用默认，即`string`。

### **②. Java配置**

Java配置有两种方式：

* **代码配置**
* **文件配置**

>[warning] 由于目前文件配置尚比较繁琐，便暂时先只介绍代码配置的方式。如果对目前的较为繁琐的文件配置有兴趣，请在了解代码配置的情况下查看 [文件配置方式](./CQHTTPAPI%E6%96%87%E4%BB%B6%E9%85%8D%E7%BD%AE.md)

首先，创建一个类，实现`com.forte.component.forcoolqhttpapi.CoolQHttpApp`接口，并实现接口中的`before`与`after`方法。

>[info] 现在假定你这个类叫做**`RunApp`**, 方便后续的代称。当然，它实际上叫做什么都无所谓。

可以发现，`before`方法中存在一个叫做`CoolQHttpConfiguration`的参数，我们就要通过这个参数对象进行配置。
以下我将会列举**最常见的**几项配置信息，而全面的配置可选项请查看[核心通用配置](./%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6.md) 与 [组件额外配置](./%E7%BB%84%E4%BB%B6CoolQ%20HTTP%20API%E9%85%8D%E7%BD%AE.md)

>[warning] 还记得上面提到的${...}替换吗？这里也一样哦~而且注意，如果${...}的提示信息是一样的，那么它们的值也应该是一样的哦！

```java
configuration.setIp("${酷Q所在的IP}");
configuration.setJavaPort(${Java程序监听端口});
configuration.setServerPort(${酷Q插件监听的端口});
// 默认为一个斜杠"/"
configuration.setServerPath("${Java程序Http服务请求路径}");
```

### **③. 举个例子**

此处我会给上述所有的`${...}`赋上一个值，来举个例子。
假如：

```properties
${java程序所在IP}=192.168.0.1
${Java程序监听端口}=15514
${Java程序Http服务请求路径}=/coolq
${酷Q插件监听的端口}=5700
${酷Q所在的IP}=192.168.101.101
```

那么两边的对应配置就分别为：
酷Q：

```ini
[general]
host = 0.0.0.0
post_url = http://192.168.0.1:15514/coolq
port = 5700
```

Java：

```java
configuration.setIp("192.168.101.101");
configuration.setJavaPort(15514);
configuration.setServerPort(5700);
configuration.setServerPath("/coolq");
```

## **5. 运行**

历尽千辛万苦，终于到了这一步。
首先，新建一个`main`方法在任意地方。
写下以下代码，并且别忘了那个`RunApp`实际上代表了什么：
```java
CoolQHttpApplication application = new CoolQHttpApplication();
// 启动
application.run(new RunApp());
```
>[success] 如果这时候一看到控制台所输出的日志中最终出现了你的机器人的一些信息，那么恭喜，这说明你已经发送、获取、设置消息了。

那么能不能监听到消息呢？写一些代码来测试一下吧。

## **6. 第一个监听器**
### **①. 新建一个类**
>[info] 尽量在`RunApp`的同级目录或者子级目录下创建。
创建好之后，在这个类上标注一个注解：`@com.forte.qqrobot.anno.depend.Beans`, 即`@Beans`
### **②. 写一个监听私信的方法**
我们写一个监听私信消息满足正则：`hello.*`的私信消息监听函数，且当我们收到消息后，复读。
完整代码如下：
```java
@Beans
public class TestListener {

    @Listen(MsgGetTypes.privateMsg)
    @Filter("hello.*")
    public void testListen1(PrivateMsg msg, MsgSender sender) {
        System.out.println(msg);
        // 以下三种方法均可
，效果相同
        sender.SENDER.sendPrivateMsg(msg, msg.getMsg());
//        sender.SENDER.sendPrivateMsg(msg.getQQ(), msg.getMsg());
//        sender.SENDER.sendPrivateMsg(msg.getQQCode(), msg.getMsg());
    }
}
```
### **③. 在来一次**
这次我们再启动一次，如果发现启动日志中成功加载了这个监听函数，那就试着给你的机器人发送一句`hello world`吧。
如果它也回复了你一句`hello world`，那么说明至此你已经成功了，探索文档所提供的众多功能并实现你的机器人吧~


## **7. 失败了？**
如果跟着上述流程完整无误的操作却无法成功，也不要气馁，尝试根据[常见问题汇总](./常见问题汇总.md)进行排查或者加入QQ群`782930037`进行咨询。





