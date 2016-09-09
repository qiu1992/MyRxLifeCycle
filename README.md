# MyRxLifeCycle
trello/RxLifecycle 的一个轻量级子集，仅仅支持绑定生命周期这一个用法，适用Activity以及Fragment。

导入：
compile 'com.qiu.rxlifecycle:rxlifecycle:0.0.3'

用法：
Observable.interval (2, TimeUnit.SECONDS)
.compose (ThirdActivity.this.<Long>bindUntilEvent (ActivityLifeCycleEvent.DESTROY))
.subscribe (...);

特别注意：bindUntilEvent请放在离subscribe最近的地方。也就是说，compose bindUntilEvent之后，必须是subscribe。
