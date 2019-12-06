package com.csce4623.carline.adapters;

        import androidx.recyclerview.widget.RecyclerView;

        import android.graphics.Color;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
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

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.Student_Name);
            mRoomView = (TextView) view.findViewById(R.id.Classroom_Number);
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
}

