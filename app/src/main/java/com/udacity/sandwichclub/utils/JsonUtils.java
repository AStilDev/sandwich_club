package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwichResult = null;
        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject sandwichJSON = new JSONObject(json);

            // names
            JSONObject nameObj = sandwichJSON.getJSONObject(NAME);
            mainName = nameObj.getString(MAIN_NAME);
            JSONArray alsoKnownAsArray = nameObj.getJSONArray(ALSO_KNOWN_AS);
            for (int i = 0; i < alsoKnownAsArray.length(); i++){
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }

            placeOfOrigin = sandwichJSON.getString(PLACE_OF_ORIGIN);
            description = sandwichJSON.getString(DESCRIPTION);
            image = sandwichJSON.getString(IMAGE);

            // ingredients
            JSONArray ingredientsArray = sandwichJSON.getJSONArray(INGREDIENTS);
            for (int j = 0; j < ingredientsArray.length(); j++){
                ingredients.add(ingredientsArray.getString(j));
            }

            sandwichResult = new Sandwich(
                    mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        catch (JSONException jsone){
            // error
            Log.e("JSONError", "Error: " + jsone.getMessage());
        }

        return sandwichResult;
    }
}
