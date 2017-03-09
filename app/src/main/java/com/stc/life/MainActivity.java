package com.stc.life;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LifeContract.View{

    private LifeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    private void init(boolean [][] arr){
        new LifePresenter(this,arr);
    }

    @Override
    public void updateMap(boolean[][] updatedMap) {

    }

    @Override
    public void setPresenter(LifeContract.Presenter presenter) {
        this.presenter=presenter;
    }
}
