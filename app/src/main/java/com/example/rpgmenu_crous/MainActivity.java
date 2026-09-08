package com.example.rpgmenu_crous;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.util.Log;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.croustillant.menu/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CrousApiClient client = retrofit.create(CrousApiClient.class);
        client.getRestaurantsByRegion(1).enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Restaurant> restaurants = response.body();
                    for (Restaurant r : restaurants) {
                        Log.d("CROUS_API", "Resto: " + r.getName() + " - " + r.getAddress());
                    }
                } else {
                    Log.e("CROUS_API", "Erreur serveur : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e("CROUS_API", "Échec de la connexion : " + t.getMessage());
            }
        });
    }

    public void scrapMenu(String text) {
        Pattern pattern = Pattern.compile("Menu du .*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        boolean matchFound = matcher.find();
        if(matchFound) {
            System.out.println("Match found");
        } else System.out.println("Match not found");
    }
}

