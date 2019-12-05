package com.csce4623.carline.view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csce4623.carline.R;
import com.csce4623.carline.adapters.DividerItemDecoration;
import com.csce4623.carline.adapters.MyCarRecyclerViewAdapter;
import com.csce4623.carline.adapters.SwipeToDeleteCallback;
import com.csce4623.carline.model.LineStudent;
import com.csce4623.carline.network.ApiRequests;
import com.csce4623.carline.network.RetrofitClientInstance;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<LineStudent> students;
    private RecyclerView recyclerView;
    private MyCarRecyclerViewAdapter adapter;
    private LineStudent mRecentlyDeletedItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CarFragment newInstance(int columnCount) {
        CarFragment fragment = new CarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);
        final FragmentActivity c = getActivity();
        recyclerView = view.findViewById(R.id.list);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ApiRequests requests = RetrofitClientInstance.getRetrofitInstance().create(ApiRequests.class);
            //  get list of all students in line
            Call<List<LineStudent>> call = requests.getAllLineStudents();
            RecyclerView finalRecyclerView = recyclerView;
            //asynchronous
            call.enqueue(new Callback<List<LineStudent>>() {
                @Override
                public void onResponse(Call<List<LineStudent>> call, Response<List<LineStudent>> response) {
                    students = response.body();
                    Collections.sort(students);
                    //add border to items
                    finalRecyclerView.addItemDecoration(
                            new DividerItemDecoration(getActivity(), R.drawable.dividers));
                    //add the students to the adapter
                    adapter = new MyCarRecyclerViewAdapter(students, mListener,
                        new MyCarRecyclerViewAdapter.DetailsAdapterListener() {
                            @Override
                            public void moveUpClick(View v, int position){
                                moveUpOne(students, position);// do something or navigate to detailed classes
                            }

                            @Override
                            public void moveDownClick(View v, int position) {
                                moveDownOne(students, position);// do something or navigate to running days
                            }
                        });
                    finalRecyclerView.setAdapter(adapter);
                    ItemTouchHelper itemTouchHelper = new
                            ItemTouchHelper(new SwipeToDeleteCallback(adapter));
                    itemTouchHelper.attachToRecyclerView(recyclerView);
                }

                @Override
                public void onFailure(Call<List<LineStudent>> call, Throwable t) {
                    System.out.println("error");
                }
            });

        }
        return view;
    }

    //move selected student up one in the line
    private void moveUpOne(List<LineStudent> students, int position){
        ApiRequests requests = RetrofitClientInstance
                .getRetrofitInstance()
                .create(ApiRequests.class);
        int indexFront = position -1;
        int indexSelected = position;
        int positionFront = students.get(indexFront).getPosition();
        int positionSelected = students.get(indexSelected).getPosition();
        int studentIdFront = Integer.parseInt(students.get(indexFront).getId());
        int studentIdSelected = Integer.parseInt(students.get(indexSelected).getId());
        Call<LineStudent> call1 = requests.addCarToLine(studentIdSelected, positionFront);
        Call<LineStudent> call2 = requests.addCarToLine(studentIdFront, positionSelected);
        call1.enqueue(new Callback<LineStudent>() {
            @Override
            public void onResponse(Call<LineStudent> call, Response<LineStudent> response) {
                refreshList();
            }

            @Override
            public void onFailure(Call<LineStudent> call, Throwable t) {
                System.out.println("error");
            }
        });
        call2.enqueue(new Callback<LineStudent>() {
            @Override
            public void onResponse(Call<LineStudent> call, Response<LineStudent> response) {
                refreshList();
            }

            @Override
            public void onFailure(Call<LineStudent> call, Throwable t) {
                System.out.println("error");
            }
        });
    }

    private void moveDownOne(List<LineStudent> students, int position) {
        ApiRequests requests = RetrofitClientInstance
                .getRetrofitInstance()
                .create(ApiRequests.class);
        int indexBack = position + 1;
        int indexSelected = position;
        int positionBack = students.get(indexBack).getPosition();
        int positionSelected = students.get(indexSelected).getPosition();
        int studentIdBack = Integer.parseInt(students.get(indexBack).getId());
        int studentIdSelected = Integer.parseInt(students.get(indexSelected).getId());
        Call<LineStudent> call1 = requests.addCarToLine(studentIdSelected, positionBack);
        Call<LineStudent> call2 = requests.addCarToLine(studentIdBack, positionSelected);
        call1.enqueue(new Callback<LineStudent>() {
            @Override
            public void onResponse(Call<LineStudent> call, Response<LineStudent> response) {
                refreshList();
            }

            @Override
            public void onFailure(Call<LineStudent> call, Throwable t) {
                System.out.println("error");
            }
        });
        call2.enqueue(new Callback<LineStudent>() {
            @Override
            public void onResponse(Call<LineStudent> call, Response<LineStudent> response) {
                refreshList();
            }

            @Override
            public void onFailure(Call<LineStudent> call, Throwable t) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(LineStudent item);
    }

    //updates the recyclerview with new items
    public void refreshList() {
        ApiRequests requests = RetrofitClientInstance.getRetrofitInstance().create(ApiRequests.class);
        //  get list of all students in line
        Call<List<LineStudent>> call = requests.getAllLineStudents();
        call.enqueue(new Callback<List<LineStudent>>() {
            @Override
            public void onResponse(Call<List<LineStudent>> call, Response<List<LineStudent>> response) {
                students.clear();
                students.addAll(response.body());
                Collections.sort(students);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<LineStudent>> call, Throwable t) {
                System.out.println("error");
            }
        });
    }



    public int getCount() {
        return adapter.getItemCount();
    }

    //get the position of the last person in line
    public int getHighestPosition() {
        return students.get(students.size()-1).getPosition();
    }
}
