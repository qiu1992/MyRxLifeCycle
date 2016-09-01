package qiu.test0901.com;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

import cycle.ActivityLifeCycleEvent;
import cycle.FragmentLifeCycleEvent;
import cycle.MyRxFragment;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016-9-1.
 */
public class TestFragment extends MyRxFragment
{
    private final String TAG = "qiuqiu";
    private CompositeSubscription subscription;
    private Subscription sub;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        subscription = new CompositeSubscription ();
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View layout = inflater.inflate (R.layout.activity_second,container,false);
        layout.findViewById (R.id.start_btn).setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
                test ();
            }
        });
        return layout;
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
                .compose (TestFragment.this.<Long>bindUntilEvent (FragmentLifeCycleEvent.DETACH))
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
}
