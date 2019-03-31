package com.twilio.interview.listenup.Models;

import java.util.Collection;
import java.util.List;

public class Friends extends Serialization {
    Collection<User> friends;
    String uri;

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public Collection<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
