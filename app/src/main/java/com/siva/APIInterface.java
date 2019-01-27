package com.siva;



import com.google.gson.JsonObject;
import com.siva.Model.Inspection;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface APIInterface {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("/api/inspectionentry/fetch.php?temp=null")
    Call<Inspection> doGetDetails(@Query("id")String id);

    @POST("/api/inspectionentry/fetch.php")
    Call<JsonObject> postRawJSON(@Body JsonObject postObject);
}
