package com.csce4623.carline.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csce4623.carline.R;
import com.csce4623.carline.model.LineStudent;
import com.csce4623.carline.network.ApiRequests;
import com.csce4623.carline.network.RetrofitClientInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentLineActivity extends AppCompatActivity implements StudentLineView, StudentListFragment.OnListFragmentInteractionListener {

    private RecyclerView.LayoutManager layoutManager;
    private StudentListFragment studentList;
    private ApiRequests request;
    public Button goToCar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentline);
        studentList = (StudentListFragment) getSupportFragmentManager().findFragmentById(R.id.student_list);
        request = RetrofitClientInstance.getRetrofitInstance().create(ApiRequests.class);
        layoutManager = new LinearLayoutManager(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.cars_icon:
                        Intent intent = new Intent(StudentLineActivity.this, CarLineActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.students_icon:
                        break;
                }
                return false;
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

    public void goToCar(View view) {
        Intent intent = new Intent(this, CarLineActivity.class);
        startActivity(intent);
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
