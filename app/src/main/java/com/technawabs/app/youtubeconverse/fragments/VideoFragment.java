package com.technawabs.app.youtubeconverse.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.technawabs.app.youtubeconverse.constants.Config;
import com.technawabs.app.youtubeconverse.uicomponents.adapter.VideoAdapter;
import com.technawabs.app.youtubeconverse.R;
import com.technawabs.app.youtubeconverse.base.BaseFragment;
import com.technawabs.app.youtubeconverse.models.Video;
import com.technawabs.app.youtubeconverse.models.User;
import com.technawabs.app.youtubeconverse.preferences.DBHelper;
import com.technawabs.app.youtubeconverse.uicomponents.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {

    private FragmentActivity myContext;
    private YouTubePlayer YPlayer;
    private static final String YoutubeDeveloperKey = "AIzaSyCvvy9Mf0-5tsWpzsckst7KXGGO8JxBOSg";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private FirebaseAnalytics mFirebaseAnalytics;
    private User user;
    private OnFragmentInteractionListener mListener;
    private DBHelper dbHelper;
    private VideoAdapter videoAdapter;
    private List<Video> videos;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public VideoFragment() {
        // Required empty public constructor
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_video, container, false);
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(Config.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(false);
                    YPlayer.loadVideo( Config.YOUTUBE_VIDEO_CODE);
                    YPlayer.play();

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Config.YOUTUBE_VIDEO_CODE);
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "video");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });
        return view;
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

}
