package com.codsoft.quoteoftheday;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView quoteTextView;
    private Button shareButton;
    private Button favoriteButton;
    private Button viewFavoritesButton;
    private ArrayList<String> quotes;
    private String currentQuote;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "QuotePrefs";
    private static final String FAVORITE_QUOTES_KEY = "FavoriteQuotes";
    private static final String CURRENT_QUOTE_KEY = "CurrentQuote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTextView = findViewById(R.id.titleTextView);
        quoteTextView = findViewById(R.id.quoteTextView);
        shareButton = findViewById(R.id.shareButton);
        favoriteButton = findViewById(R.id.favoriteButton);
        viewFavoritesButton = findViewById(R.id.viewFavoritesButton);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Set title
        titleTextView.setText("Quote of the Day");

        // Sample quotes
        quotes = new ArrayList<>();
        quotes.add("The best way to predict the future is to invent it.");
        quotes.add("Life is 10% what happens to us and 90% how we react to it.");
        quotes.add("The only way to do great work is to love what you do.");
        quotes.add("The greatest glory in living lies not in never falling, but in rising every time we fall.");
        quotes.add("The way to get started is to quit talking and begin doing.");
        quotes.add("Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma – which is living with the results of other people's thinking.");
        quotes.add("The future belongs to those who believe in the beauty of their dreams.");
        quotes.add("If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough.");
        quotes.add("If you set your goals ridiculously high and it's a failure, you will fail above everyone else's success.");
        quotes.add("You may say I'm a dreamer, but I'm not the only one. I hope someday you'll join us. And the world will live as one.");
        quotes.add("You must be the change you wish to see in the world.");
        quotes.add("Spread love everywhere you go. Let no one ever come to you without leaving happier.");
        quotes.add("The only thing we have to fear is fear itself.");
        quotes.add("Darkness cannot drive out darkness; only light can do that. Hate cannot drive out hate: only love can do that.");
        quotes.add("Do one thing every day that scares you.");
        quotes.add("Well done is better than well said.");
        quotes.add("The best and most beautiful things in the world cannot be seen or even touched - they must be felt with the heart.");
        quotes.add("It is during our darkest moments that we must focus to see the light.");
        quotes.add("Do not go where the path may lead; go instead where there is no path and leave a trail.");
        quotes.add("Be yourself; everyone else is already taken.");

        displayRandomQuote();

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuote(currentQuote);
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavoriteQuote(currentQuote);
            }
        });

        viewFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFavoriteQuotes();
            }
        });

        scheduleDailyQuoteRefresh();
    }

    private void displayRandomQuote() {
        Random random = new Random();
        currentQuote = quotes.get(random.nextInt(quotes.size()));
        quoteTextView.setText(currentQuote);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_QUOTE_KEY, currentQuote);
        editor.apply();
    }

    private void shareQuote(String quote) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, quote);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void saveFavoriteQuote(String quote) {
        List<String> favoriteQuotes = getFavoriteQuotes();
        if (!favoriteQuotes.contains(quote)) {
            favoriteQuotes.add(quote);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favoriteQuotes);
        editor.putString(FAVORITE_QUOTES_KEY, json);
        editor.apply();
    }

    private List<String> getFavoriteQuotes() {
        String json = sharedPreferences.getString(FAVORITE_QUOTES_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        List<String> favoriteQuotes = gson.fromJson(json, type);
        return favoriteQuotes != null ? favoriteQuotes : new ArrayList<String>();
    }

    private void viewFavoriteQuotes() {
        Intent intent = new Intent(MainActivity.this, FavoriteQuotesActivity.class);
        startActivity(intent);
    }

    private void scheduleDailyQuoteRefresh() {
        Intent intent = new Intent(this, QuoteRefreshReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        }
    }

}
