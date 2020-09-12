package com.android.car.testapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.car.testapplication.Database.DatabaseHandler;
import com.android.car.testapplication.Models.EmpSub;
import com.android.car.testapplication.Models.Employee;
import com.android.car.testapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private APIService mApiService;
    private List<Employee> employeeList = new ArrayList<>();
    String BASE_URL = "http://www.mocky.io/";
    ActivityMainBinding binding;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);
        mApiService = CreateSerVice();
        if (db.getEmpCount() == 0)
            getEmployeeDetailsFromServer();
        else
            getDataFromDB();
    }

    private void getEmployeeDetailsFromServer() {
        Call<List<Employee>> call = mApiService.getEmployeeDetails();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                try {
                    Log.d(TAG, "getEmployeeDetailsFromServer  code " + response.code() + "  body " + response.body());
                    //if (response.code() == 200) {
                    if (response.body() != null) {
                        employeeList = response.body();
                        if (employeeList != null && employeeList.isEmpty()) {
                            setView(employeeList);
                            //writing to db
                            saveToDatabase(employeeList);
                           /* Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //fetching from db
                                    getTheData();
                                }
                            }, 1000);*/
                        } else {
                            //List is Empty
                        }
                    }
                    //}
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                if (t != null) {
                    Log.e(TAG, t.getMessage());
                }
            }
        });
    }

    private void getDataFromDB() {
        List<EmpSub> empSubList = db.getEmployeeSubList();
        setRecycler(empSubList);
    }

    private void setView(List<Employee> employeeList) {
        List<EmpSub> empSubList = new ArrayList<>();
        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            EmpSub empSub = new EmpSub(employee.getId(), employee.getName(), employee.getProfile_image(), employee.getCompany().getName());
            empSubList.add(empSub);
        }
        setRecycler(empSubList);
    }

    private void setRecycler(List<EmpSub> empSubList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerView.setAdapter(new EmployeeAdapter(this, empSubList));
    }

    private void saveToDatabase(final List<Employee> employeeList) {
        new Thread(new Runnable() {
            public void run() {
                for (Employee emp : employeeList) {
                    db.addEmployee(emp);
                }
            }
        }).start();
    }

    public APIService CreateSerVice() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(APIService.class);
    }
}