# MyRxLifeCycle
trello/RxLifecycle 的一个轻量级子集，仅仅支持绑定生命周期这一个用法。

导入：
compile 'com.qiu.rxlifecycle:rxlifecycle:0.0.3'

用法：
Observable.interval (2, TimeUnit.SECONDS)
.compose (ThirdActivity.this.<Long>bindUntilEvent (ActivityLifeCycleEvent.DESTROY))
.subscribe (...);
