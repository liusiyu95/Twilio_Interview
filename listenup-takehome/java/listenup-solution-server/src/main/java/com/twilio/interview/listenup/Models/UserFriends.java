package com.twilio.interview.listenup.Models;

import java.util.Collection;
import java.util.List;

public class UserFriends extends Serialization {
    Collection<String> friends;
    String uri;

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public Collection<String> getFriends() {
        return friends;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
