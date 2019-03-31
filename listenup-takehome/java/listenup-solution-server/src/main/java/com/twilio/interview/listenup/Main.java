package com.twilio.interview.listenup;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.port;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;

public class Main {
    private static final String URL = "/users";
    private static final String RESPONSE_TYPE = "application/json";
    private static final String HTTP_IO_EXP = "Unexpected IOException ";
    private static final String JSON_MATCH_EXP = "Unable to match user info for ";
    private static final int PORT_NUM = 8005;
    public static void main(String[] args) {
        port(PORT_NUM);
        get(URL, (req, res) -> {
                    res.type(RESPONSE_TYPE);
                    try {
                        return new Gson().toJson(ServiceManager.sharedInstance().getAllUserInfo());
                    }  catch (IOException io) {
                        halt(500, HTTP_IO_EXP + io.getMessage());
                        return null;
                    } catch (JsonSyntaxException jsx) {
                        halt(500, JSON_MATCH_EXP);
                        return null;
                    }
        });

        get(URL + "/:username", (req, res) -> {
            res.type(RESPONSE_TYPE);
            try {
                return new Gson().toJson(ServiceManager.sharedInstance().getUserTrack(req.params(":username")));
            }  catch (IOException io) {
                halt(500, HTTP_IO_EXP + io.getMessage());
                return null;
            } catch (JsonSyntaxException jsx) {
                halt(500, JSON_MATCH_EXP + req.params(":username"));
                return null;
            }
        });
    }
}
