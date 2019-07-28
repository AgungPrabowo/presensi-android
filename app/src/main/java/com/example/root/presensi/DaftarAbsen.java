package com.example.root.presensi;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.root.presensi.adapter.ItemArrayAdapter;
import com.example.root.presensi.model.Item;
import com.example.root.presensi.model.ModelReportAttendance;
import com.example.root.presensi.util.BaseApiService;
import com.example.root.presensi.util.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarAbsen extends AppCompatActivity {
    RecyclerView recyclerView;
    BaseApiService mApiService;
    SharedPrefManager spManager;
    List<ModelReportAttendance> modelReportAttendance;
    Context mContext;
    ProgressDialog loading;
    ItemArrayAdapter itemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_absen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper
        spManager = new SharedPrefManager(getApplicationContext());

        loading = ProgressDialog.show(this, null, "Mohon Tunggu...",
                true, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.dismiss();
                getReportAttendance();
            }
        }, 1000);


//        // Initializing list view with the custom adapter
//        ArrayList <Item> itemList = new ArrayList<Item>();
//        // Populating list items
//        for(int i=0; i<100; i++) {
//            itemList.add(new Item("Item " + i));
//        }
//
//        ItemArrayAdapter itemArrayAdapter = new ItemArrayAdapter(R.layout.item_report_absen, itemList);
//        recyclerView = findViewById(R.id.item_list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(itemArrayAdapter);
    }

    private void getReportAttendance() {
//        String employee_id = spManager.getSPKaryawanID();
        mApiService.getReportAttendance(spManager.getSPKaryawanID()).enqueue(new Callback<List<ModelReportAttendance>>() {
            @Override
            public void onResponse(Call<List<ModelReportAttendance>> call, Response<List<ModelReportAttendance>> response) {
                recyclerView = findViewById(R.id.item_list);
                modelReportAttendance = new ArrayList<ModelReportAttendance>();
                List<ModelReportAttendance> reportAttendances = response.body();
//                System.out.println(reportAttendances.get(0).getCreated_at()+" "+reportAttendances.size());
                int count = reportAttendances.size();

                for (int i=0;i < count;i++) {
                    if(i == 0) {
                        modelReportAttendance.add(new ModelReportAttendance(reportAttendances.get(i).getId_report(),
                                reportAttendances.get(i).getEmployee_id(), reportAttendances.get(i).getAttendance_check(),
                                reportAttendances.get(i).getAttendance_info(), reportAttendances.get(i).getAttendance_latitude(),
                                reportAttendances.get(i).getAttendance_longitude(), reportAttendances.get(i).getCreated_at(),
                                reportAttendances.get(i).getUpdated_at()));
                    }
                    modelReportAttendance.add(new ModelReportAttendance(reportAttendances.get(i).getId_report(),
                            reportAttendances.get(i).getEmployee_id(), reportAttendances.get(i).getAttendance_check(),
                            reportAttendances.get(i).getAttendance_info(), reportAttendances.get(i).getAttendance_latitude(),
                            reportAttendances.get(i).getAttendance_longitude(), reportAttendances.get(i).getCreated_at(),
                            reportAttendances.get(i).getUpdated_at()));
                }

                itemArrayAdapter = new ItemArrayAdapter(R.layout.item_report_absen, modelReportAttendance);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(itemArrayAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelReportAttendance>> call, Throwable t) {

            }
        });
    }
}
