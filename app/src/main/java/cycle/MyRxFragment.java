package cycle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Administrator on 2016-9-1.
 */
public class MyRxFragment extends Fragment
{
    private final BehaviorSubject<FragmentLifeCycleEvent> lifecycleSubject = BehaviorSubject.create();

    public <T> Observable.Transformer<T, T> bindUntilEvent(@NonNull final FragmentLifeCycleEvent event)
    {
        return new Observable.Transformer<T, T> ()
        {
            @Override
            public Observable<T> call (Observable<T> sourceObservable)
            {
                Observable<FragmentLifeCycleEvent> compareLifeCycleObservable =
                        lifecycleSubject.takeFirst (new Func1<FragmentLifeCycleEvent, Boolean> ()
                        {
                            @Override
                            public Boolean call (FragmentLifeCycleEvent fragmentLifeCycleEvent)
                            {
                                return fragmentLifeCycleEvent.equals (event);
                            }
                        });
                return sourceObservable.takeUntil (compareLifeCycleObservable);
            }
        };
    }

    @Override
    public void onAttach (Context context)
    {
        lifecycleSubject.onNext (FragmentLifeCycleEvent.ATTACH);
        super.onAttach (context);
    }

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState)
    {
        lifecycleSubject.onNext (FragmentLifeCycleEvent.CREATE);
        super.onCreate (savedInstanceState);
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState)
    {
        lifecycleSubject.onNext (FragmentLifeCycleEvent.CREATE_VIEW);
        super.onViewCreated (view, savedInstanceState);
    }

    @Override
    public void onStart ()
    {
        lifecycleSubject.onNext (FragmentLifeCycleEvent.START);
        super.onStart ();
    }

    @Override
    public void onResume ()
    {
        lifecycleSubject.onNext (FragmentLifeCycleEvent.RESUME);
        super.onResume ();
    }

    @Override
    public void onPause ()
    {
        lifecycleSubject.onNext (FragmentLifeCycleEvent.PAUSE);
        super.onPause ();
    }

    @Override
    public void onStop ()
    {
        lifecycleSubject.onNext (FragmentLifeCycleEvent.STOP);
        super.onStop ();
    }

    @Override
    public void onDestroyView ()
    {
        lifecycleSubject.onNext (FragmentLifeCycleEvent.DESTROY_VIEW);
        super.onDestroyView ();
    }

    @Override
    public void onDestroy ()
    {
        lifecycleSubject.onNext (FragmentLifeCycleEvent.DESTROY);
        super.onDestroy ();
    }

    @Override
    public void onDetach ()
    {
        lifecycleSubject.onNext (FragmentLifeCycleEvent.DETACH);
        super.onDetach ();
    }
}
