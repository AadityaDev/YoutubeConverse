package com.technawabs.app.youtubeconverse.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.technawabs.app.youtubeconverse.uicomponents.adapter.VideoAdapter;
import com.technawabs.app.youtubeconverse.R;
import com.technawabs.app.youtubeconverse.base.BaseFragment;
import com.technawabs.app.youtubeconverse.models.Video;
import com.technawabs.app.youtubeconverse.models.User;
import com.technawabs.app.youtubeconverse.preferences.DBHelper;
import com.technawabs.app.youtubeconverse.uicomponents.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;
    private User user;
    private DBHelper dbHelper;
    private VideoAdapter videoAdapter;
    private List<Video> videos;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EditText searchMedicine;

    public ChatFragment() {
        // Required empty public constructor
    }

   public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        dbHelper=new DBHelper(getContext());
        videos =new ArrayList<>();
        searchMedicine=(EditText)view.findViewById(R.id.search_box);
        recyclerView=(RecyclerView)view.findViewById(R.id.medicines);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        videos =new ArrayList<>();
        videoAdapter =new VideoAdapter(getContext(), videos);
        getMedicines();
        recyclerView.setAdapter(videoAdapter);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });
        addTextListener();
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

    private void getMedicines(){
        videos.addAll(dbHelper.getAllMedicine());
        videoAdapter.notifyDataSetChanged();
    }

    public void addTextListener() {

        searchMedicine.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();
                final List<Video> filteredList = new ArrayList<>();
                for (int i = 0; i < videos.size(); i++) {
                    final String text = videos.get(i).getName().toLowerCase();
                    if (text.contains(query)) {
                        filteredList.add(videos.get(i));
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                if (filteredList.size() > 0) {
                    videoAdapter = new VideoAdapter(getContext(), filteredList);
                    recyclerView.setAdapter(videoAdapter);
                    videoAdapter.notifyDataSetChanged();  // data set changed
                }
                searchMedicine.setImeActionLabel(query, KeyEvent.KEYCODE_ENTER);
                if (videos.size() <= 0) {
                    videos = new ArrayList<>();
                    videoAdapter = new VideoAdapter(getContext(), videos);
                    getMedicines();
                    recyclerView.setAdapter(videoAdapter);
                    videoAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
