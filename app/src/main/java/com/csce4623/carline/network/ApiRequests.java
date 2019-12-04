package com.csce4623.carline.network;

import com.csce4623.carline.model.LineStudent;
import com.csce4623.carline.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequests {

    @GET("all")
    Call<List<Student>> getAllStudents();

    @GET("line")
    Call<List<LineStudent>> getAllLineStudents();

    @POST("line/{studentId}/{position}")
    Call<LineStudent> addCarToLine(@Path("studentId") int studentId,
                                   @Path("position") int position);
}
