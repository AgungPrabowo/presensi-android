package com.example.root.presensi.util;

public class UtilsApi {

    public static final String BASE_URL_API = "http://tugasakhir.site/api/v1/";

    // deklarasi interface base api service
    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
