package com.kurtspace.chat.message.clients;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;
import org.assertj.core.util.Lists;

import lombok.Data;
import lombok.experimental.Accessors;

public class UsersClient {

    public List<User> getUsers() {
        URL url;
        try {
            url = new URL("http://users-svc:8777/users");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            String response = IOUtils.toString(con.getInputStream());
            return new Gson().fromJson(response, new TypeToken<ArrayList<User>>() {}.getType());
        } catch ( IOException e) {
            e.printStackTrace();
            return Lists.emptyList();
        }
    }

    @Data
    @Accessors(chain = true)
    public static final class User {
        private int sub;
        private String name;
        private String[] roles;
        private boolean active;
    }
}
