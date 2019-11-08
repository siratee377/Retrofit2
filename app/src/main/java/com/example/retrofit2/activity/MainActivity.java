package com.example.retrofit2.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit2.R;
import com.example.retrofit2.adapter.Adapter;
import com.example.retrofit2.model.Feeds;
import com.example.retrofit2.model.Items;
import com.example.retrofit2.network.GetDataService;
import com.example.retrofit2.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Adapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Items> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_image);

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);

        /*Call the method with parameter in the interface to get the employee data*/
        Call<Feeds> call = service.getItems("json",1);

        /*Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<Feeds>() {
            @Override
            public void onResponse(Call<Feeds> call, Response<Feeds> response) {
                generateEmployeeList(response.body().getItems());
            }

            @Override
            public void onFailure(Call<Feeds> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
    }

    /*Method to generate List of employees using RecyclerView with custom adapter*/
    private void generateEmployeeList(List<Items> empDataList) {


        adapter = new Adapter(empDataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

}
