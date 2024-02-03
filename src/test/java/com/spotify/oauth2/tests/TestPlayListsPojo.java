package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.applicationAPI.PlayListAPI;
import com.spotify.oauth2.api.applicationAPI.StatusCode;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.ErrorRoot;
import com.spotify.oauth2.pojo.PlayList;
import io.qameta.allure.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
//Below annotations can be used at the test level also
@Epic("Jan 2024 Epic")
@Feature("Playlist API")
public class TestPlayListsPojo extends BaseTest{
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String playListId;
    PlayList playListPayload= PlayList.builder().build();
    @BeforeClass
    public void setup(){
        requestSpecification=getRequestSpecification();

        responseSpecification=getResponseSpecification();
    }

    @Story("Create playlist story")
    @Link("xyz")
    @Issue("EQ-8765")
    @TmsLink("123456")
    @Description("User is able to create a new playlist")
    @Test(description = "User is able to create a new playlist")
    public void verifyCreatePlaylist(){
        String name="Playlist_"+ Long.toString(Instant.now().getEpochSecond());
        //We have implemented Builder pattern with POJO classes now. It is like a method chaining where each method returns the object of the same class
/*
        playListPayload.setName(name)
                .setDescription("Random description created through POJO")
                .setPublic(false);
*/
        playListPayload=playListBuilder(name,"Random description created through POJO",false);

        Response response=PlayListAPI.post(playListPayload);
        assertThat(response.statusCode(),equalTo(StatusCode.CODE_201.code));
        PlayList playListResponse=response.as(PlayList.class);
        assertPlayListValues(playListResponse);
        Assert.assertNotNull(playListResponse.getId(),"ID value is null");
        playListId=playListResponse.getId();
    }

    @Story("Update playlist story")
    @Link("xyz")
    @Issue("EQ-8765")
    @TmsLink("123456")
    @Description("User is able to update a playlist")
    @Test(dependsOnMethods = "verifyCreatePlaylist",priority = 2,description = "User is able to update a playlist")
    public void verifyUpdatePlaylist(){
        String name="Playlist_Updated_"+ Long.toString(Instant.now().getEpochSecond());
        //We have implemented Builder pattern with POJO classes now. It is like a method chaining where each method returns the object of the same class
/*
        playList.setName(name)
                .setDescription("Updated playlist description")
                .setPublic(false);
*/
        PlayList playList=playListBuilder(name,"Updated playlist description",false);

        Response response=PlayListAPI.put(playList,playListId);

        assertThat(response.statusCode(),equalTo(StatusCode.CODE_200.code));
    }

    //public must be false in response. It is a defect
    @Story("Get playlist story")
    @Link("xyz")
    @Issue("EQ-8765")
    @TmsLink("123456")
    @Description("User is able to get details of a playlist")
    @Test(dependsOnMethods = "verifyCreatePlaylist",priority = 1,description = "User is able to get details of a playlist")
    public void verifyGetPlayList(){
        Response response=PlayListAPI.get(playListId);
        assertThat(response.statusCode(),equalTo(StatusCode.CODE_200.code));
        PlayList playListResponse= response.as(PlayList.class);
        assertPlayListValues(playListResponse);
        assertThat(playListResponse.getId(),equalTo(playListId));

    }

    @Story("Create playlist story")
    @Link("xyz")
    @Issue("EQ-8765")
    @TmsLink("123456")
    @Description("Negative Test: User is able to get appropriate error on using an invalid token.")
    @Test(description = "Negative Test: User is able to get appropriate error on using an invalid token.")
    public void validateInvalidTokenCreatePlaylist(){
        String name="Playlist_"+ Long.toString(Instant.now().getEpochSecond());
        //We have implemented Builder pattern with POJO classes now. It is like a method chaining where each method returns the object of the same class
        /*playListPayload.setName(name)
                .setDescription("Random description created through POJO")
                .setPublic(false);*/
        PlayList playListPayload=playListBuilder(name,"Random description created through POJO",false);

        Response response=PlayListAPI.post(playListPayload,"InvalidString");
        assertThat(response.statusCode(),equalTo(StatusCode.CODE_401.code));

        ErrorRoot errorRoot=setErrorObject(StatusCode.CODE_401);
        ErrorRoot errorRootResponse= response.as(ErrorRoot.class);
        assertErrorValues(errorRootResponse,errorRoot);
    }

    public PlayList playListBuilder(String name,String description,Boolean _public){
        return PlayList.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();
    }

    public ErrorRoot setErrorObject(StatusCode statusCode){
        ErrorRoot errorRoot=new ErrorRoot();
        Error error= Error.builder()
                .status(statusCode.code)
                .message(statusCode.message)
                .build();
        errorRoot.setError(error);
        return errorRoot;
    }

    @Step("Asserting playlist values")
    public void assertPlayListValues(PlayList playListResponse){
        assertThat(playListResponse.getName(),equalTo(playListPayload.getName()));
        assertThat(playListResponse.getDescription(),equalTo(playListPayload.getDescription()));
    }
    @Step("Asserting error values")
    public void assertErrorValues(ErrorRoot errorRootResponse,ErrorRoot errorRootExpected){
        assertThat(errorRootResponse.getError().getMessage(),equalTo(errorRootExpected.getError().getMessage()));
        assertThat(errorRootResponse.getError().getStatus(),equalTo(errorRootExpected.getError().getStatus()));
    }

}
