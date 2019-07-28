package com.example.root.presensi.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.presensi.R;
import com.example.root.presensi.model.Item;
import com.example.root.presensi.model.ModelReportAttendance;

import java.util.ArrayList;
import java.util.List;

public class ItemArrayAdapter extends RecyclerView.Adapter<ItemArrayAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
//    private List<Item> itemList;
    private List<ModelReportAttendance> reportAttendances;
    // Constructor of the class
    public ItemArrayAdapter(int layoutId, List<ModelReportAttendance> reportAttendances) {
        listItemLayout = layoutId;
        this.reportAttendances = reportAttendances;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return reportAttendances == null ? 0 : reportAttendances.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
//        TextView item = holder.item;
//        item.setText(reportAttendances.get(listPosition).getCreated_at());
//        item.setText(reportAttendances.get(listPosition).getUpdated_at());
        ModelReportAttendance mra = reportAttendances.get(listPosition);
        String outTime = mra.getUpdated_at().substring(11, 16);
        String inTime = mra.getCreated_at().substring(11, 16);
        String outTime1;
        if (inTime.equals(outTime)) {
            outTime1 = "null";
        } else {
            outTime1 = outTime;
        }
        holder.tgl.setText(mra.getCreated_at().substring(0, 11));
        holder.time.setText(inTime+" - "+outTime1);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tgl, time;
        public ViewHolder(View itemView) {
            super(itemView);
            tgl = itemView.findViewById(R.id.tgl);
            time = itemView.findViewById(R.id.time);
        }
    }
}
