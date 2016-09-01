package qiu.test0901.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

public class MainActivity extends RxAppCompatActivity
{

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        findViewById (R.id.test_btn).setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
                startActivity (new Intent (MainActivity.this,SecondActivity.class));
            }
        });
    }
}
