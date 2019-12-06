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

import com.csce4623.carline.R;
import com.csce4623.carline.adapters.DividerItemDecoration;
import com.csce4623.carline.adapters.StudentLineAdapter;
import com.csce4623.carline.adapters.SwipeToDeleteCallBackStudents;
import com.csce4623.carline.model.LineStudent;
import com.csce4623.carline.network.ApiRequests;
import com.csce4623.carline.network.RetrofitClientInstance;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<LineStudent> students;
    private RecyclerView recyclerView;
    private StudentLineAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        final FragmentActivity c = getActivity();
        recyclerView = view.findViewById(R.id.student_list);
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
                    adapter = new StudentLineAdapter(students, mListener);
                    finalRecyclerView.setAdapter(adapter);
                    ItemTouchHelper itemTouchHelper = new
                            ItemTouchHelper(new SwipeToDeleteCallBackStudents(adapter));
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
}
