package com.getaround.getaroundgalleryviewer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.getaround.getaroundgalleryviewer.R;
import com.getaround.getaroundgalleryviewer.models.Photo;
import com.getaround.getaroundgalleryviewer.uiutils.TextUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class PhotoAdapter  extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{


    private final Context context;
    private final ArrayList<Photo> photoList;
    private CustomOnClickListener customOnClickListener;

    public interface CustomOnClickListener{

        public void onItemClick(View view,int position);
    }

    public PhotoAdapter(Context context, ArrayList<Photo> photoList){

        this.context = context;
        this.photoList = photoList;
    }

    public void setClickListener(CustomOnClickListener customOnClickListener) {
        this.customOnClickListener = customOnClickListener;
    }

    @Override
    public int getItemCount() {

        if(photoList == null)
            return 0;
        else
            return photoList.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View photoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_card,parent,false);
        return new ViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder holder, int position) {

        Photo photo = photoList.get(position);
        String name = photo.getName();
        if(TextUtils.isEnglishWord(name)) {
            String modifiedText = TextUtils.toTitleCase(name.toUpperCase(Locale.ENGLISH));
            holder.title.setText(modifiedText);
        }else
            holder.title.setText(name);

        Picasso.with(context).load(photo.getImgUrl()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.thumbnail);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView thumbnail;

        public ViewHolder(View view) {

            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(customOnClickListener!=null){

                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            customOnClickListener.onItemClick(view, position);
                        }
                    }
                }
            });
        }
    }
}




