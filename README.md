# AndroidRapidDevelopLibrarys
# Android快速开发资源库

本工程是在开发过程中抽取封装出的与业务逻辑无关的基础资源工具类，可直接引用。
目的：减少低层次代码书写量，真正实现业务逻辑和与业务逻辑无关代码的分离。

## android-ui-library

Android UI 资源库

代码位置：[android-ui-library](https://github.com/bailiangjin/AndroidRapidDevelopLibrarys/tree/master/android-ui-library)


封装了与业务无关的BaseActivity、BaseFragment、RecyclerView 的Adapter

带下拉刷新和上拉加载更多的ListFragment，想实现一个列表？带下拉刷新？带上拉加载更多？
嗯 你连界面都不用写了直接继承PullToRefreshAndLoadMoreFragment，实现方法protected abstract T getListRvAdapter();
返回你的Adapter就行了 其他逻辑都已经帮你封装了，下拉刷新、加载更多只需要在暴露给你的相应回调中进行你数据的更改即可



## android-utils-library

Android 工具类 资源库

代码位置：[android-ui-library](http://code.ds.bailiangjin.com.cn/gitlab/im-android/AndroidRapidDevelopLibrarys/tree/master/android-utils-library)


具体包括：常用工具类app、device、file、html、ui 几种类别的工具类

具体请参考工程代码[工具类代码目录](http://code.ds.bailaingjin.com.cn/gitlab/im-android/AndroidRapidDevelopLibrarys/tree/master/android-utils-library/src/main/java/com/bailiangjin/utilslibrary/utils)
等







这两个项目均用 git submodule的方式引用了AndroidRapidDevelopLibrarys 工程

后续会添加具体的使用说明，敬请期待。

