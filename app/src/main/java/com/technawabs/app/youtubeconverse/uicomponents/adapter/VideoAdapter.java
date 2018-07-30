package com.technawabs.app.youtubeconverse.uicomponents.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technawabs.app.youtubeconverse.R;
import com.technawabs.app.youtubeconverse.models.Video;
import com.technawabs.app.youtubeconverse.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MedicineViewHelper>{

    private List<Video> videos;
    private Context context;

    public VideoAdapter(@NonNull Context context, @NonNull List<Video> videos){
        this.context=context;
        this.videos = videos;
    }

    @Override
    public MedicineViewHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.video_card,parent,false);
        return new MedicineViewHelper(view);
    }

    @Override
    public void onBindViewHolder(MedicineViewHelper holder, int position) {
        final Video video = videos.get(position);
        if(video !=null){
            if(StringUtils.isNotEmptyOrNull(video.getName())){
                holder.name.setText(video.getName());
            }
            if(StringUtils.isNotEmptyOrNull(video.getPriority())){
                holder.priority.setText(video.getPriority());
            }
            holder.description.setText(video.getDosage()+" in a day");
        }
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public long getItemId(int position) {
        return videos.get(position).getId();
    }

    public Video getMovie(int position){
        return videos.get(position);
    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class MedicineViewHelper extends RecyclerView.ViewHolder{

        private ImageView background;
        private TextView name;
        private TextView priority;
        private TextView description;

        public MedicineViewHelper(@NonNull View item){
            super(item);
            name=(TextView)item.findViewById(R.id.medicine_name);
            priority=(TextView)item.findViewById(R.id.medicine_priority);
            description =(TextView)item.findViewById(R.id.medicine_dosage);
        }

    }

}
