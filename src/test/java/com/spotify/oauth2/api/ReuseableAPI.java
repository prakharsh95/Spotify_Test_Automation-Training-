package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.PlayList;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

public class ReuseableAPI {

    public static Response postAccount(Map<String,String> formParams){
        return given()
                .baseUri(System.getProperty("Account_Base_URL"))
                //.baseUri("https://accounts.spotify.com")
                .formParams(formParams)
                .contentType(ContentType.URLENC)
                .log().all()
                .when()
                .post(RouteConstants.API+RouteConstants.TOKEN)
                .then()
                .log().all()
                .extract().response();

    }
    public static  Response post(Object pojoClassObject,String path, String token){
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(token)
                //.header("Authorization","Bearer "+token)
                .body(pojoClassObject)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpecification())
                .log().all()
                .extract().response();
    }

    public static Response get(String path, String token){
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(token)
                //.header("Authorization","Bearer "+token)
                .when()
                .get(path)
                .then()
                .spec(getResponseSpecification())
                .log().all()
                .extract()
                .response();
    }

    public static Response put(Object pojoClassObject,String path,String token){
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(token)
                //.header("Authorization","Bearer "+token)
                .body(pojoClassObject)
                .when()
                .put(path)
                .then()
                .log().all()
                .extract().response();

    }
}
