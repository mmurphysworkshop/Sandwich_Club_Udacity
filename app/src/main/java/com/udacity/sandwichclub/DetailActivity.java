package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    private static final String SANDWICH__CLUB = "Sandwich_Club";
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
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView origin_tv = findViewById(R.id.origin_tv);
        TextView also_known_tv = findViewById(R.id.also_known_tv);
        TextView description_tv = findViewById(R.id.description_tv);
        TextView ingredients_tv = findViewById(R.id.ingredients_tv);

        LinearLayout also_known_ll = findViewById(R.id.also_known_ll);
        LinearLayout origin_ll = findViewById(R.id.origin_ll);
        LinearLayout description_ll = findViewById(R.id.description_ll);
        LinearLayout ingredients_ll = findViewById(R.id.ingredients_ll);

        populateTextViews(also_known_ll, also_known_tv, sandwich.getAlsoKnownAs().toArray(new String[sandwich.getAlsoKnownAs().size()]));
        populateTextViews(origin_ll, origin_tv, sandwich.getPlaceOfOrigin());
        populateTextViews(description_ll, description_tv, sandwich.getDescription());
        populateTextViews(ingredients_ll, ingredients_tv, sandwich.getIngredients().toArray(new String[sandwich.getIngredients().size()]));
    }

//    method to populate textViews and if there is not data to populate it hides the linear layout
//    the text view is a part of.
    private void populateTextViews(LinearLayout layoutWrapper, TextView viewToPopulate, String... args) {

        if(args == null || args.length == 0){
            layoutWrapper.setVisibility(View.GONE);
            return;
        }
        else{
//            I found that the Lists needed to be checked if they were empty but that did not
//            account for if the country of origin is blank
            if(args[0].equals("")) {
                layoutWrapper.setVisibility(View.GONE);
                return;
            }
            layoutWrapper.setVisibility(View.VISIBLE);
            String delim = ", ";
            viewToPopulate.append(TextUtils.join(delim, args));
        }
    }
}


