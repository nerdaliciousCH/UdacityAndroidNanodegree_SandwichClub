package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;

        try {
            // Get the JSON objects and JSON arrays
            JSONObject jsonObjSandwich = new JSONObject(json);
            JSONObject jsonObjName = jsonObjSandwich.getJSONObject("name");
            JSONArray jsonArrayAlsoKnownAs = jsonObjName.getJSONArray("alsoKnownAs");
            JSONArray jsonArrayIngredients = jsonObjSandwich.getJSONArray("ingredients");

            // And then get all the values we need
            mainName = jsonObjName.getString("mainName");
            alsoKnownAs = new ArrayList<>();
            int len = jsonArrayAlsoKnownAs.length();
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    alsoKnownAs.add(jsonArrayAlsoKnownAs.getString(i));
                }
            }
            placeOfOrigin = jsonObjSandwich.getString("placeOfOrigin");
            description = jsonObjSandwich.getString("description");
            image = jsonObjSandwich.getString("image");

            ingredients = new ArrayList<>();
            len = jsonArrayIngredients.length();
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    ingredients.add(jsonArrayIngredients.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error during JSON parsing", e);

            return null;
        }
        return new Sandwich(
                mainName,
                alsoKnownAs,
                placeOfOrigin,
                description,
                image,
                ingredients
        );
    }
}
