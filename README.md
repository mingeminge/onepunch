# major-onepunch
# 一拳超人主题后台管理系统

#### 个人兴趣爱好，喜欢一拳超人，做了个一拳超人主题的后台系统

[在线体验地址](http://mingege.top)
           体验账号：
                   
账号  |  密码   |  权限
----| -----|  -----
mingege | 123456 | 所有功能         

# 项目介绍：
现在都在走前后端分离开发，作为java小白的我也去学了相对其他两大前端框架相对入手比较简单的vue，虽然开始遇到过很多问题，但是用着确实舒服！前前后后花了两个多月的时间，都是下班回到家搞搞，做了个属于自己兴趣爱好的东西，灵感来源兵长砍🐒（好像关系不大）😁；

## 主要用到的技术：
### 后端：
* [springboot 2.1.4](https://spring.io/projects/spring-boot/)
* [springsecurity](https://spring.io/projects/spring-security)
* [redis](https://redis.io)
* [mybatis](https://blog.mybatis.org/)
* [fastjson](https://www.w3cschool.cn/fastjson/)
* [pagehelper](https://pagehelper.github.io/)

### 前端：
* [vue 2.5.2](https://cn.vuejs.org/)
* [ui框架element-ui](https://element.eleme.cn/#/zh-CN)
* [axios](http://www.axios-js.com)
* [vuex](https://vuex.vuejs.org/)
* [首页图表echarts](https://echarts.baidu.com/)
* [登录页面动画vue-particles](https://www.jianshu.com/p/53199b842d25)
* [路由vue-router](https://router.vuejs.org/)
* [前端模版 vue-element-admin](https://panjiachen.github.io/vue-element-admin-site/zh/)

## 主要功能：
* 1、登录：登录之后将通过京东万象买的接口查询当前登录用户的ip信息，并生成token存储到redis，最后通过json返回给前端，每次登录成功都会将路由初始化时随机加载的动画全屏播放，播放完成之后跳转到系统首页，系不系很炫酷😁！
* 2、首页：主要展示当前登录用户的信息、登录信息及系统访问统计等；
* 3、个人中心：可以修改个人信息（身份信息、头像、密码等）😁；
* 4、系统管理：后台权限使用的是springsecurity进行权限控制，包括用户管理、角色管理、权限管理，前端权限控制是通过vue的自定义指令，后台返回用户的权限及角色信息后，将信息存储到vuex里，按钮的权限控制通过自定制指令加vuex里的权限去控制；
* 5、头像管理：用户可进行上传头像、查看头像、修改头像分类及删除头像等
* 6、在线用户：查看当前系统的在线用户，是通过查询redis中存储token，管理员权限可强制下线其他用户;
* 7、系统日志：用户进行操作之后，系统会将用户的操作信息进行入库，可进行查询及删除操作；
## 兼容性：
    pc端建议使用谷歌浏览器访问

## 运行条件：
### 前端需安装node及npm
#### install dependencies
    npm install
#### serve with hot reload at localhost:8080
    npm run dev
#### build for production with minification
    npm run build
#### build for production and view the bundle analyzer report
    npm run build --report
### 后端需要jdk1.8 maven、mysql及redis
    将sql文件跑到本地数据库中，即可运行
    京东的ip地址接口需要的可以找我免费使用；
## 作者：
    Mingege,96年的小码农
## 联系方式：
    qq：278406977

## 鸣谢：
[该项目参考了vue-element-admin](https://panjiachen.github.io/vue-element-admin-site/zh/)

### 赞助下秃头青年去植个发吧
<p align="center">
<img src="http://47.105.230.85:9090/wxfkm/mgg.png" alt="Sample"  width="250">	
</p>
