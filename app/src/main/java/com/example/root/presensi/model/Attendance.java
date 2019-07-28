package com.example.root.presensi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendance {

    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("attendance_check")
    @Expose
    private String attendanceCheck;
    @SerializedName("attendance_info")
    @Expose
    private String attendanceInfo;
    @SerializedName("attendance_latitude")
    @Expose
    private Double attendanceLatitude;
    @SerializedName("attendance_longitude")
    @Expose
    private Double attendanceLongitude;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAttendanceCheck() {
        return attendanceCheck;
    }

    public void setAttendanceCheck(String attendanceCheck) {
        this.attendanceCheck = attendanceCheck;
    }

    public String getAttendanceInfo() {
        return attendanceInfo;
    }

    public void setAttendanceInfo(String attendanceInfo) {
        this.attendanceInfo = attendanceInfo;
    }

    public Double getAttendanceLatitude() {
        return attendanceLatitude;
    }

    public void setAttendanceLatitude(Double attendanceLatitude) {
        this.attendanceLatitude = attendanceLatitude;
    }

    public Double getAttendanceLongitude() {
        return attendanceLongitude;
    }

    public void setAttendanceLongitude(Double attendanceLongitude) {
        this.attendanceLongitude = attendanceLongitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "employee_id='" + employeeId + '\'' +
                ", attendance_check='" + attendanceCheck + '\'' +
                ", attendance_info=" + attendanceInfo +
                ", attendance_latitude=" + attendanceLatitude +
                ", attendance_longitude=" + attendanceLongitude +
                '}';
    }

}