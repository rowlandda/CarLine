package com.csce4623.carline.network;

import com.csce4623.carline.model.LineStudent;
import com.csce4623.carline.model.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequests {

    //get all student records
    @GET("all")
    Call<List<Student>> getAllStudents();

    //get all students in line with their position in line
    @GET("line")
    Call<List<LineStudent>> getAllLineStudents();

    //add a car/student to the line
    @POST("line/{studentId}/{position}")
    Call<LineStudent> addCarToLine(@Path("studentId") int studentId,
                                   @Path("position") int position);

    //remove a student from the line
    @DELETE("line/{studentId}")
    Call<LineStudent> deleteCarFromLine(@Path("studentId") int studentId);
}
