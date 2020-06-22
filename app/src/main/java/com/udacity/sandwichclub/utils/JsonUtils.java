package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

//     The method to parse the JSON data for the selected Sandwich
    public static Sandwich parseSandwichJson(String json) {
        JSONObject sandwichJson = null;
        try{
            sandwichJson = new JSONObject(json);
            if(sandwichJson != null){
//                Setting the strings of the Sandwich Object
                Sandwich sandwich = new Sandwich();
//                TODO must drill into name list to get mainname and alsoknownas list
                Log.d("SANDWICHCLUBDEBUG", "Assemble the Sandwich");
                Log.d("SANDWICHCLUBDEBUG", json);
                sandwich.setMainName(sandwichJson.getString("mainName"));
                sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
                sandwich.setDescription(sandwichJson.getString("description"));
                sandwich.setImage(sandwichJson.getString("image"));

//                Using our helper method to populate the List fields for our Sandwich
                JSONArray tempJSONArray = sandwichJson.getJSONArray("ingredients");
                List<String> tempList = new ArrayList<String>(getListFromJSON(tempJSONArray));
                sandwich.setIngredients(tempList);
                tempJSONArray = sandwichJson.getJSONArray("alsoKnownAs");
                tempList = new ArrayList<String>(getListFromJSON(tempJSONArray));
                sandwich.setAlsoKnownAs(tempList);

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
        try{
            for(int i = 0; i < tempJSONArray.length(); i++)
                tempList.add(tempJSONArray.getString(i));
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        return tempList;
    }
}
