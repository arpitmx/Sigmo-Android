package com.bitpolarity.spotifytestapp;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerScrollManager {


    public abstract static class FabScroll extends RecyclerView.OnScrollListener {

       public static int scrollDist1 = 0;
    boolean isVisible = false;
    final float MINIMUM = 300;


    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


        if (isVisible && scrollDist1 > MINIMUM) {
            hide();
            scrollDist1 = 0;
            isVisible = false;
        }
        else if (!isVisible && scrollDist1 < -MINIMUM) {
            show();
            scrollDist1 = 0;
            isVisible = true;
        }

        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            scrollDist1 += dy;
        }

    }

    public static void setScrollDist(){
        scrollDist1 = 0;
    }


    public abstract void show();
    public abstract void hide();
}



    public abstract static class MiniplayerScroll extends RecyclerView.OnScrollListener {

        public static int scrollDist = 0;
        boolean isVisible = false;
        final float MINIMUM_SHOW = 5500;
        final float MINIMUM_HIDE = 300;
        final long HIDING_DELAY = 3000;


        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);


            if (isVisible && scrollDist > MINIMUM_HIDE) {


                hide();
                scrollDist = 0;
                isVisible = false;





            }
            else if (!isVisible && scrollDist < -MINIMUM_SHOW) {
                        show();
                        scrollDist = 0;
                        isVisible = true;

                                new Handler().postDelayed(() -> {

                                    hide();
                                    scrollDist = 0;
                                    isVisible = false;

                                },  HIDING_DELAY);

            }

            if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
                scrollDist += dy;
            }

        }

        public static void setScrollDist(){
            scrollDist = 0;
        }


        public abstract void show();
        public abstract void hide();
    }
}
