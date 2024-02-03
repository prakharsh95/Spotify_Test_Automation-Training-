package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TokenManager {
    private static Instant expiry_time;
    private static String access_token;
    public static String getToken(){
        int expiryDurationInSeconds;
        try {
            if((access_token==null)||Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing the token");
                Response response=renewToken();
                expiryDurationInSeconds=response.path("expires_in");
                expiry_time=Instant.now().plusSeconds(expiryDurationInSeconds-300);
                access_token=response.path("access_token");
            }
            else
                System.out.println("The old token can still be used!!");
        }
        catch (Exception e){
            throw new RuntimeException("Not able to fetch the token!! Tests will be skipped.");
        }
        return access_token;
    }
    private static Response renewToken(){
        HashMap<String,String> formParams=new HashMap<>();
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("client_id",ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret",ConfigLoader.getInstance().getClientSecret());
        formParams.put("refresh_token",ConfigLoader.getInstance().getRefreshToken());
        Response response=ReuseableAPI.postAccount(formParams);
        if(response.statusCode()!=200)
            throw new RuntimeException("Token fetching failed!!\nTests will be skipped");
    return response;
    }
}
