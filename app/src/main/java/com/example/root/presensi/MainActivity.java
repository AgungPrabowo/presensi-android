package com.example.root.presensi;

import android.Manifest;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.root.presensi.model.ModelCheckAbsen;
import com.example.root.presensi.model.ModelEmployee;
import com.example.root.presensi.model.ModelLocation;
import com.example.root.presensi.util.BaseApiService;
import com.example.root.presensi.util.UtilsApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {

    private FusedLocationProviderClient client;

    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager spManager;
    Button button;
    ImageView Jempol;
    TextView status;
    String currentDate;
    Location loc1;
    ProgressDialog loading;
    List<ModelLocation> dataLocation;
    List<ModelEmployee> dataEmployee;
    List<ModelCheckAbsen> dataCheckAbsen;
    String cekAbsen;
    List<String> latitude = new ArrayList<String>();
    List<String> longitude = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        this.Jempol = findViewById(R.id.jempol);
        status = findViewById(R.id.status_absen);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package api helper

        spManager = new SharedPrefManager(getApplicationContext());

//        get current date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        currentDate = formatter.format(date);
        System.out.println(formatter.format(date));

        getCheckAttendance();
        getLocation();
        getEmployee();

//        reset button
        loading = ProgressDialog.show(this, null, "Mohon Tunggu...",
                true, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.dismiss();
                System.out.println("absen id"+ spManager.getSpAbsenId());
//                System.out.println(dataEmployee.get(0).getEmployee_firstName());
                Calendar rightNow = Calendar.getInstance();
                System.out.println(rightNow.get(Calendar.HOUR_OF_DAY));
                if (rightNow.get(Calendar.HOUR_OF_DAY) == 0) {
                    MainActivity.this.spManager.resetSPButtonStatus();
                }

                System.out.println(cekAbsen);
                if (cekAbsen.equals("0")) {
                    MainActivity.this.status.setText("ABSEN MASUK");
                } else if (cekAbsen.equals("1")) {
                    MainActivity.this.status.setText("ABSEN PULANG");
                } else if (cekAbsen.equals("2")) {
                    MainActivity.this.status.setText("ABSEN LENGKAP");
                }
            }
        }, 2000);

//        if (MainActivity.this.spManager.getSPButtonStatus() == 1) {
//            MainActivity.this.status.setText("ABSEN MASUK");
//        } else if (MainActivity.this.spManager.getSPButtonStatus() == 2) {
//            MainActivity.this.status.setText("ABSEN PULANG");
//        } else if (MainActivity.this.spManager.getSPButtonStatus() == 3) {
//            MainActivity.this.status.setText("ABSEN LENGKAP");
//        }

        this.Jempol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        loc1 = location;
                        if(cekAbsen.equals("0")) {
                            loading = ProgressDialog.show(mContext, null,
                                    "Mohon Tunggu...", true, false);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loading.dismiss();
                                    requestAttendance(MainActivity.this.loc1);
                                }
                            },1000);
                        } else if(cekAbsen.equals("1")) {
                            Builder alertDialog = new Builder(MainActivity.this);
                            alertDialog.setMessage("Anda yakin untuk absen pulang?");
                            alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestAttendance(MainActivity.this.loc1);
                                }
                            });
                            alertDialog.show();
                        }
