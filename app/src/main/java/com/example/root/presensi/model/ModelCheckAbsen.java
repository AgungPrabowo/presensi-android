package com.example.root.presensi.model;

public class ModelCheckAbsen {
    String id, employee_id, attendance_check, attendance_info, attendance_latitude, attendance_longitude,
            created_at, updated_at;

    public ModelCheckAbsen(String id, String employee_id, String attendance_check, String attendance_info,
                           String attendance_latitude, String attendance_longitude, String created_at, String updated_at) {
        this.id = id;
        this.employee_id = employee_id;
        this.attendance_check = attendance_check;
        this.attendance_info = attendance_info;
        this.attendance_latitude = attendance_latitude;
        this.attendance_longitude = attendance_longitude;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getAttendance_check() {
        return attendance_check;
    }

    public void setAttendance_check(String attendance_check) {
        this.attendance_check = attendance_check;
    }

    public String getAttendance_info() {
        return attendance_info;
    }

    public void setAttendance_info(String attendance_info) {
        this.attendance_info = attendance_info;
    }

    public String getAttendance_latitude() {
        return attendance_latitude;
    }

    public void setAttendance_latitude(String attendance_latitude) {
        this.attendance_latitude = attendance_latitude;
    }

    public String getAttendance_longitude() {
        return attendance_longitude;
    }

    public void setAttendance_longitude(String attendance_longitude) {
        this.attendance_longitude = attendance_longitude;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
