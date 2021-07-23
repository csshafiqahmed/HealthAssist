package in.starbow.fitindia.helper;

import com.google.gson.Gson;

import in.starbow.fitindia.model.User;

import static in.starbow.fitindia.helper.PrefsHelper.getString;
import static in.starbow.fitindia.helper.PrefsHelper.putString;

public class AppPrefs {
    public static final String USER = "user_object";
    public static final String DISEASE = "disease";
    public static final String EMAIL = "email";

    public static void setUserData(User user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        putString(USER,json);
        putString(EMAIL,user.getEmail());
        putString(DISEASE,user.getDisease());
    }

    public static User getUserData(){
        String json = getString(USER,"");
        Gson gson = new Gson();
        return gson.fromJson(json,User.class);
    }

    public static String getEmail(){
        return getString(EMAIL,"");
    }

    public static String getDisease(){
        return getString(DISEASE);
    }
}
