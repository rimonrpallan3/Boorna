package com.voyager.boorna.activity.login.helper;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.voyager.boorna.activity.login.model.UserDetails;

public class Helper {

    public static void addUserGsonInSharedPrefrences(UserDetails UserDetails, SharedPreferences.Editor editor){
        Gson gson = new Gson();
        String jsonString = gson.toJson(UserDetails);
        //UserDetails user1 = gson.fromJson(jsonString,UserDetails.class);
        if(jsonString!=null) {
            editor.putString("UserDetails", jsonString);
            editor.commit();
            System.out.println("-----------Helper addUserGsonInSharedPrefrences UserDetails : "+jsonString);
        }

    }

}
