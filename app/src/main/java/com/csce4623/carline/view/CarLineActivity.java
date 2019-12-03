package com.csce4623.carline.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.csce4623.carline.R;
import com.csce4623.carline.adapters.MyCarRecyclerViewAdapter;
import com.csce4623.carline.model.LineStudent;
import com.csce4623.carline.model.Student;
import com.csce4623.carline.network.ApiRequests;
import com.csce4623.carline.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarLineActivity extends AppCompatActivity implements CarlineView, CarFragment.OnListFragmentInteractionListener {

    private List<Student> students;
    private List<LineStudent> lineStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carline);

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
                Toast.makeText(CarLineActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(CarLineActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

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

    @Override
    public void onListFragmentInteraction(LineStudent item) {

    }
}
