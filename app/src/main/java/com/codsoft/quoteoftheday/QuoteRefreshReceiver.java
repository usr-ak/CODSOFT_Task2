package com.codsoft.quoteoftheday;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Random;

public class QuoteRefreshReceiver extends BroadcastReceiver {
    private static final String PREFS_NAME = "QuotePrefs";
    private static final String CURRENT_QUOTE_KEY = "CurrentQuote";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        ArrayList<String> quotes = new ArrayList<>();
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

        Random random = new Random();
        String newQuote = quotes.get(random.nextInt(quotes.size()));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CURRENT_QUOTE_KEY, newQuote);
        editor.apply();
    }
}
