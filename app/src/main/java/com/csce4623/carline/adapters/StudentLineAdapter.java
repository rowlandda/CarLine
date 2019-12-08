package com.csce4623.carline.adapters;

        import androidx.recyclerview.widget.RecyclerView;

        import android.graphics.Color;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CheckBox;
        import android.widget.TextView;

        import com.csce4623.carline.R;
        import com.csce4623.carline.model.LineStudent;
        import com.csce4623.carline.network.ApiRequests;
        import com.csce4623.carline.network.RetrofitClientInstance;
        import com.csce4623.carline.view.StudentListFragment;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class StudentLineAdapter extends RecyclerView.Adapter<StudentLineAdapter.ViewHolder> {

    private final List<LineStudent> mValues;
    private final StudentListFragment.OnListFragmentInteractionListener mListener;
    private LineStudent mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;
    protected RecyclerView mRecyclerView;


    public StudentLineAdapter(List<LineStudent> students,
                                    StudentListFragment.OnListFragmentInteractionListener listener)
    {
        mValues = students;
        mListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.mView.setBackgroundColor(Color.parseColor("#95D6CB"));
        }else {
            holder.mView.setBackgroundColor(Color.WHITE);
        }
        holder.mStudent = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mRoomView.setText(Integer.toString(mValues.get(position).getRoom()));
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mTeacherView.setText(mValues.get(position).getTeacher());
        holder.mGradeView.setText(Integer.toString(mValues.get(position).getGrade()));
        if (mValues.get(position).getWaiting() == true) {
            holder.mCheckBox.setChecked(true);
        } else if (mValues.get(position).getWaiting() == false) {
            holder.mCheckBox.setChecked(false);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mStudent);
                }
            }
        });

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sends apirequest to toggle waiting status
                toggleWaiting(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public LineStudent mStudent;
        public final TextView mNameView;
        public final TextView mRoomView;
        public final TextView mIdView;
        public final TextView mTeacherView;
        public final TextView mGradeView;
        public final CheckBox mCheckBox;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.Student_Name);
            mRoomView = (TextView) view.findViewById(R.id.Classroom_Number);
            mIdView = (TextView) view.findViewById(R.id.car_id);
            mTeacherView = (TextView) view.findViewById(R.id.Teacher_Name);
            mGradeView = (TextView) view.findViewById(R.id.Grade);
            mCheckBox = (CheckBox) view.findViewById(R.id.checkBox);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mStudent.getName() + "'";
        }
    }

    public void deleteItem(int position) {
        mRecentlyDeletedItem = mValues.get(position);
        mRecentlyDeletedItemPosition = position;
        ApiRequests request = RetrofitClientInstance.getRetrofitInstance().create(ApiRequests.class);
        Call<LineStudent> call =
                request.deleteCarFromLine(Integer.parseInt(mValues.get(position).getId()));
        call.enqueue(new Callback<LineStudent>() {
            @Override
            public void onResponse(Call<LineStudent> call, Response<LineStudent> response) {
                LineStudent student = response.body();
                mValues.remove(position);
                notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<LineStudent> call, Throwable t) {
                System.out.println("error");
            }
        });
    }

    public void toggleWaiting(int position) {
        ApiRequests requests = RetrofitClientInstance.getRetrofitInstance().create(ApiRequests.class);
        Call<LineStudent> call = requests.changeWaitingStatus(Integer.parseInt(mValues.get(position).getId()));
        call.enqueue(new Callback<LineStudent>() {
            @Override
            public void onResponse(Call<LineStudent> call, Response<LineStudent> response) {
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<LineStudent> call, Throwable t) {
                System.out.println("error");
            }
        });
    }
}

