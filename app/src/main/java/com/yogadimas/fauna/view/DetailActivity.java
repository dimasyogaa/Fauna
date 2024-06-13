package com.yogadimas.fauna.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.yogadimas.fauna.R;

import co.mobiwise.library.InteractivePlayerView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ID_ANIMAL = "id_animal";
    private final Runnable mRunnable;
    private int id;
    private TextView tvName, tvDesc, tvKingdom, tvPhylum, tvClass, tvOrder, tvFamily;
    private InteractivePlayerView ipv;
    private MediaPlayer mediaPlayer;
    private ImageButton ibPlay;
    private Handler mHandler;

    // Update
    {
        mRunnable = new Runnable() {
            public void run() {
                if (ipv.getProgress() == 0 && !ipv.isPlaying()) {
                    ibPlay.setBackgroundResource(R.drawable.ic_play);
                }
                DetailActivity.this.mHandler.postDelayed(mRunnable, 1000);
            }

        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.mHandler = new Handler();
        this.mHandler.postDelayed(mRunnable, 1000);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> finish());

        tvName = findViewById(R.id.tv_name);
        tvDesc = findViewById(R.id.tv_desc);
        tvKingdom = findViewById(R.id.tv_kingdom);
        tvPhylum = findViewById(R.id.tv_phylum);
        tvClass = findViewById(R.id.tv_class);
        tvOrder = findViewById(R.id.tv_order);
        tvFamily = findViewById(R.id.tv_family);
        ibPlay = findViewById(R.id.ib_play);
        Button btnWeb = findViewById(R.id.btn_web);

        String animalId = getIntent().getExtras().getString(ID_ANIMAL);
        id = Integer.parseInt(animalId);

        loadDetailAnimal();
        callAudio();

        ibPlay.setOnClickListener(this);
        btnWeb.setOnClickListener(this);


    }

    private void loadDetailAnimal() {
        String[] dataName = getResources().getStringArray(R.array.data_animal_name);
        String[] dataDescription = getResources().getStringArray(R.array.data_animal_description);
        String[] dataKingdom = getResources().getStringArray(R.array.data_animal_kingdom);
        String[] dataPhylum = getResources().getStringArray(R.array.data_animal_phylum);
        String[] dataClass = getResources().getStringArray(R.array.data_animal_class);
        String[] dataOrder = getResources().getStringArray(R.array.data_animal_order);
        String[] dataFamily = getResources().getStringArray(R.array.data_animal_family);
        tvName.setText(dataName[id]);
        tvDesc.setText(dataDescription[id]);
        tvKingdom.setText(dataKingdom[id]);
        tvPhylum.setText(dataPhylum[id]);
        tvClass.setText(dataClass[id]);
        tvOrder.setText(dataOrder[id]);
        tvFamily.setText(dataFamily[id]);
    }

    private void callAudio() {
        switch (id) {
            case 0:
                audio(R.raw.rooster, "rooster");
                break;
            case 1:
                audio(R.raw.rhinoceros, "rhinoceros");
                break;
            case 2:
                audio(R.raw.duck, "duck");
                break;
            case 3:
                audio(R.raw.owl, "owl");
                break;
            case 4:
                audio(R.raw.crow, "crow");
                break;
            case 5:
                audio(R.raw.elephant, "elephant");
                break;
            case 6:
                audio(R.raw.tiger, "tiger");
                break;
            case 7:
                audio(R.raw.cat, "cat");
                break;
            case 8:
                audio(R.raw.horse, "horse");
                break;
            case 9:
                audio(R.raw.dove, "dove");
                break;
            case 10:
                audio(R.raw.cow, "cow");
                break;
            case 11:
                audio(R.raw.lion, "lion");
                break;
            default:
                audio(R.raw.rat, "rat");
                break;
        }
    }

    private void audio(int raw, String resource) {
        @SuppressLint("Recycle")
        TypedArray dataPhoto = getResources().obtainTypedArray(R.array.data_animal_photo);
        mediaPlayer = MediaPlayer.create(DetailActivity.this, raw);
        Uri uri = Uri.parse("android.resource://com.yogadimas.fauna/raw/" + resource);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(DetailActivity.this, uri);
        String durationString = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int milli = Integer.parseInt(durationString);
        milli = milli / 1000;
        ipv = findViewById(R.id.ipv);
        ipv.setCoverDrawable(dataPhoto.getResourceId(id, -1));
        ipv.setMax(milli);
        ipv.setProgress(0);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ib_play) {
            play();
        } else {
            web();
        }
    }

    private void play() {
        if (!ipv.isPlaying()) {
            ipv.start();
            ibPlay.setBackgroundResource(R.drawable.ic_pause);
            mediaPlayer.start();
        } else {
            ipv.stop();
            ibPlay.setBackgroundResource(R.drawable.ic_play);
            mediaPlayer.pause();
        }
    }


    private void web() {
        String[] dataWeb = getResources().getStringArray(R.array.data_animal_web);
        Intent intent = new Intent(DetailActivity.this, WebActivity.class);
        intent.putExtra(WebActivity.LINK_WEB, dataWeb[id]);
        startActivity(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        this.mHandler.removeCallbacks(mRunnable);
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.mHandler.postDelayed(mRunnable, 1000);
    }
}