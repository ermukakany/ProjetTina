package cloudm120152016.puy2docs.utils.retrofit;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cloudm120152016.puy2docs.models.Item;
import cloudm120152016.puy2docs.models.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerQueries {

    public String userSignIn(String username, String password) {

        UserService userService = ServiceGenerator.createService(UserService.class);
        Call<Token> token = userService.login(username, password);
        Token task = new Token();
        try {
            task = token.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return task.getToken();
    }

    public ArrayList<Item> getFolderContent(String id) {

        //final List<Item> items = new ArrayList<>();

        UserService userService = ServiceGenerator.createService(UserService.class, "65b6c2525ccafb37eafbc8495dd833cee63764d1");
        Call<ArrayList<Item>> data;
        ArrayList<Item> items = new ArrayList<>();

        if (TextUtils.isEmpty(id)) {
            data = userService.getRoot();
        }

        else {
            data = userService.getFolder(id);
        }
        //Call<ArrayList<Item>> data = userService.getRoot();


        try {
            items = data.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }
}
