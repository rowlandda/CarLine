package com.csce4623.carline.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.csce4623.carline.R;
import com.csce4623.carline.model.LineStudent;
import com.csce4623.carline.network.ApiRequests;
import com.csce4623.carline.network.RetrofitClientInstance;
import com.csce4623.carline.view.CarFragment.OnListFragmentInteractionListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCarRecyclerViewAdapter extends RecyclerView.Adapter<MyCarRecyclerViewAdapter.ViewHolder> {

    private final List<LineStudent> mValues;
    private final OnListFragmentInteractionListener mListener;
    public DetailsAdapterListener onClickListener;
    private LineStudent mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;
    protected RecyclerView mRecyclerView;


    public MyCarRecyclerViewAdapter(List<LineStudent> students,
                                    OnListFragmentInteractionListener listener,
                                    DetailsAdapterListener detailsListener) {
        mValues = students;
        mListener = listener;
        this.onClickListener = detailsListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mStudent = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mLicence.setText(mValues.get(position).getCars()[0]);

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
        public final TextView mIdView;
        public final TextView mLicence;
        public LineStudent mStudent;
        @BindView(R.id.up_arrow)
        Button mvUpBtn;
        @BindView(R.id.down_arrow)
        Button mvDownBtn;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.student_id);
            mLicence = (TextView) view.findViewById(R.id.plate_number);

            mvUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.moveUpClick(v, getAdapterPosition());
                }
            });
            mvDownBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.moveDownClick(v, getAdapterPosition());
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLicence.getText() + "'";
        }
    }

    public interface DetailsAdapterListener {

        void moveUpClick(View v, int position);

        void moveDownClick(View v, int position);
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
                notifyItemRemoved(position);
            }
            @Override
            public void onFailure(Call<LineStudent> call, Throwable t) {
                System.out.println("error");
            }
        });
    }
}
