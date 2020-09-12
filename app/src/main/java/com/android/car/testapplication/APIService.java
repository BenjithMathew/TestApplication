package com.android.car.testapplication;

import com.android.car.testapplication.Models.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    String URl = "v2/5d565297300000680030a986";

    @GET(URl)
    Call<List<Employee>> getEmployeeDetails();
}
