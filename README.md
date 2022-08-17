# 前言
blog项目是基于Springboot + Vue 开发的前后端分离博客。
# 项目文档

- **在线接口文档地址：**[https://www.apifox.cn/apidoc/shared-278ea781-8b98-46bb-b3d3-dd19522464c9](https://www.apifox.cn/apidoc/shared-278ea781-8b98-46bb-b3d3-dd19522464c9)
- **项目文档地址：**[https://www.yuque.com/orpioi/blog](https://www.yuque.com/orpioi/blog)
# 项目介绍
## 项目演示
### 前端演示：
地址：[http://blog.erotsx.me/#/](http://blog.erotsx.me/#/)
![](https://s3.bmp.ovh/imgs/2022/08/17/0749e2a97eb92060.png#crop=0&crop=0&crop=1&crop=1&id=KW20S&originHeight=933&originWidth=1919&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)
![](https://s3.bmp.ovh/imgs/2022/08/17/59a600894d0804fb.png#crop=0&crop=0&crop=1&crop=1&id=dLava&originHeight=940&originWidth=1919&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)
### 后端演示：
地址：
![](https://s3.bmp.ovh/imgs/2022/08/17/40e1dc4764ae4cf1.png#crop=0&crop=0&crop=1&crop=1&id=Kqyok&originHeight=838&originWidth=1919&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)
![](https://s3.bmp.ovh/imgs/2022/08/17/335a815153f8ad9c.png#crop=0&crop=0&crop=1&crop=1&id=lhyNh&originHeight=761&originWidth=1919&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)

## 项目特点

- 支持动态权限修改，采用RBAC模型，后台权限实时更新。
- 使用线程池，提高响应速度。
- 使用aop注解实现日志管理。
- 使用MongoDB存储评论，读写效率高
## 目录结构
### 后端目录结构
```
blog
├── blog-common            -- 工具类及通用代码
├── blog-security          -- SpringSecurity封装公用模块
└── blog-api               -- 前台接口及后台博客管理系统接口
```
### 前端目录结构
```
├── build                   -- 构建相关
├── mock                    -- 项目mock 模拟数据
├── plop-templates          -- 基本模板
├── public                  -- 静态资源
│   │── favicon.ico         -- favicon图标
│   └── index.html          -- html模板
├── src                     -- 源代码
│   ├── api                 -- 所有请求
│   ├── assets              -- 主题 字体等静态资源
│   ├── components          -- 全局公用组件
│   ├── directive           -- 全局指令
│   ├── filters             -- 全局 filter
│   ├── icons               -- 项目所有 svg icons
│   ├── lang                -- 国际化 language
│   ├── layout              -- 全局 layout
│   ├── router              -- 路由
│   ├── store               -- 全局 store管理
│   ├── styles              -- 全局样式
│   ├── utils               -- 全局公用方法
│   ├── vendor              -- 公用vendor
│   ├── views               -- views 所有页面
│   ├── App.vue             -- 入口页面
│   ├── main.js             -- 入口文件 加载组件 初始化等
│   └── permission.js       -- 权限管理
├── tests                   -- 测试
├── .env.xxx                -- 环境变量配置
├── .eslintrc.js            -- eslint 配置项
├── .babelrc                -- babel-loader 配置
├── .travis.yml             -- 自动化CI配置
├── vue.config.js           -- vue-cli 配置
├── postcss.config.js       -- postcss 配置
└── package.json            -- package.json
```
## 技术选型
### 后端技术
| 技术 | 说明 | 官网 |
| --- | --- | --- |
| SpringBoot | 容器+MVC框架 | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| SpringSecurity | 认证和授权框架 | [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security) |
| Elasticsearch | 搜索引擎 | [https://github.com/elastic/elasticsearch](https://github.com/elastic/elasticsearch) |
| Redis | 分布式缓存 | [https://redis.io/](https://redis.io/) |
| MongoDB | NoSql数据库 | [https://www.mongodb.com](https://www.mongodb.com) |
| JWT | JWT登录支持 | [https://github.com/jwtk/jjwt](https://github.com/jwtk/jjwt) |
| Lombok | 简化对象封装工具 | [https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok) |
| Hutool | Java工具类库 | [https://github.com/looly/hutool](https://github.com/looly/hutool) |
| MyBatis-Plus | Mybatis增强工具 | [https://baomidou.com/](https://baomidou.com/) |

### 前端技术
| 技术 | 说明 | 官网 |
| --- | --- | --- |
| Vue | 前端框架 | [https://vuejs.org/](https://vuejs.org/) |
| Vue-router | 路由框架 | [https://router.vuejs.org/](https://router.vuejs.org/) |
| Vuex | 全局状态管理框架 | [https://vuex.vuejs.org/](https://vuex.vuejs.org/) |
| Element | 前端UI框架 | [https://element.eleme.io](https://element.eleme.io) |
| Axios | 前端HTTP框架 | [https://github.com/axios/axios](https://github.com/axios/axios) |
| vuetify | 前端UI框架 | [https://vuetifyjs.com/zh-Hans/](https://vuetifyjs.com/zh-Hans/) |

# 开发环境
## 开发工具
| 工具 | 说明 | 官网 |
| --- | --- | --- |
| IDEA | 开发IDE | [https://www.jetbrains.com/idea/download](https://www.jetbrains.com/idea/download) |
| X-shell | Linux远程连接工具 | [http://www.netsarang.com/download/software.html](http://www.netsarang.com/download/software.html) |
| ProcessOn | 流程图绘制工具 | [https://www.processon.com/](https://www.processon.com/) |
| Apifox | API接口调试工具 | [https://www.apifox.cn/](https://www.apifox.cn/) |
| DataGrip | 数据库连接工具 | [https://www.jetbrains.com/datagrip/](https://www.jetbrains.com/datagrip/) |
| Another Redis Desktop Manager | 前端UI框架 | [https://github.com/qishibo/AnotherRedisDesktopManager](https://github.com/qishibo/AnotherRedisDesktopManager) |

## 开发环境
| 工具 | 版本号 | 官网 |
| --- | --- | --- |
| JDK | 1.8 | [https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) |
| Mysql | 5.7 | [https://www.mysql.com/](https://www.mysql.com/) |
| MongoDB | 6.0.0 | [https://www.mongodb.com/try/download/community](https://www.mongodb.com/try/download/community) |
| Redis | 5.0.14 | [https://redis.io/download](https://redis.io/download) |
| Elasticsearch | 7.17.3 | [https://www.elastic.co/downloads/elasticsearch](https://www.elastic.co/downloads/elasticsearch) |

