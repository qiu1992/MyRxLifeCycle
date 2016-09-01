package cycle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2016-9-1.
 */
public class MyRxAppCompatActivity extends AppCompatActivity
{
    private final BehaviorSubject<ActivityLifeCycleEvent> lifecycleSubject = BehaviorSubject.create();

    public <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull final ActivityLifeCycleEvent event)
    {
        return new Observable.Transformer<T, T> ()
        {
            @Override
            public Observable<T> call (Observable<T> sourceObservable)
            {
                Observable<ActivityLifeCycleEvent> compareLifeCycleObservable =
                        lifecycleSubject.takeFirst (new Func1<ActivityLifeCycleEvent, Boolean> ()
                {
                    @Override
                    public Boolean call (ActivityLifeCycleEvent activityLifeCycleEvent)
                    {
                        return activityLifeCycleEvent.equals (event);
                    }
                });
                return sourceObservable.takeUntil (compareLifeCycleObservable);
            }
        };
    }

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState)
    {
        lifecycleSubject.onNext (ActivityLifeCycleEvent.CREATE);
        super.onCreate (savedInstanceState);
    }

    @Override
    protected void onStart ()
    {
        lifecycleSubject.onNext (ActivityLifeCycleEvent.START);
        super.onStart ();
    }

    @Override
    protected void onResume ()
    {
        lifecycleSubject.onNext (ActivityLifeCycleEvent.RESUME);
        super.onResume ();
    }

    @Override
    protected void onPause ()
    {
        lifecycleSubject.onNext (ActivityLifeCycleEvent.PAUSE);
        super.onPause ();
    }

    @Override
    protected void onStop ()
    {
        lifecycleSubject.onNext (ActivityLifeCycleEvent.STOP);
        super.onStop ();
    }

    @Override
    protected void onDestroy ()
    {
        lifecycleSubject.onNext (ActivityLifeCycleEvent.DESTROY);
        super.onDestroy ();
    }
}
