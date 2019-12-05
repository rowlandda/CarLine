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

public class StudentLineActivity extends AppCompatActivity implements StudentLineView, StudentListFragment.OnListFragmentInteractionListener {

    private RecyclerView.LayoutManager layoutManager;
    private StudentListFragment studentList;
    private ApiRequests request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentline);
        studentList = (StudentListFragment) getSupportFragmentManager().findFragmentById(R.id.student_list);
        request = RetrofitClientInstance.getRetrofitInstance().create(ApiRequests.class);

        layoutManager = new LinearLayoutManager(this);

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

    @Override
    public void onListFragmentInteraction(LineStudent item) {

    }
}
