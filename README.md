# mdc-ttl-dubbo

#### 介绍
基于ttl mdc slf4j在dubbo的RPC体系中做的全链路日志跟踪

#### 软件架构
软件架构说明


#### 安装教程

1.  拉取仓库install到本地(or deploy到本地私服)
2.  引入依赖
3.  web项目将com.ruhnn.trace.web.filter.WebRequestTraceFilter设置到web.xml中
4.  将spring-trace-service.xml引入项目中
5.  如需线程池中打印出traceId则需要在Spring配置文件中用replace-method 将提交到线程池的方法替换掉

#### 使用说明

web项目引入此依赖 要做链路跟踪 需要做的几点
1.请求从web层中进入会在过滤链生成traceId 如需自定义生成traceId的规则 可以实现IDefaultRandomTrace接口

2.dubbo服务端或消费端无需要多余的配置即可将traceId传递下去

3.线程池下传递主要依靠了TransmittableThreadLocal组件,在我Spring配置文件中可以用replace-method将提交到线程池中的方法替换成
ExecutorConfigurationSupportMethodReplacer 此方法中将Runnable和Callable 进行修饰成TtlRunnable和TtlCallable
（ps 如不使用线程池可以无视）

4.@Trace注解可以将通过Spring调用的方法拦截生成traceId

#### 参与贡献

1. damon0425@outlook.com(damon)


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