//                        if (location != null) {
//
//                            Log.d("TAG", String.valueOf(location));
//                            Log.d("RESULT", location.getLatitude()+" "+location.getLongitude());
//                            getLatitude = location.getLatitude();
//                            getLongitude = location.getLongitude();
//                            TextView textview = findViewById(R.id.location);
//                            textview.setText(location.toString()+MainActivity.this.spManager.getSPButtonStatus());
//                        }

                    }
                });
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        final TextView nav_user = (TextView)hView.findViewById(R.id.nama);
        nav_user.setText("Presensi Karyawan");
    }

    private void getLocation() {
        mApiService.getLocation().enqueue(new Callback<List<ModelLocation>>() {
            @Override
            public void onResponse(Call<List<ModelLocation>> call, Response<List<ModelLocation>> response) {
                dataLocation = new ArrayList<ModelLocation>();
                List<ModelLocation> loc = response.body();

                for (int i=0;i < loc.size();i++) {
                    dataLocation.add(new ModelLocation(loc.get(i).getId(), loc.get(i).getLocation_name(),
                            loc.get(i).getLocation_latitude(), loc.get(i).getLocation_longitude(),
                            loc.get(i).getLocation_distance()));
                    latitude.add(loc.get(i).getLocation_latitude());
                    longitude.add(loc.get(i).getLocation_longitude());
                }
            }

            @Override
            public void onFailure(Call<List<ModelLocation>> call, Throwable t) {

            }
        });
    }

    private void getEmployee() {
        mApiService.getEmployee(spManager.getSpNik()).enqueue(new Callback<List<ModelEmployee>>() {
            @Override
            public void onResponse(Call<List<ModelEmployee>> call, Response<List<ModelEmployee>> response) {
                dataEmployee = new ArrayList<ModelEmployee>();
                List<ModelEmployee> emp = response.body();

                dataEmployee.add(new ModelEmployee(emp.get(0).getId(), emp.get(0).getNik(), emp.get(0).getLocation_id(), emp.get(0).getRole_id(),
                        emp.get(0).getEmployee_ktp(), emp.get(0).getEmployee_firstName(), emp.get(0).getEmployee_lastName(), emp.get(0).getEmployee_position(),
                        emp.get(0).getEmployee_IMEI(), emp.get(0).getEmployee_phone(), emp.get(0).getEmployee_gender(), emp.get(0).getEmployee_birthDate(),
                        emp.get(0).getEmployee_address(), emp.get(0).getEmployee_city(), emp.get(0).getEmployee_region(), emp.get(0).getEmployee_religion(),
                        emp.get(0).getCreated_at(), emp.get(0).getUpdated_at()));
                spManager.saveSPString(SharedPrefManager.SP_KARYAWAN_ID, emp.get(0).getId());
            }

            @Override
            public void onFailure(Call<List<ModelEmployee>> call, Throwable t) {

            }
        });
    }

    private void getCheckAttendance() {
        mApiService.getCheckAttendance(spManager.getSPKaryawanID(), currentDate).enqueue(new Callback<List<ModelCheckAbsen>>() {
            @Override
            public void onResponse(Call<List<ModelCheckAbsen>> call, Response<List<ModelCheckAbsen>> response) {
                dataCheckAbsen = new ArrayList<ModelCheckAbsen>();
                List<ModelCheckAbsen> check = response.body();
                if(check.toString().equals("[]")){
                    cekAbsen = "0";
                } else if(check.toString().equals("null")) {
                    cekAbsen = "0";
                } else {
                    cekAbsen = check.get(0).getAttendance_check();
                    spManager.saveSPString(SharedPrefManager.SP_ABSEN_ID, check.get(0).getId());
                    dataCheckAbsen.add(new ModelCheckAbsen(check.get(0).getId(), check.get(0).getEmployee_id(), check.get(0).getAttendance_check(),
                            check.get(0).getAttendance_info(), check.get(0).getAttendance_latitude(), check.get(0).getAttendance_longitude(),
                            check.get(0).getCreated_at(), check.get(0).getUpdated_at()));
                }
            }

            @Override
            public void onFailure(Call<List<ModelCheckAbsen>> call, Throwable t) {

            }
        });
    }

    private void requestAttendance(Location location) {
//        mContext = this;

        if(cekAbsen.equals("0")) {
            mApiService.attendanceInRequest(dataEmployee.get(0).getLocation_id(), spManager.getSPKaryawanID(),
                    Double.valueOf(location.getLongitude()), Double.valueOf(location.getLatitude()), 1)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            JSONObject jsonRESULTS;
                            if (response.isSuccessful()) {
                                try {
                                    jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("success").equals("true")) {
                                        String msg = jsonRESULTS.getString("message");
                                        String loc = jsonRESULTS.getJSONObject("data").getString("attendance_info");
                                        String absenId = jsonRESULTS.getJSONObject("data").getString("id");
                                        spManager.saveSPString(SharedPrefManager.SP_ABSEN_ID, absenId);
                                        spManager.saveSPInteger(spManager.SP_BUTTON_STATUS, 2);
                                        MainActivity.this.status.setText("ABSEN PULANG");
                                        Toast.makeText(getApplicationContext(), msg+loc, Toast.LENGTH_LONG).show();
                                    } else {
                                        spManager.saveSPInteger(spManager.SP_BUTTON_STATUS, 1);
                                        Toast.makeText(getApplicationContext(), jsonRESULTS.getString("message"),
                                                Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(mContext, "Gagal   ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.toString());
                        }
                    });
        } else if(cekAbsen.equals("1")) {
                mApiService.attendanceOutRequest(dataEmployee.get(0).getLocation_id(), spManager.getSPKaryawanID(),
                        Double.valueOf(location.getLongitude()), Double.valueOf(location.getLatitude()), 2, spManager.getSpAbsenId())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                JSONObject jsonRESULTS;
                                if (response.isSuccessful()) {
                                    try {
                                        jsonRESULTS = new JSONObject(response.body().string());
                                        if (jsonRESULTS.getString("success").equals("true")) {
                                            String msg = jsonRESULTS.getString("message");
                                            String loc = jsonRESULTS.getJSONObject("data").getString("attendance_info");
                                            spManager.saveSPInteger(spManager.SP_BUTTON_STATUS, 3);
                                            MainActivity.this.status.setText("ABSEN LENGKAP");
                                            Toast.makeText(getApplicationContext(), msg+loc, Toast.LENGTH_LONG).show();
                                        } else {
                                            spManager.saveSPInteger(spManager.SP_BUTTON_STATUS, 2);
                                            Toast.makeText(getApplicationContext(), jsonRESULTS.getString("message"),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(mContext, "Gagal   ", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
        }
    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        ButterKnife.bind(this);
        spManager = new SharedPrefManager(this);

        if (id == R.id.view_map) {
            String[] arrayLatitude = latitude.toArray(new String[latitude.size()]);
            String[] arrayLongitude = longitude.toArray(new String[longitude.size()]);
            Intent intent = new Intent(mContext, MapActivity.class);
            intent.putExtra("latitude", arrayLatitude);
            intent.putExtra("longitude", arrayLongitude);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, DaftarAbsen.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        } else if (id == R.id.logout) {
            spManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            startActivity(new Intent(MainActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
