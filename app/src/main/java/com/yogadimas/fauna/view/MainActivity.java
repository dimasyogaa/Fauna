package com.yogadimas.fauna.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yogadimas.fauna.R;
import com.yogadimas.fauna.adapter.Adapter;
import com.yogadimas.fauna.model.Animals;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<Animals> animalsArrayList = new ArrayList<>();
    private ToggleButton btnMoreMenu;
    private RecyclerView rvAnimals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMoreMenu = findViewById(R.id.btn_more_menu);


        rvAnimals = findViewById(R.id.rv_animals);
        rvAnimals.setHasFixedSize(true);

        animalsArrayList.addAll(getListAnimals());
        showRecyclerList();

        btnMoreMenu.setOnClickListener(view -> {
            if (btnMoreMenu.isChecked()) {
                inflate();
            }
        });
    }

    public ArrayList<Animals> getListAnimals() {
        @SuppressLint("Recycle")
        TypedArray dataPhoto = getResources().obtainTypedArray(R.array.data_animal_photo);
        String[] dataId = getResources().getStringArray(R.array.data_animal_id);
        String[] dataName = getResources().getStringArray(R.array.data_animal_name);
        String[] dataDescription = getResources().getStringArray(R.array.data_animal_description);


        ArrayList<Animals> listAnimals = new ArrayList<>();
        for (int i = 0; i < dataId.length; i++) {
            Animals animals = new Animals();
            animals.setPhoto(dataPhoto.getResourceId(i, -1));
            animals.setId(dataId[i]);
            animals.setName(dataName[i]);
            animals.setDesc(dataDescription[i]);
            listAnimals.add(animals);
        }

        return listAnimals;
    }

    private void showRecyclerList() {
        rvAnimals.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter(animalsArrayList);
        rvAnimals.setAdapter(adapter);
    }

    @SuppressLint("NonConstantResourceId")
    private void inflate() {
        PopupMenu popup = new PopupMenu(MainActivity.this, btnMoreMenu);
        popup.getMenuInflater().inflate(R.menu.menu_option, popup.getMenu());
        setTheme(R.style.MenuTheme);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_team_us:
                    Intent goToTimeUs = new Intent(MainActivity.this, TeamUsActivity.class);
                    startActivity(goToTimeUs);
                    break;
                case R.id.item_language:
                    Intent goToLanguage = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                    startActivity(goToLanguage);
                    break;
                case R.id.item_information_app:
                    Intent goToInformation = new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(goToInformation);
                    break;
            }
            return true;
        });
        popup.show();
        btnMoreMenu.setChecked(true);
        popup.setOnDismissListener(menu -> btnMoreMenu.setChecked(false));
    }

}