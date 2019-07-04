package server;

import io.joshworks.restclient.http.HttpResponse;
import io.joshworks.restclient.http.Unirest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBMethods {

    public static void initDB(String DBName) {
        DBMethods(DBName, "init_DB", null, null);
    }

    public static void put(String DBName, String key, String value) {
        delete(DBName, key);
        DBMethods(DBName, "put", key, value);
    }

    public static String get(String DBName, String key) {
        return DBMethods(DBName, "get", key, null);
    }

    public static void delete(String DBName, String key) {
        DBMethods(DBName, "del_from_DB", key, null);
    }

    public static ArrayList<String> getAllKeys(String DBName) {
        return getAll(DBName, "keys");
    }

    public static ArrayList<String> getAllValues(String DBName) {
        return getAll(DBName, "values");
    }

    //----------------------------

    private static ArrayList<String> getAll(String DBName, String keysOrValues) {
        ArrayList<String> output = new ArrayList<>();
        String response = DBMethods(DBName, "get_all_" + keysOrValues, null, null);
        Pattern pattern = Pattern.compile("(\"\\w*\")");
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            output.add(matcher.group(1));
        }
        return output;
    }

    private static String DBMethods(String DBName, String whatDoWeWantToDo, String key, String value) {
        final String baseAddress = "http://127.0.0.1:8080/";
        final String path = whatDoWeWantToDo;
        HttpResponse<String> response;
        HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("name", DBName);
        parameters.put("key", key);
        parameters.put("value", value);
        response = Unirest.post(baseAddress + path).fields(parameters).asString();

        if (response.getStatus() != 200) {
            try {
                throw new Exception("There is a problem about: \"" + whatDoWeWantToDo + "\"");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response.getBody();
    }
}