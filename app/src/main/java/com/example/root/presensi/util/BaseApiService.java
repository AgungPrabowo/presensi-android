package com.example.root.presensi.util;

import com.example.root.presensi.model.Attendance;
import com.example.root.presensi.model.ModelCheckAbsen;
import com.example.root.presensi.model.ModelEmployee;
import com.example.root.presensi.model.ModelLocation;
import com.example.root.presensi.model.ModelReportAttendance;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {

    // fungsi untuk memanggil api
    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("attendance/in")
    Call<ResponseBody> attendanceInRequest(@Field("location_id") String location_id,
                                         @Field("employee_id") String employee_id,
                                         @Field("latitude") double latitude,
                                         @Field("longitude") double longitude,
                                         @Field("check") int check);
    @FormUrlEncoded
    @POST("attendance/out/{id}")
    Call<ResponseBody> attendanceOutRequest(@Field("location_id") String location_id,
                                            @Field("employee_id") String employee_id,
                                            @Field("latitude") double latitude,
                                            @Field("longitude") double longitude,
                                            @Field("check") int check,
                                            @Path("id") String id);

    @GET("location")
    Call<List<ModelLocation>> getLocation();

    @GET("attendance/{id}")
    Call<List<ModelReportAttendance>> getReportAttendance(@Path("id") String id);

    @GET("attendance/check/{emp_id}/{tgl}")
    Call<List<ModelCheckAbsen>> getCheckAttendance(@Path("emp_id") String emp_id,
                                                   @Path("tgl") String tgl);

    @GET("employee/nik/{nik}")
    Call<List<ModelEmployee>> getEmployee(@Path("nik") String nik);
}
