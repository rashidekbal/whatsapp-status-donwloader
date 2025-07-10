package com.rtech.statusdownloaderwa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rtech.statusdownloaderwa.AppRoot.RootApp;
import com.rtech.statusdownloaderwa.activity.OpenMedia;
import com.rtech.statusdownloaderwa.R;
import com.rtech.statusdownloaderwa.interfaces.MediaSaveInterface;
import com.rtech.statusdownloaderwa.models.MediaModel;
import com.rtech.statusdownloaderwa.utils.MediaSaver;

import java.io.File;
import java.util.ArrayList;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.viewHolder> {
    Context context;
    ArrayList<MediaModel> list;

    public MediaAdapter(Context context, ArrayList<MediaModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.media_card,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(list.get(position).isVideo){
            holder.PlayBtn.setVisibility(View.VISIBLE);

        }else {
            holder.PlayBtn.setVisibility(View.GONE);

        }
        if(list.get(position).type=="file"){
            File file=new File(list.get(position).path);
            Glide.with(context).load(file).placeholder(R.drawable.loading).into(holder.imageView);
            holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.downloadBtn.setEnabled(false);
                    MediaSaver.saveFromFile(RootApp.getContextGlobal(),list.get(position).path, new MediaSaveInterface() {
                        @Override
                        public void onSave() {


                            Toast.makeText(context, "Status saved successfully", Toast.LENGTH_SHORT).show();
                            holder.downloadBtn.setEnabled(true);

                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(context, "Error saving Status", Toast.LENGTH_SHORT).show();
                            holder.downloadBtn.setEnabled(true);

                        }
                    });
                }
            });
        }
        else{
            Uri uri=Uri.parse(list.get(position).path);
            Glide.with(context).load(uri).placeholder(R.drawable.loading).into(holder.imageView);
            holder.downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.downloadBtn.setEnabled(false);
                    MediaSaver.saveFromUri(RootApp.getContextGlobal(),list.get(position).path, new MediaSaveInterface() {
                        @Override
                        public void onSave() {

                            Toast.makeText(context, "Status saved successfully", Toast.LENGTH_SHORT).show();
                            holder.downloadBtn.setEnabled(true);

                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(context, "Error saving Status", Toast.LENGTH_SHORT).show();
                            holder.downloadBtn.setEnabled(true);

                        }
                    });
                }
            });

        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage=new Intent(context, OpenMedia.class);
                nextPage.putExtra("path",list.get(position).path);
                nextPage.putExtra("isVideo",list.get(position).isVideo);
                nextPage.putExtra("type",list.get(position).type);
                context.startActivity(nextPage);
            }
        });
        holder.PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage=new Intent(context, OpenMedia.class);
                nextPage.putExtra("path",list.get(position).path);
                nextPage.putExtra("isVideo",list.get(position).isVideo);
                nextPage.putExtra("type",list.get(position).type);
                context.startActivity(nextPage);
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,PlayBtn;
        LinearLayout downloadBtn;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageView);
            downloadBtn=itemView.findViewById(R.id.downloadBtn);
            PlayBtn=itemView.findViewById(R.id.playBtn);
        }
    }
}
