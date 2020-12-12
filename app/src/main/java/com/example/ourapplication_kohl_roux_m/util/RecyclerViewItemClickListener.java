package com.example.ourapplication_kohl_roux_m.util;

import android.view.View;

public interface RecyclerViewItemClickListener {
    void onItemClick(View v, int position);

    void onItemLongClick(View v, int position);
}
