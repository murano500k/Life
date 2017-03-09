package com.stc.life;

/**
 * Created by artem on 3/9/17.
 */

public interface LifeContract  {
    interface View {
        void updateMap(boolean [][] updatedMap);

        void setPresenter(LifeContract.Presenter presenter);
    }
    interface Presenter {
        void iterate();
    }

}
