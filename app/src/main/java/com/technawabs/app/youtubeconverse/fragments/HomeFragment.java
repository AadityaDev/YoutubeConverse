package com.technawabs.app.youtubeconverse.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technawabs.app.youtubeconverse.R;
import com.technawabs.app.youtubeconverse.base.BaseFragment;
import com.technawabs.app.youtubeconverse.models.User;
import com.technawabs.app.youtubeconverse.uicomponents.PagerTabWidget;

import java.util.List;

public class HomeFragment extends BaseFragment {

    private User user;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private OnFragmentInteractionListener mListener;
    private PagerTabWidget tabWidget;
    private ViewPager viewPager;
    private HomePagerAdapter homePagerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView= inflater.inflate(R.layout.fragment_home, container, false);
        initTab(itemView);
        return itemView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void initTab(@NonNull View itemView) {
        tabWidget = (PagerTabWidget) itemView.findViewById(R.id.home_tabWidget);
        tabWidget.setDividerInvisible();

        tabWidget.addTab(LayoutInflater.from(getContext()).inflate(R.layout.bottom_bar_videos, null));
        tabWidget.addTab(LayoutInflater.from(getContext()).inflate(R.layout.bottom_bar_job, null));

        viewPager = (ViewPager) itemView.findViewById(R.id.main_viewPager);
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(homePagerAdapter);

        tabWidget.setmViewPager(viewPager);
        tabWidget.setmOnTabSelectedListener(new PagerTabWidget.OnTabSelectedListener() {
            @Override
            public void onSelected(List<View> tabViews, int position) {
//                Toast.makeText(getContext(), "tab" + (position + 1) + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class HomePagerAdapter extends FragmentPagerAdapter {

        private final int TABS_COUNT=2;
        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    return VideoFragment.newInstance();
                case 1:
                    return ChatFragment.newInstance();
                default:
                    return VideoFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return TABS_COUNT;
        }
    }

    public void openFragment(@NonNull Fragment fragment){
        fragmentManager=getChildFragmentManager();
        fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
