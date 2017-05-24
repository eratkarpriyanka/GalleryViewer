package com.getaround.getaroundgalleryviewer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.getaround.getaroundgalleryviewer.R;
import com.getaround.getaroundgalleryviewer.adapters.PhotoAdapter;
import com.getaround.getaroundgalleryviewer.models.Photo;
import com.getaround.getaroundgalleryviewer.network.ApiEndpointInterface;
import com.getaround.getaroundgalleryviewer.network.ResponseGetPhoto;
import com.getaround.getaroundgalleryviewer.network.ServiceGenerator;
import com.getaround.getaroundgalleryviewer.uiutils.GridSpacingItemDecoration;

import org.parceler.Parcels;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryViewActivity extends AppCompatActivity implements PhotoAdapter.CustomOnClickListener {

    public static final String TAG = GalleryViewActivity.class.getSimpleName();
    private Button btnLoad;
    private TextView tvEmptyList;
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;
    private MenuItem actionProgressItem;
    private ProgressBar progressView;
    private ArrayList<Photo> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);

        getPhotos();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvEmptyList = (TextView) findViewById(R.id.tvEmptyList);
    }

    /**
     * If photo list not loaded yet, set an empty view to GridView
     */
    private void setEmptyView() {

        tvEmptyList.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * Fetch photo list from server
     */
    private void getPhotos() {

        final Handler handler = new Handler();

        showProgressBar();

        ApiEndpointInterface apiService = ServiceGenerator.createService();
        Call<ResponseGetPhoto> call = apiService.getPhotosApi("popular","nature");
        call.enqueue(new Callback<ResponseGetPhoto>() {
            @Override
            public void onResponse(Call<ResponseGetPhoto> call, Response<ResponseGetPhoto> response) {

                if(response!=null) {

                    // added just to show progress
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           hideProgressBar();
                       }
                   },2500);

                    photoList = response.body().getPhotoList();
                    if(photoList == null)
                        setEmptyView();
                    else
                        loadViews(photoList);
                    Log.d(TAG, "response is " + photoList);
                }
            }

            @Override
            public void onFailure(Call<ResponseGetPhoto> call, Throwable t) {

                hideProgressBar();
                // communication failure
                String strCommFailure = getResources().getString(R.string.connection_failure);
                Toast.makeText(GalleryViewActivity.this,""+strCommFailure,Toast.LENGTH_LONG).show();
                Log.d(TAG,"request failure "+t.getMessage());
            }
        });
    }

    private void loadViews(ArrayList<Photo> photoList) {

        tvEmptyList.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new PhotoAdapter(this, photoList);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(this, 2, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        adapter.setClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // Store instance of the menu item containing progress
        actionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        progressView =  (ProgressBar) MenuItemCompat.getActionView(actionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        if(actionProgressItem!=null && progressView!=null) {
            progressView.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        // Hide progress item

        if(actionProgressItem!=null && progressView!=null) {
            progressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        Photo photoInfo = photoList.get(position);
        Intent intent = new Intent(this, PhotoDetailsActivity.class);
        intent.putExtra("photo", Parcels.wrap(photoInfo));
        startActivity(intent);
    }
}
