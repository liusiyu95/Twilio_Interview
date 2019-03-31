package com.twilio.interview.listenup.Models;

import java.util.Collection;

public class UserPool extends Serialization {
    private Collection<UserInfo> users;
    private String uri;

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUsers(Collection<UserInfo> users) {
        this.users = users;
    }

    public Collection<UserInfo> getUsers() {
        return users;
    }
}
