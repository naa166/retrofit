package com.bmkg.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.bmkg.retrofit.model.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvLokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLokasi = findViewById(R.id.rvLokasi);
        rvLokasi.setLayoutManager(new LinearLayoutManager(this));

        List<Location> list = loadLocations();

        LocationAdapter adapter = new LocationAdapter(list, loc -> {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            intent.putExtra("kode_wilayah", loc.getCode());
            startActivity(intent);
        });

        rvLokasi.setAdapter(adapter);
    }

    private List<Location> loadLocations() {
        List<Location> list = new ArrayList<>();

        try {
            InputStream is = getAssets().open("lokasi.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Location>>() {}.getType();
            list = gson.fromJson(json, listType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
