package com.codsoft.quoteoftheday;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteQuotesActivity extends AppCompatActivity {
    private ListView favoriteQuotesListView;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "QuotePrefs";
    private static final String FAVORITE_QUOTES_KEY = "FavoriteQuotes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_quotes);

        // Set activity title
        setTitle("Favorite Quotes");

        favoriteQuotesListView = findViewById(R.id.favoriteQuotesListView);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        loadFavoriteQuotes();
    }

    private void loadFavoriteQuotes() {
        List<String> favoriteQuotesList = getFavoriteQuotes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteQuotesList);
        favoriteQuotesListView.setAdapter(adapter);
    }

    private List<String> getFavoriteQuotes() {
        String json = sharedPreferences.getString(FAVORITE_QUOTES_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        List<String> favoriteQuotes = gson.fromJson(json, type);
        return favoriteQuotes != null ? favoriteQuotes : new ArrayList<String>();
    }
}
