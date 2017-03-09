package com.stc.life;

import android.view.View;

/**
 * Created by artem on 3/9/17.
 */

public class LifePresenter implements LifeContract.Presenter {
    Map map;
    LifeContract.View v;

    public LifePresenter(LifeContract.View view,boolean[] [] array) {
        map=new Map(array);
        v=view;
        v.setPresenter(this);
    }


    @Override
    public void iterate() {

    }
}
