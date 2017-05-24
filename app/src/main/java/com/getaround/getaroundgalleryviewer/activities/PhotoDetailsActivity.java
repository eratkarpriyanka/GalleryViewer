package com.getaround.getaroundgalleryviewer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.getaround.getaroundgalleryviewer.R;
import com.getaround.getaroundgalleryviewer.models.Photo;
import com.getaround.getaroundgalleryviewer.uiutils.TextUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class PhotoDetailsActivity extends Activity {

    private static final String TAG = PhotoDetailsActivity.class.getSimpleName();
    private TextView tvTitle;
    private TextView tvDescription;
    private ImageView ivPhoto;
    private ProgressBar progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        setViews();
    }

    private void setViews() {

        Photo photo = (Photo) Parcels.unwrap(getIntent().getParcelableExtra("photo"));
        progressView = (ProgressBar) findViewById(R.id.pbProgressAction);

        tvTitle = (TextView)findViewById(R.id.title);
        tvDescription = (TextView) findViewById(R.id.photoDesc);
        String name = photo.getName();
        String description = photo.getDescription();
        Log.d(TAG,"DESCRIPTION "+description);

        if(name!=null)
            tvTitle.setText(TextUtils.toTitleCase(name));

        if( description!=null ){
            tvDescription.setText(TextUtils.toTitleCase(description));
        }

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        final Handler handler = new Handler();

        showProgressBar();

        Picasso.with(this).load(photo.getImgUrl()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(ivPhoto, new Callback() {

            @Override
            public void onSuccess() {

                handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            hideProgressBar();
                                        }
                                    },1500);

            }

            @Override
            public void onError() {

                hideProgressBar();
                String strCommFailure = getResources().getString(R.string.photo_load_fail);
                Toast.makeText(PhotoDetailsActivity.this,""+strCommFailure,Toast.LENGTH_LONG).show();
            }
        });

    }

    public void showProgressBar() {
        // Show progress item
        if(progressView!=null) {
            progressView.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        // Hide progress item

        if(progressView!=null) {
            progressView.setVisibility(View.GONE);
        }
    }
}
