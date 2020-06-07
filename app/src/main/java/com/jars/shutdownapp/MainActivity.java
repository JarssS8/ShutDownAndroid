package com.jars.shutdownapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Action;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btReboot)
    ImageView btReboot;
    @BindView(R.id.btShutdown)
    ImageView btShutdown;
    @BindView(R.id.btHibernate)
    ImageView btHibernate;
    @BindView(R.id.btLock)
    ImageView btLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btLock.setOnClickListener(this);
        btHibernate.setOnClickListener(this);
        btShutdown.setOnClickListener(this);
        btReboot.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        Action action = new Action();
        switch (v.getId()){
            case R.id.btLock:
                action.setType(Action.ActionType.LOCK);
                break;
            case R.id.btHibernate:
                action.setType(Action.ActionType.HIBERNATE);
                break;
            case R.id.btReboot:
                action.setType(Action.ActionType.REBOOT);
                break;
            case R.id.btShutdown:
                action.setType(Action.ActionType.SHUT_DOWN);
                break;
        }
        action.setTimeInSeconcds(30);
        ConnectionThread thread = new ConnectionThread(action, getApplicationContext());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
