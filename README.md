# 基于simple-robot与CQ HTTP API插件的Java开发框架
[![](https://img.shields.io/badge/simple--robot-core-green)](https://github.com/ForteScarlet/simple-robot-core)  [![img](https://camo.githubusercontent.com/f8464f5d605886b8369ab6daf28d7130a72fd80e/68747470733a2f2f696d672e736869656c64732e696f2f6d6176656e2d63656e7472616c2f762f696f2e6769746875622e466f727465536361726c65742f73696d706c652d726f626f742d636f7265)](https://search.maven.org/artifact/io.github.ForteScarlet/simple-robot-core) [![](https://img.shields.io/maven-central/v/io.github.ForteScarlet.simple-robot-core/component-forcoolqhttpapi)](https://search.maven.org/artifact/io.github.ForteScarlet.simple-robot-core/component-forcoolqhttpapi)  [![](https://img.shields.io/badge/%E7%9C%8B%E4%BA%91%E6%96%87%E6%A1%A3-doc-green)](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc)  [![](https://img.shields.io/badge/QQ%E7%BE%A4-782930037-blue)](https://jq.qq.com/?_wv=1027&k=57ynqB1)  

> 如果需要获得更好的阅读体验，请前往 
>[原文档 http://simple-robot-doc.forte.love/](http://simple-robot-doc.forte.love/) -> `组件-酷Q-CoolQ HTTP API(推荐)` -> `快速开始` 处阅读
>或
>[https://www.kancloud.cn/forte-scarlet/simple-coolq-doc/](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc/) -> `组件-酷Q-CoolQ HTTP API(推荐)` -> `快速开始` 处阅读
> 或尝试直接进入[快速开始](https://www.kancloud.cn/forte-scarlet/simple-coolq-doc/1519585)

**点击项目右上角的`star`展示完整文档**

# 通告

酷Q已停止维护，因此cqhttp插件也将无法继续在酷Q平台运行。但是幸运的是，作为一款http交互的插件，
只要能够提供与cqhttp相同的交互API，此组件便依旧可以使用。


# 快速开始

*****
>[success] 以下教程以版本`0.7-BETA-1.7.0`**以上**的版本为准（不包括`0.7-BETA-1.7.0`），即核心`1.8.0`以上版本。

<br>


## 一、**安装**

### 1\. **下载并安装 酷Q**

前往酷Q[官方下载地址](https://cqp.cc/t/23253)下载酷Q应用，并安装（启动一次），然后关闭。

<br>


### 2\. **下载并安装 CQ HTTP API插件**

**①.** 前往`CQ HTTP API`的 [releases ](https://github.com/richardchien/coolq-http-api/releases) 下载最新版本。
**②.** 将下载好的`.cpk`格式文件移动至`酷Q`根目录下的`/app`文件夹下。
**③.** 启动一次酷Q程序，右键酷Q标志，选择：`应用 > 应用管理`，如图所示：
![](https://i.vgy.me/QpgBpK.png)
然后将会出现应用管理界面，选择HTTP API插件并选择启用，如图所示：
![](https://i.vgy.me/bGIXVP.png)
默认情况下，插件会弹出一个黑窗口，并输出日志信息。此时如果你关闭黑窗口，酷Q可能会崩溃并提示错误信息。**不过没有关系**，可以先暂时关闭它并暂时关闭酷Q。安装完插件后的此次启动主要目的是为了让插件自动生成一次配置文件。

>[info] 如果你有信心，可以选择直接查看CQ HTTP API插件的 [配置文档](https://cqhttp.cc/docs/4.14/#/Configuration) 进行手动配置。

<br>


### 3\. **创建Java项目**
（此教程为核心1.8.x以上版本。如何判断核心版本参考[核心版本系与升级](.组件如何升级核心.md/)）
你可以使用一切支持的方式来自动构建项目，以下将会举几个例子：

>[info]maven仓库地址参考：
> ①. https://search.maven.org/artifact/io.github.ForteScarlet.simple-robot-core/component-forcoolqhttpapi
> ②. https://mvnrepository.com/artifact/io.github.ForteScarlet.simple-robot-core/component-forcoolqhttpapi
 
#### **①. Maven**

```xml
        <dependency>
            <groupId>io.github.ForteScarlet.simple-robot-core</groupId>
            <artifactId>component-forcoolqhttpapi</artifactId>
            <version>1.2.1-1.14</version>
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

## **二. 使用**

## **4\. 配置**

有两个地方需要你进行配置：

* **酷Q的`CQ HTTP API`**
* **你需要启动的Java程序**

OK，让我们一个一个来。

### **①. CQ HTTP API插件配置**

首先，这里给出`CQ HTTP API`插件配置的官方说明文档地址：[配置文档](https://cqhttp.cc/docs/4.14/#/Configuration)
如果你有能力根据文档自己进行配置，可以选择跳过此小节。
其次，上面提到了启动一次应用，可以自动生成插件的一个配置文件，此文件可能位于：

* `{酷Q根目录}\data\app\io.github.richardchien.coolqhttpapi`下的`config.ini`文件。
* `{酷Q根目录}\data\app\io.github.richardchien.coolqhttpapi`下的`config.json`文件。
* `{酷Q根目录}\data\app\io.github.richardchien.coolqhttpapi\config`下的`{你机器人的QQ号}.json`文件。


>[warning] 如果没有，那么请参照官方配置文档进行寻找。

你需要将文件内容大致修改为如下内容：

>[warning] 其中标注${....}的参数请**根据描述自行修改为想要设置的值，且暂时记住它们**，后续的Java配置中会用到。

使用`.ini`格式举例：
```ini
[general]
host = 0.0.0.0
post_url = http://${java程序所在IP}:${Java程序监听端口}${Java程序Http服务请求路径}
port = ${酷Q插件监听的端口}
```

>[info] 其余参数可查看文档并根据需求选填。框架**暂时不支持**请求头验证，所以请暂时不要开启`access_token`与`secret`参数，且`post_message_format`参数也请使用默认，即`string`。

### **②. Java配置**

Java配置有以下几种方式：

* **代码配置**
* **文件配置**
* **注解配置（需要核心1.8.0+）**

>[warning] 由于此教程为使用核心`1.8.0`以上的版本，所以使用配置文件+注解配置的形式。 全部配置内容参考 [文件配置](./CQHTTPAPI文件配置.md)

首先，创建一个类。

此时你可以**选择**：

- 去实现`com.forte.component.forcoolqhttpapi.CoolQHttpApp`接口，并实现接口中的`before`与`after`方法。这个接口是cqhttp组件中提供的接口。

- 去实现`com.forte.qqrobot.Application`接口，并实现接口中的`before`与`after`方法。这个是核心提供的接口，任何组件中都存在。

- 不去实现任何接口。

>[info] 现在假定你这个类叫做 **`RunApp`** , 方便后续的代称。当然，它实际上叫做什么都无所谓。

然后在项目的`resources`路径下创建一个配置文件`conf.properties`，填入如下参数：
>[info] 全面的配置可选项请查看[核心通用配置](./%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6.md) 与 [组件额外配置](./%E7%BB%84%E4%BB%B6CoolQ%20HTTP%20API%E9%85%8D%E7%BD%AE.md)

```properties
################################
### 核心所提供的公共配置, 一般情况下不会存在变化
################################
core.bots=:http://${酷Q所在的IP}:${酷Q插件监听的端口}

# 日志等级
core.logLevel=DEBUG

################################
### 组件所提供的额外配置
################################

cqhttp.javaPort=${Java程序监听端口}
cqhttp.serverPath=${Java程序Http服务请求路径}
```
>[info] 还记得上面提到的${...}替换吗？这里也一样哦~而且注意，如果${...}的提示信息是一样的，那么它们的值也应该是一样的哦！

写完配置文件后，在你的那个`RunApp`类上标注一个注解：`@SimpleRobotApplication(resources = "conf.properties")`，其中，`resources`参数即代表你的配置文件在resouces路径下的相对路径哦。
就像这样：
```java
@SimpleRobotApplication(resources = "conf.properties")
public class RunApp { //... }
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
酷Q插件配置文件：

```ini
[general]
host = 0.0.0.0
post_url = http://192.168.0.1:15514/coolq
port = 5700
```

java配置文件：

```properties
core.bots=:http://192.168.101.101:5700
core.logLevel=DEBUG
cqhttp.javaPort=15514
cqhttp.serverPath=/coolq
```

>[success] cqhttp组件的更多java配置(例如token相关)可查看此组件文档的`配置`章节

## **5\. 运行**

历尽千辛万苦，终于到了这一步。
<br>
**首先，启动酷Q程序与插件。**
<br>
然后，新建一个`main`方法在任意地方。
以下方式择其一，并且别忘了那个`RunApp`实际上代表了什么。我会在每个启动方式下简单解释他们之间的差异。
<br>

**方法一：**
```java
CoolQHttpApplication application = new CoolQHttpApplication();
// 启动, 因为没有继承接口，并且使用注解，于是直接使用RunApp的类进行启动就好了
application.run(RunApp.class, args);
```
方法一使用`CoolQHttpApplication`启动，此类是cqhttp组件所提供的启动器，使用它启动可以保证在cqhttp组件环境下不会出现找不到启动器的问题，但是相对兼容性下降。（例如更换组件的时候，启动器的代码也需要修改）

<br>

**方法二：**

```java
// 通过runAuto方法启动，自动寻找启动器
BaseApplication.runAuto(RunApp.class, args);
```
方法二使用`BaseApplication`的静态方法runAuto启动，此类是核心提供的父启动类，任何组件环境下都存在。使用它可以增加项目兼容性，使得启动代码在任何组件环境下都可以生效。（例如更换组件依赖后，启动器代码无需做出修改）
但是自动寻找必然存在找不到启动器的隐患。


>[success] 如果这时候一看到控制台所输出的日志中最终出现了你的机器人的一些信息，那么恭喜，这说明你已经发送、获取、设置消息了。

那么能不能监听到消息呢？写一些代码来测试一下吧。

## **6\. 第一个监听器**

### **①. 新建一个类**

>[info] 尽量在`RunApp`的同级目录或者子级目录下创建。
> 创建好之后，在这个类上标注一个注解：`@com.forte.qqrobot.anno.depend.Beans`, 即`@Beans`

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
        // 以下三种方法均可，效果相同
        sender.SENDER.sendPrivateMsg(msg, msg.getMsg());
//        sender.SENDER.sendPrivateMsg(msg.getQQ(), msg.getMsg());
//        sender.SENDER.sendPrivateMsg(msg.getQQCode(), msg.getMsg());
    }
}
```

### **③. 再来一次**

这次我们再启动一次，如果发现启动日志中成功加载了这个监听函数，那就试着给你的机器人发送一句`hello world`吧。
如果它也回复了你一句`hello world`，那么说明至此你已经成功了，探索文档所提供的众多功能并实现你的机器人吧~
消息截取、自定义过滤器、监听函数拦截器、定时任务......几乎可以满足你的所有开发需求！
如果有什么疑问，别忘了可以加入群聊`782930037`提问哦。
## **7\. 失败了？**

如果跟着上述流程完整无误的操作却无法成功，也不要气馁，尝试根据[常见问题汇总](./%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98%E6%B1%87%E6%80%BB.md)进行排查或者加入QQ群`782930037`进行咨询。




