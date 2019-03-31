package com.twilio.interview.listenup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.twilio.interview.listenup.Models.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ServiceManager {

    private HttpClient httpClient;
    private HttpGet getRequest;
    private HttpResponse httpResponse;
    private InputStream responseStream;
    private InputStreamReader inputStreamReader;
    private Gson gson;
    private static ServiceManager sharedInstance = null;

    public static final String LOCAL_HOST = "http://localhost:";
    public static final String FRIEND_PORT = "8000";
    public static final String PLAY_PORT = "8001";
    public static final String FRIEND_PREFIX = "/friends";
    public static final String PLAY_PREFIX = "/plays";
    public static final String USER_PREFIX = "/users";
    // http://localhost:8000/friends
    public static final String ALL_USERS_FRIENDS_URL = LOCAL_HOST + FRIEND_PORT + FRIEND_PREFIX;
    // http://localhost:8001/plays
    public static final String ALL_USERS_PLAY_URL = LOCAL_HOST + PLAY_PORT + PLAY_PREFIX;

    private ServiceManager() {
        httpClient = HttpClientBuilder.create().build();
        gson = new GsonBuilder().create();
    }

    public static ServiceManager sharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new ServiceManager();
        }
        return sharedInstance;
    }

    public UserPool getAllUserInfo() throws IOException, JsonSyntaxException {
        Map<String, UserInfo> map = new HashMap<>();
        Friends allfriends = getAllUserFriends(ALL_USERS_FRIENDS_URL);
        for (User user: allfriends.getFriends()) {
            String username = user.getUsername();
            UserFriends userFriends = getUserFriends(ALL_USERS_FRIENDS_URL + "/" + username);
            map.putIfAbsent(username, new UserInfo());
            UserInfo userInfo = map.get(username);
            userInfo.setUsername(username);
            userInfo.setUri(USER_PREFIX + "/" + username);
            userInfo.setFriends(userFriends.getFriends().size());
        }

        Plays allPlays = getAllPlays(ALL_USERS_PLAY_URL);
        for (User user: allPlays.getUsers()) {
            String username = user.getUsername();
            UserPlays userPlays = getUserPlays(ALL_USERS_PLAY_URL + "/" + username);
            map.putIfAbsent(username, new UserInfo());
            UserInfo userInfo = map.get(username);
            userInfo.setUsername(username);
            userInfo.setUri(USER_PREFIX + "/" + username);
            userInfo.setPlays(userPlays.getPlays().size());
        }
        UserPool userPool = new UserPool();
        userPool.setUsers(map.values());
        userPool.setUri(USER_PREFIX);
        return userPool;
    }

    public UserTrack getUserTrack(String username) throws IOException, JsonSyntaxException {
        UserFriends userFriends = getUserFriends(ALL_USERS_FRIENDS_URL + "/" + username);
        UserPlays userPlays = getUserPlays(ALL_USERS_PLAY_URL + "/" + username);
        if (userFriends == null && userPlays == null)
            return new UserTrack();
        UserTrack userTrack = new UserTrack();
        userTrack.setUsername(username);
        userTrack.setFriends(userFriends.getFriends().size());
        userTrack.setPlays(userPlays.getPlays().size());

        Supplier<List<String>> supplier = () -> new LinkedList<String>();
        List<String> userPlaysTrack = userPlays.getPlays().stream().
                filter(i -> Collections.frequency(userPlays.getPlays(), i)
                == 1).collect(Collectors.toCollection(supplier));
        userTrack.setTracks(userPlaysTrack.size());
        userTrack.setUri(USER_PREFIX + "/" + username);
        return userTrack;
    }

    public Friends getAllUserFriends(String url) throws IOException, JsonSyntaxException {
        inputStreamReader = httpResponseStream(url);
        return gson.fromJson(inputStreamReader, Friends.class);
    }

    public Plays getAllPlays(String url) throws IOException, JsonSyntaxException {
        inputStreamReader = httpResponseStream(url);
        return gson.fromJson(inputStreamReader, Plays.class);
    }

    public UserFriends getUserFriends(String url) throws IOException, JsonSyntaxException {
        inputStreamReader = httpResponseStream(url);
        return gson.fromJson(inputStreamReader, UserFriends.class);
    }

    public UserPlays getUserPlays(String url) throws IOException, JsonSyntaxException {
        inputStreamReader = httpResponseStream(url);
        return gson.fromJson(inputStreamReader, UserPlays.class);
    }

    private InputStreamReader httpResponseStream(String url) throws IOException, JsonSyntaxException {
        getRequest = new HttpGet(url);
        httpResponse = httpClient.execute(getRequest);
        responseStream = httpResponse.getEntity().getContent();
        return new InputStreamReader(responseStream);
    }
}