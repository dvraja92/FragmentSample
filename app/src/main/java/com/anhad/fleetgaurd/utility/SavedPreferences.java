package com.anhad.fleetgaurd.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.anhad.fleetgaurd.application.FleetGaurdApplication;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Anhad on 07-01-2017.
 */
public class SavedPreferences {

        final static String user_pref = "FleetGaurd";

        private SharedPreferences sharedPreferences;
        private static SavedPreferences savedPreferences;

        public static final String FULL_NAME = "name";
        public static final String ID = "id";
        public static final String ROLE = "role";

        private SavedPreferences() {}

        public static void init(Context context){
            if(savedPreferences == null){
                savedPreferences = new SavedPreferences();
                savedPreferences.sharedPreferences = context.getSharedPreferences(user_pref, Context.MODE_PRIVATE);
            }
        }

        public static SavedPreferences getInstance(){
            if(savedPreferences == null){
                init(FleetGaurdApplication.getInstance());
            }
            return savedPreferences;
        }

        public void saveStringValue(String value, String key){
            SharedPreferences.Editor editor = getEditor();
            editor.putString(key, value);
            editor.commit();
        }

        public void saveBooleanValue(boolean value, String key){
            SharedPreferences.Editor editor = getEditor();
            editor.putBoolean(key, value);
            editor.commit();
        }

        public String getStringValue(String key){
            return sharedPreferences.getString(key, "");
        }

        public boolean getBooleanValue(String key){
            return sharedPreferences.getBoolean(key,false);
        }

        public void saveIntValue(int value, String key){
            SharedPreferences.Editor editor = getEditor();
            editor.putInt(key, value);
            editor.commit();
        }

        public int getIntValue(String key){
            return sharedPreferences.getInt(key, 0);
        }

        public void removeValue(String key){
            SharedPreferences.Editor editor = getEditor();
            editor.remove(key);
            editor.commit();
        }

        private SharedPreferences.Editor getEditor(){
            return sharedPreferences.edit();
        }

        public void clearAll(){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
}
