package com.twilio.interview.listenup.Models;

public class UserInfo extends User{
    public int plays;
    public int friends;

    public void setFriends(int friends) {
        this.friends = friends;
    }

    public int getFriends() {
        return friends;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public int getPlays() {
        return plays;
    }
}
