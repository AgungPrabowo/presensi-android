package com.example.root.presensi.model;

public class ModelEmployee {
    String id, nik, location_id, role_id, employee_ktp, employee_firstName = "Presensi Karyawan", employee_lastName, employee_position,
            employee_IMEI, employee_phone, employee_gender, employee_birthDate, employee_address, employee_city,
            employee_region, employee_religion, created_at, updated_at;

    public ModelEmployee(String id, String nik, String location_id, String role_id, String employee_ktp, String employee_firstName, String employee_lastName, String employee_position, String employee_IMEI, String employee_phone, String employee_gender, String employee_birthDate, String employee_address, String employee_city, String employee_region, String employee_religion, String created_at, String updated_at) {
        this.id = id;
        this.nik = nik;
        this.location_id = location_id;
        this.role_id = role_id;
        this.employee_ktp = employee_ktp;
        this.employee_firstName = employee_firstName;
        this.employee_lastName = employee_lastName;
        this.employee_position = employee_position;
        this.employee_IMEI = employee_IMEI;
        this.employee_phone = employee_phone;
        this.employee_gender = employee_gender;
        this.employee_birthDate = employee_birthDate;
        this.employee_address = employee_address;
        this.employee_city = employee_city;
        this.employee_region = employee_region;
        this.employee_religion = employee_religion;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getEmployee_ktp() {
        return employee_ktp;
    }

    public void setEmployee_ktp(String employee_ktp) {
        this.employee_ktp = employee_ktp;
    }

    public String getEmployee_firstName() {
        return employee_firstName;
    }

    public void setEmployee_firstName(String employee_firstName) {
        this.employee_firstName = employee_firstName;
    }

    public String getEmployee_lastName() {
        return employee_lastName;
    }

    public void setEmployee_lastName(String employee_lastName) {
        this.employee_lastName = employee_lastName;
    }

    public String getEmployee_position() {
        return employee_position;
    }

    public void setEmployee_position(String employee_position) {
        this.employee_position = employee_position;
    }

    public String getEmployee_IMEI() {
        return employee_IMEI;
    }

    public void setEmployee_IMEI(String employee_IMEI) {
        this.employee_IMEI = employee_IMEI;
    }

    public String getEmployee_phone() {
        return employee_phone;
    }

    public void setEmployee_phone(String employee_phone) {
        this.employee_phone = employee_phone;
    }

    public String getEmployee_gender() {
        return employee_gender;
    }

    public void setEmployee_gender(String employee_gender) {
        this.employee_gender = employee_gender;
    }

    public String getEmployee_birthDate() {
        return employee_birthDate;
    }

    public void setEmployee_birthDate(String employee_birthDate) {
        this.employee_birthDate = employee_birthDate;
    }

    public String getEmployee_address() {
        return employee_address;
    }

    public void setEmployee_address(String employee_address) {
        this.employee_address = employee_address;
    }

    public String getEmployee_city() {
        return employee_city;
    }

    public void setEmployee_city(String employee_city) {
        this.employee_city = employee_city;
    }

    public String getEmployee_region() {
        return employee_region;
    }

    public void setEmployee_region(String employee_region) {
        this.employee_region = employee_region;
    }

    public String getEmployee_religion() {
        return employee_religion;
    }

    public void setEmployee_religion(String employee_religion) {
        this.employee_religion = employee_religion;
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
