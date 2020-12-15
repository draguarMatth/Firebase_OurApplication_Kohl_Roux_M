package com.example.ourapplication_kohl_roux_m.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.dbClass.entities.CarEntity;
import com.example.ourapplication_kohl_roux_m.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapterWithPicture<T> extends RecyclerView.Adapter<RecyclerAdapterWithPicture.ViewHolder> {

    private final RecyclerViewItemClickListener mListener;
    private List<T> mData;

    public RecyclerAdapterWithPicture(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_with_picture, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = mData.get(position);

        if (!item.getClass().equals(CarEntity.class)) {
            Log.i(getClass().toString(), "Data no corresponding");
        }
        if (((CarEntity) item).getNickname().isEmpty())
            holder.mTextView.setText(((CarEntity) item).getModel());
        else
            holder.mTextView.setText(((CarEntity) item).getNickname());

        if (((CarEntity) item).getPicture() == 0)
            holder.imageView.setImageResource(R.drawable.simplex_car);
        else
            holder.imageView.setImageResource(((CarEntity) item).getPicture());

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof CarEntity) {
                        return mData.get(oldItemPosition).equals(data.get(newItemPosition));
                    }

                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof CarEntity) {
                        CarEntity newCarEntity = (CarEntity) data.get(newItemPosition);
                        CarEntity oldCarEntity = (CarEntity) mData.get(newItemPosition);
                        return Objects.equals(newCarEntity.getUid(), (oldCarEntity.getUid()))
                                && Objects.equals(newCarEntity.getNickname(), oldCarEntity.getNickname());
                    }

                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pictureView);
            mTextView = itemView.findViewById(R.id.txtPictureView);
        }
    }
}
