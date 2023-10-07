package com.example.projecte0salmar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface QuestionService {
    @POST("getPreguntes?num=10") //Faig la petició amb POST per temes de seguretat (fer get es el mateix pero posant GET)
    Call<Questions> getPreguntes();
}
