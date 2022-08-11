# README

## 博客介绍

基于Springboot + Vue 开发的前后端分离博客

![https://img.shields.io/hexpm/l/plug.svg](https://img.shields.io/hexpm/l/plug.svg)

![https://img.shields.io/badge/JDK-1.8+-green.svg](https://img.shields.io/badge/JDK-1.8+-green.svg)

[](https://img.shields.io/badge/springboot-2.4.2.RELEASE-green)[https://img.shields.io/badge/springboot-2.4.2.RELEASE-green](https://img.shields.io/badge/springboot-2.4.2.RELEASE-green)

[](https://img.shields.io/badge/vue-2.5.17-green)[https://img.shields.io/badge/vue-2.5.17-green](https://img.shields.io/badge/vue-2.5.17-green)

[](https://img.shields.io/badge/mysql-8.0.20-green)[https://img.shields.io/badge/mysql-8.0.20-green](https://img.shields.io/badge/mysql-8.0.20-green)

[](https://img.shields.io/badge/mybatis--plus-3.4.0-green)[https://img.shields.io/badge/mybatis--plus-3.4.0-green](https://img.shields.io/badge/mybatis--plus-3.4.0-green)

[](https://img.shields.io/badge/redis-6.0.5-green)[https://img.shields.io/badge/redis-6.0.5-green](https://img.shields.io/badge/redis-6.0.5-green)

[](https://img.shields.io/badge/elasticsearch-7.9.2-green)[https://img.shields.io/badge/elasticsearch-7.9.2-green](https://img.shields.io/badge/elasticsearch-7.9.2-green)

[](https://img.shields.io/badge/rabbitmq-3.8.5-green)[https://img.shields.io/badge/rabbitmq-3.8.5-green](https://img.shields.io/badge/rabbitmq-3.8.5-green)

[在线地址](about:blank#%E5%9C%A8%E7%BA%BF%E5%9C%B0%E5%9D%80) | [目录结构](about:blank#%E7%9B%AE%E5%BD%95%E7%BB%93%E6%9E%84) | [项目特点](about:blank#%E9%A1%B9%E7%9B%AE%E7%89%B9%E7%82%B9) | [技术介绍](about:blank#%E6%8A%80%E6%9C%AF%E4%BB%8B%E7%BB%8D) | [运行环境](about:blank#%E8%BF%90%E8%A1%8C%E7%8E%AF%E5%A2%83) | [开发环境](about:blank#%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83) | [项目截图](about:blank#%E9%A1%B9%E7%9B%AE%E6%88%AA%E5%9B%BE) | [快速开始](about:blank#%E5%BF%AB%E9%80%9F%E5%BC%80%E5%A7%8B) | [注意事项](about:blank#%E6%B3%A8%E6%84%8F%E4%BA%8B%E9%A1%B9) | [项目总结](about:blank#%E9%A1%B9%E7%9B%AE%E6%80%BB%E7%BB%93) | [交流群](about:blank#%E4%BA%A4%E6%B5%81%E7%BE%A4)

## 在线地址

**项目链接：** [www.talkxj.com](https://www.talkxj.com/)

**后台链接：** [admin.talkxj.com](https://admin.talkxj.com/)

测试账号：test@qq.com，密码：1234567，可登入后台查看。

**Github地址：** [](https://github.com/X1192176811/blog)[https://github.com/X1192176811/blog](https://github.com/X1192176811/blog)

**Gitee地址：** [](https://gitee.com/feng_meiyu/blog)[https://gitee.com/feng_meiyu/blog](https://gitee.com/feng_meiyu/blog)

**在线接口文档地址：** [](https://www.talkxj.com/api/doc.html)[https://www.talkxj.com/api/doc.html](https://www.talkxj.com/api/doc.html)

您的star是我坚持的动力，感谢大家的支持，欢迎提交pr共同改进项目。

## 目录结构

前端项目位于blog-vue下，blog为前台，admin为后台。

后端项目位于blog-springboot下。

SQL文件位于根目录下的blog-mysql8.sql，需要MYSQL8以上版本。

可直接导入该项目于本地，修改后端配置文件中的数据库等连接信息，项目中使用到的关于阿里云功能和第三方授权登录等需要自行开通。

当你克隆项目到本地后可使用邮箱账号：admin@qq.com，密码：1234567 进行登录，也可自行注册账号并将其修改为admin角色。

本地访问接口文档地址：[](http://127.0.0.1:8080/doc.html)[http://127.0.0.1:8080/doc.html](http://127.0.0.1:8080/doc.html)

**ps：请先运行后端项目，再启动前端项目，前端项目配置由后端动态加载。**

```
blog-springboot
├── annotation    --  自定义注解
├── aspect        --  aop模块
├── config        --  配置模块
├── constant      --  常量模块
├── consumer      --  MQ消费者模块
├── controller    --  控制器模块
├── dao           --  框架核心模块
├── dto           --  dto模块
├── enums         --  枚举模块
├── exception     --  自定义异常模块
├── handler       --  处理器模块（扩展Security过滤器，自定义Security提示信息等）
├── service       --  服务模块
├── strategy      --  策略模块（用于扩展第三方登录，搜索模式，上传文件模式等策略）
├── util          --  工具类模块
└── vo            --  vo模块
```

## 项目特点

-   前台参考“Hexo”的“Butterfly”设计，美观简洁，响应式体验好。
-   后台参考“element-admin”设计，侧边栏，历史标签，面包屑自动生成。
-   采用Markdown编辑器，写法简单。
-   评论支持表情输入回复等，样式参考Valine。
-   添加音乐播放器，支持在线搜索歌曲。
-   前后端分离部署，适应当前潮流。
-   接入第三方登录，减少注册成本。
-   支持发布说说，随时分享趣事。
-   留言采用弹幕墙，更加炫酷。
-   支持代码高亮和复制，图片预览，深色模式等功能，提升用户体验。
-   搜索文章支持高亮分词，响应速度快。
-   新增文章目录、推荐文章等功能，优化用户体验。
-   新增在线聊天室，支持撤回、语音输入、统计未读数量等功能。
-   新增aop注解实现日志管理。
-   支持动态权限修改，采用RBAC模型，前端菜单和后台权限实时更新。
-   后台管理支持修改背景图片，博客配置等信息，操作简单，支持上传相册。
-   代码支持多种搜索模式（Elasticsearch或MYSQL），支持多种上传模式（OSS或本地），可支持配置。
-   代码遵循阿里巴巴开发规范，利于开发者学习。

## 技术介绍

**前端：** vue + vuex + vue-router + axios + vuetify + element + echarts

**后端：** SpringBoot + nginx + docker + SpringSecurity + Swagger2 + MyBatisPlus + Mysql + Redis + elasticsearch + RabbitMQ + MaxWell + Websocket

**其他：** 接入QQ，微博第三方登录，接入腾讯云人机验证、websocket

## 运行环境

**服务器：** 腾讯云2核4G CentOS7.6

**CDN：** 阿里云全站加速

**对象存储：** 阿里云OSS

这套搭配响应速度非常快，可以做到响应100ms以下。

**最低配置：** 1核2G服务器（关闭ElasticSearch）

## 开发环境

[无标题](https://www.notion.so/2d972a165a3d4bb78d9dc8a27cfd0e13)

[无标题](https://www.notion.so/17f5c727d2804eaaa6fb3e67c3c1251e)

## 项目截图

![https://static.talkxj.com/articles/1616231666692.png](https://static.talkxj.com/articles/1616231666692.png)

QQ截图20210320171133.png

![https://static.talkxj.com/articles/1616255938601.jpg](https://static.talkxj.com/articles/1616255938601.jpg)

QQ截图20210320235519 1.jpg

![https://static.talkxj.com/articles/1616231705373.png](https://static.talkxj.com/articles/1616231705373.png)

QQ截图20210320171338.png

![https://static.talkxj.com/articles/1616231714148.png](https://static.talkxj.com/articles/1616231714148.png)

QQ截图20210320171401.png

## 快速开始

### 项目环境安装

详见文章[Docker安装运行环境](https://www.talkxj.com/articles/2)

### 项目配置

详见文章[项目配置教程](https://www.talkxj.com/articles/3)

### Docker部署项目

详见文章[项目部署教程](https://www.talkxj.com/articles/13)

## 注意事项

-   项目拉下来运行后，可到后台管理页面网站配置处修改博客相关信息.
-   邮箱配置，第三方授权配置需要自己申请。
-   ElasticSearch需要自己先创建索引，项目运行环境教程中有介绍。

## 项目总结

博客作为新手入门项目是十分不错的，项目所用的技术栈覆盖的也比较广，适合初学者学习。主要难点在于权限管理、第三方登录、websocket这块。做的不好的地方请大家见谅，有问题的或者有好的建议可以私聊联系我。

## 交流群

![https://static.talkxj.com/articles/1594437310326.png](https://static.talkxj.com/articles/1594437310326.png)

博客技术交流群聊二维码.png