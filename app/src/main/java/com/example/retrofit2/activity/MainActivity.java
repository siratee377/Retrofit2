package com.example.retrofit2.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    SwipeRefreshLayout pullToRefresh;
    private RecyclerView.LayoutManager layoutManager;
    private List<Items> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_image);
        pullToRefresh = findViewById(R.id.pull_to_refresh);

        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);

        final Call<Feeds> call = service.getItems("json",1);

        call.enqueue(new Callback<Feeds>() {
            @Override
            public void onResponse(Call<Feeds> call, Response<Feeds> response) {
                generateItemsList(response.body().getItems());
            }

            @Override
            public void onFailure(Call<Feeds> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                recreate();

                pullToRefresh.setRefreshing(false);
            }
        });


    }

    private void generateItemsList(List<Items> DataList) {


        adapter = new Adapter(DataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

}
