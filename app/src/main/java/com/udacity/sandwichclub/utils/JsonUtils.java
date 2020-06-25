package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String JSON_NAME_KEY = "name";
    public static final String JSON_MAIN_NAME_KEY = "mainName";
    public static final String JSON_ORIGIN_KEY = "placeOfOrigin";
    public static final String JSON_DESCRIPTION_KEY = "description";
    public static final String JSON_IMAGE_KEY = "image";
    public static final String JSON_AKA_KEY = "alsoKnownAs";
    public static final String JSON_INGREDIENTS_KEY = "ingredients";

    //     The method to parse the JSON data for the selected Sandwich
    public static Sandwich parseSandwichJson(String json) {
        JSONObject sandwichJson = null;
        try{
            sandwichJson = new JSONObject(json);
            if(sandwichJson != null){
//                Setting the strings of the Sandwich Object
                Sandwich sandwich = new Sandwich();
                sandwich.setPlaceOfOrigin(sandwichJson.optString(JSON_ORIGIN_KEY));
                sandwich.setDescription(sandwichJson.optString(JSON_DESCRIPTION_KEY));
                sandwich.setImage(sandwichJson.optString(JSON_IMAGE_KEY));

//                mainName and alsoKnownAs are bundled in a JSONObject known
//                as name. Drill down through name to get them.
                JSONObject tempJSONObject = sandwichJson.getJSONObject(JSON_NAME_KEY);
                sandwich.setMainName(tempJSONObject.optString(JSON_MAIN_NAME_KEY));
                JSONArray tempJSONArray = tempJSONObject.getJSONArray(JSON_AKA_KEY);
                List<String> tempList = new ArrayList<String>(getListFromJSON(tempJSONArray));
                sandwich.setAlsoKnownAs(tempList);

//                Ingredients is just a list in the base JSONObject
                tempJSONArray = sandwichJson.getJSONArray(JSON_INGREDIENTS_KEY);
                tempList = new ArrayList<String>(getListFromJSON(tempJSONArray));
                sandwich.setIngredients(tempList);

//                Our sandwich is assembled, time to return it
                return sandwich;
            }
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
//  Helper method for getting a List of Strings from a JSONArray
    public static List<String> getListFromJSON(JSONArray tempJSONArray){
        List<String> tempList = new ArrayList<String>();
            for(int i = 0; i < tempJSONArray.length(); i++)
                tempList.add(tempJSONArray.optString(i));
        return tempList;
    }
}
