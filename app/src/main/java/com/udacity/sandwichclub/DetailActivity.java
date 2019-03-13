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

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // TODO
        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        TextView originTV = findViewById(R.id.origin_tv);
        TextView descriptionTV = findViewById(R.id.description_tv);
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);

        List<String> akaList = sandwich.getAlsoKnownAs();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < akaList.size(); i++){
            if (i == 0)
                builder.append(akaList.get(i));
            else
                builder.append(", ").append(akaList.get(i));
        }

        alsoKnownAsTV.setText(builder.toString());

        originTV.setText(sandwich.getPlaceOfOrigin());
        descriptionTV.setText(sandwich.getDescription());

        List<String> ingredientsList = sandwich.getIngredients();
        builder = new StringBuilder();
        for (int j = 0; j < ingredientsList.size(); j++){
            if (j == 0)
                builder.append(ingredientsList.get(j));
            else
                builder.append(", ").append(ingredientsList.get(j));
        }

        ingredientsTV.setText(builder.toString());
    }
}
