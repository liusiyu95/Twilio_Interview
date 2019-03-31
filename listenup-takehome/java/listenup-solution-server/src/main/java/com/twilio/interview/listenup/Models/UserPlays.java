package com.twilio.interview.listenup.Models;

import java.util.List;

public class UserPlays extends Serialization {
    private List<String> plays;
    private String uri;

    public List<String> getPlays() {
        return plays;
    }

    public void setPlays(List<String> plays) {
        this.plays = plays;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
