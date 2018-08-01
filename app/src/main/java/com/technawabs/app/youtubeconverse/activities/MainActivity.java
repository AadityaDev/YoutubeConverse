package com.technawabs.app.youtubeconverse.activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.technawabs.app.youtubeconverse.R;
import com.technawabs.app.youtubeconverse.base.BaseAppCompat;
import com.technawabs.app.youtubeconverse.fragments.ChatFragment;
import com.technawabs.app.youtubeconverse.fragments.HomeFragment;
import com.technawabs.app.youtubeconverse.fragments.TextChatFragment;
import com.technawabs.app.youtubeconverse.fragments.VideoFragment;

public class MainActivity extends BaseAppCompat implements HomeFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener,
        VideoFragment.OnFragmentInteractionListener,
        TextChatFragment.OnFragmentInteractionListener{

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openFragment();
    }

    private void openFragment(){
        fragment=new HomeFragment();
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame,fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
