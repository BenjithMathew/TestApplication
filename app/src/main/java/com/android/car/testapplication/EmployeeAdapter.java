package com.android.car.testapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.car.testapplication.Models.EmpSub;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private static final String TAG = EmployeeAdapter.class.getSimpleName();
    Context context;
    List<EmpSub> empList;

    EmployeeAdapter(Context context, List<EmpSub> empList) {
        this.context = context;
        this.empList = empList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context).load(empList.get(holder.getAdapterPosition()).getImage()).into(new DrawableImageViewTarget(holder.ivProfile) {
            @Override
            public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                super.onResourceReady(resource, transition);
                Log.d(TAG, "onResourceReady: ");
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                Log.e(TAG, "onLoadFailed: ");
            }
        });

        holder.tvName.setText(empList.get(holder.getAdapterPosition()).getName());
        holder.tvCompanyName.setText(empList.get(holder.getAdapterPosition()).getCmp_name());

    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvName;
        TextView tvCompanyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
        }
    }
}
