package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageViewIngredients = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageViewIngredients);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView textViewAKA = findViewById(R.id.tv_also_known);
        TextView textViewDescription = findViewById(R.id.tv_description);
        TextView textViewIngredients = findViewById(R.id.tv_ingredients);
        TextView textViewOrigin = findViewById(R.id.tv_origin);

        StringBuilder stringBuilder = new StringBuilder();

        // Get the origin and set the TextView's text accordingly
        String origin = sandwich.getPlaceOfOrigin();
        if (origin.isEmpty()) {
            origin = "unknown";
        }
        textViewOrigin.setText(origin);

        // Get the ingredients and set the TextView's text accordingly
        List<String> ingredients = sandwich.getIngredients();
        if (ingredients.size() > 0) {
            for (String ingredient : ingredients) {
                stringBuilder.append(ingredient + ", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()); // delete last comma and space
            textViewIngredients.setText(stringBuilder.toString());
        } else {
            // Hope we'll never get this kind of tasteless sandwich... :D
            textViewIngredients.setText("none");
        }

        // Get the aliases and set the TextView's text accordingly
        List<String> akas = sandwich.getAlsoKnownAs();
        if (akas.size() > 0) {
            stringBuilder = new StringBuilder();
            for (String aka : akas) {
                stringBuilder.append(aka + ", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()); // delete last comma and space
            textViewAKA.setText(stringBuilder.toString());
        } else {
            textViewAKA.setText("none");
        }

        // And finally the description text
        textViewDescription.setText(sandwich.getDescription());
    }
}
