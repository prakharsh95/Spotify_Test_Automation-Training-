package com.spotify.oauth2.api.applicationAPI;

import com.spotify.oauth2.api.ReuseableAPI;
import com.spotify.oauth2.api.RouteConstants;
import com.spotify.oauth2.pojo.PlayList;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.TokenManager.getToken;


public class PlayListAPI {

    @Step
    public static Response post(PlayList requestPlayList){
        return ReuseableAPI.post(requestPlayList,RouteConstants.USERS+"/"+ ConfigLoader.getInstance().getUserId() +RouteConstants.PLAYLISTS,getToken());
    }

    @Step
    public static  Response post(PlayList requestPlayList, String token){
        return ReuseableAPI.post(requestPlayList,RouteConstants.USERS+"/"+ConfigLoader.getInstance().getUserId()+RouteConstants.PLAYLISTS,token);
    }

    @Step
    public static Response get(String playListId){

        return  ReuseableAPI.get(RouteConstants.PLAYLISTS+"/"+playListId,getToken());
    }
    @Step
    public static Response put(PlayList requestPlayList,String playListId){
        return ReuseableAPI.put(requestPlayList, RouteConstants.PLAYLISTS+"/" +playListId,getToken());
    }
}
