package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Circle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitpolarity.spotifytestapp.Adapters.CircleTabAdapter.TopLevelTabAdapter;
import com.bitpolarity.spotifytestapp.databinding.FragmentTabManagerBinding;


public class TabManagerFragment extends Fragment {

    ViewPager2 viewPager;
    FragmentTabManagerBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentTabManagerBinding.inflate(inflater, container, false);

        viewPager = binding.viewpager;

        viewPager.setSaveEnabled(false);
        viewPager.setOffscreenPageLimit(1);

        final FragmentManager fm = getChildFragmentManager();
        final TopLevelTabAdapter adapter = new TopLevelTabAdapter(fm, getLifecycle());

        //tabLayout.addTab(tabLayout.newTab().setText("Stories ⚡"));

        viewPager.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager.setCurrentItem(1,true);
        viewPager.setPageTransformer(new DepthPageTransformer());


        }

    @Override
    public void onResume() {
        super.onResume();

    }


}

class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0f);

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0f);
        }
    }
}


 class DepthPageTransformer implements ViewPager2.PageTransformer {

     private static final float MIN_SCALE = 1f;


         public void transformPage (View view,float position){
             int pageWidth = view.getWidth();

             if (position < -1) { // [-Infinity,-1)
                 // This page is way off-screen to the left.
                 view.setAlpha(0f);

             } else if (position <= 0) { // [-1,0]

                 // Fade the page out.
                 view.setAlpha(1 - position);
                 // Counteract the default slide transition
                 view.setTranslationY(-position);
                 view.setTranslationX(position);
                 // Scale the page down (between MIN_SCALE and 1)
                 float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                 view.setScaleX(scaleFactor-position);
                 view.setScaleY(scaleFactor-position);


               //  view.setScaleX(scaleFactor-position);
              //   view.setScaleY(scaleFactor-position);

             } else if (position <= 1) { // (0,1]
                 // Use the default slide transition when moving to the left page
                 view.setAlpha(1f);
                 view.setTranslationX(0f);
                 view.setScaleX(1f);
                 view.setScaleY(1f);



             } else { // (1,+Infinity]
                 // This page is way off-screen to the right.
                 view.setAlpha(0f);
             }
         }

     }









//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition(),true);
//
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//
//        });
//
//        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                tabLayout.selectTab(tabLayout.getTabAt(position));
//            }
//        });
