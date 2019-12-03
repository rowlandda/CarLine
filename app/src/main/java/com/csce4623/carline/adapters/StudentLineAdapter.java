package com.csce4623.carline.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csce4623.carline.R;
import com.csce4623.carline.model.LineStudent;

import java.util.List;

public class StudentLineAdapter extends RecyclerView.Adapter<StudentLineAdapter.MyViewHolder> {
    private List<LineStudent> studentDataset;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.studentline_fragment, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    public StudentLineAdapter(List<LineStudent> studentSet) {

        studentDataset = studentSet;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sStudent = studentDataset.get(position);
        holder.sNameView.setText(studentDataset.get(position).getName());
        holder.sRoomView.setText(studentDataset.get(position).getRoom());
    }

    @Override
    public int getItemCount() {
        return studentDataset.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public LineStudent sStudent;
        public TextView textView;
        public TextView sNameView;
        public TextView sRoomView;

        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
            sNameView = v.findViewById(R.id.Student_Name);
            sRoomView = v.findViewById(R.id.Classroom_Number);
        }

        public TextView getTextView() {
            return textView;
        }

    }
}

