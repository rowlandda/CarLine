package com.csce4623.carline.network;

import com.csce4623.carline.model.LineStudent;
import com.csce4623.carline.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequests {

    @GET("all")
    Call<List<Student>> getAllStudents();

    @GET("line")
    Call<List<LineStudent>> getAllLineStudents();
}
