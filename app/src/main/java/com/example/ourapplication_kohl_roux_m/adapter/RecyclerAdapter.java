package com.example.ourapplication_kohl_roux_m.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourapplication_kohl_roux_m.R;
import com.example.ourapplication_kohl_roux_m.dbClass.entity.CarEntity;
import com.example.ourapplication_kohl_roux_m.dbClass.entity.TrajetEntity;
import com.example.ourapplication_kohl_roux_m.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;


public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderText> {

    private final RecyclerViewItemClickListener mListener;
    private List<T> mData;


    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolderText onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolderText viewHolder = new ViewHolderText(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderText holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(String.class))
            holder.mTextView.setText((String) item);
        if (item.getClass().equals(CarEntity.class)) {
            holder.mTextView.setText(((CarEntity) item).getNickName());
        }
        if (item.getClass().equals(TrajetEntity.class))
            holder.mTextView.setText(((TrajetEntity) item).getName() + " "
                    + ((TrajetEntity) item).getDate() + " " + ((TrajetEntity) item).getKmTot());
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
                    if (mData instanceof TrajetEntity) {
                        return mData.get(oldItemPosition).equals(
                                data.get(newItemPosition));
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof CarEntity) {
                        CarEntity newCarEntity = (CarEntity) data.get(newItemPosition);
                        CarEntity oldCarEntity = (CarEntity) mData.get(newItemPosition);
                        return Objects.equals(newCarEntity.getUid(), (oldCarEntity.getUid()))
                                && Objects.equals(newCarEntity.getNickName(), oldCarEntity.getNickName());
                    }
                    if (mData instanceof TrajetEntity) {
                        TrajetEntity newClient = (TrajetEntity) data.get(newItemPosition);
                        TrajetEntity oldClient = (TrajetEntity) mData.get(newItemPosition);
                        return Objects.equals(newClient.getCarId(), oldClient.getCarId())
                                && Objects.equals(newClient.getName(), oldClient.getName())
                                && Objects.equals(newClient.getDate(), oldClient.getDate());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }

    static class ViewHolderText extends RecyclerView.ViewHolder {
        TextView mTextView;

        ViewHolderText(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }
}
