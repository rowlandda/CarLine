package com.csce4623.carline.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.csce4623.carline.R;
import com.csce4623.carline.adapters.DividerItemDecoration;
import com.csce4623.carline.adapters.MyCarRecyclerViewAdapter;
import com.csce4623.carline.model.LineStudent;
import com.csce4623.carline.model.Student;
import com.csce4623.carline.network.ApiRequests;
import com.csce4623.carline.network.RetrofitClientInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarLineActivity extends AppCompatActivity implements CarlineView, CarFragment.OnListFragmentInteractionListener, AddCarFragment.OnFragmentInteractionListener {

    private List<Student> students;
    private List<LineStudent> lineStudents;
    private ApiRequests request;
    private CarFragment carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carline);
        carList = (CarFragment) getSupportFragmentManager().findFragmentById(R.id.list);
        request = RetrofitClientInstance.getRetrofitInstance().create(ApiRequests.class);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.cars_icon:

                        break;
                    case R.id.students_icon:
                        Intent intent = new Intent(CarLineActivity.this, StudentLineActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void refreshList() {
        carList.refreshList();
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

    public void goToStudent(View view) {
        Intent intent = new Intent(this, StudentLineActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void addCarToLine(View view) {
        TextView editText = findViewById(R.id.student_id_enter);
        int studentId = Integer.parseInt(editText.getText().toString());
        int position = carList.getHighestPosition()+1;
        Call<LineStudent> call = request.addCarToLine(studentId, position);
        call.enqueue(new Callback<LineStudent>() {
            @Override
            public void onResponse(Call<LineStudent> call, Response<LineStudent> response) {
                LineStudent lineStudent= response.body();
                refreshList();
            }

            @Override
            public void onFailure(Call<LineStudent> call, Throwable t) {
                System.out.println("error");
            }
        });
    }
}
