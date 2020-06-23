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
                sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
                sandwich.setDescription(sandwichJson.getString("description"));
                sandwich.setImage(sandwichJson.getString("image"));

//                mainName and alsoKnownAs are bundled in a JSONObject known
//                as name. Drill down through name to get them.
                JSONObject tempJSONObject = sandwichJson.getJSONObject("name");
                sandwich.setMainName(tempJSONObject.getString("mainName"));
                JSONArray tempJSONArray = tempJSONObject.getJSONArray("alsoKnownAs");
                List<String> tempList = new ArrayList<String>(getListFromJSON(tempJSONArray));
                sandwich.setAlsoKnownAs(tempList);

//                Ingredients is just a list in the base JSONObject
                tempJSONArray = sandwichJson.getJSONArray("ingredients");
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
