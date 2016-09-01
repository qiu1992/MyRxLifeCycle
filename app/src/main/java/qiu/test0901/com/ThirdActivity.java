package qiu.test0901.com;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import cycle.ActivityLifeCycleEvent;
import cycle.MyRxAppCompatActivity;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class ThirdActivity extends MyRxAppCompatActivity
{
    private final String TAG = "qiuqiu";
    private CompositeSubscription subscription;
    private Subscription sub;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_third);

        subscription = new CompositeSubscription ();
        findViewById (R.id.third_btn).setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
//                test ();
            }
        });
    }

    private void test ()
    {
        sub = Observable.interval (2, TimeUnit.SECONDS)
                .doOnUnsubscribe (new Action0 ()
                {
                    @Override
                    public void call ()
                    {
                        Log.d (TAG, "call: doOnUnsubscribe");
                    }
                })
                .doOnCompleted (new Action0 ()
                {
                    @Override
                    public void call ()
                    {
                        Log.d (TAG, "call: doOnCompleted");
                    }
                })
                .doOnError (new Action1<Throwable> ()
                {
                    @Override
                    public void call (Throwable throwable)
                    {
                        Log.d (TAG, "call: doOnError");
                    }
                })
                .doOnNext (new Action1<Long> ()
                {
                    @Override
                    public void call (Long aLong)
                    {
                        Log.d (TAG, "call: doOnNext");
                    }
                })
                .compose (ThirdActivity.this.<Long>bindUntilEvent (ActivityLifeCycleEvent.DESTROY))
                .subscribe (new Subscriber<Long> ()
                {
                    @Override
                    public void onCompleted ()
                    {
                        Log.d (TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError (Throwable e)
                    {
                        Log.d (TAG, "onError: ");
                    }

                    @Override
                    public void onNext (Long aLong)
                    {
                        Log.d (TAG, "onNext: " + aLong);
                    }
                });
        subscription.add (sub);
    }

    @Override
    protected void onDestroy ()
    {
        System.out.println ();
        super.onDestroy ();
        System.out.println ();
    }
}
