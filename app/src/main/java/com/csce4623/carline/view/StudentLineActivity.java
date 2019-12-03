package com.csce4623.carline.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csce4623.carline.R;
import com.csce4623.carline.adapters.StudentLineAdapter;
import com.csce4623.carline.model.LineStudent;
import com.csce4623.carline.model.Student;
import com.csce4623.carline.network.ApiRequests;
import com.csce4623.carline.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentLineActivity extends AppCompatActivity implements StudentLineView {

    private List<Student> students;
    private List<LineStudent> lineStudents;
    private RecyclerView studentRecycler;
    private RecyclerView.Adapter srAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentline);
        studentRecycler = (RecyclerView) findViewById(R.id.StudentLineRecycler);

        layoutManager = new LinearLayoutManager(this);
        studentRecycler.setLayoutManager(layoutManager);

        srAdapter = new StudentLineAdapter(lineStudents);
        studentRecycler.setAdapter(srAdapter);


        ApiRequests requests = RetrofitClientInstance.getRetrofitInstance().create(ApiRequests.class);
        //  get list of all students
        Call<List<Student>> call = requests.getAllStudents();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                students = response.body();
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(StudentLineActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        //  get list of all students in line
        Call<List<LineStudent>> call2 = requests.getAllLineStudents();
        call2.enqueue(new Callback<List<LineStudent>>() {
            @Override
            public void onResponse(Call<List<LineStudent>> call, Response<List<LineStudent>> response) {
                lineStudents = response.body();
            }

            @Override
            public void onFailure(Call<List<LineStudent>> call, Throwable t) {
                Toast.makeText(StudentLineActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
        //need to update list of students, call refresh
    }

    @Override
    public void refresh() {

    }

    @Override
    public void moveUpOne(LineStudent student) {

    }

    @Override
    public void moveDownOne(LineStudent student) {

    }

    @Override
    public void changePosition(LineStudent student, int pos) {

    }
}
