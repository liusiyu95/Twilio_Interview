package com.twilio.interview.listenup.Models;

import java.util.Collection;
import java.util.List;

public class Plays extends Serialization {
    Collection<User> users;
    String uri;

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
