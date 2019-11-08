package com.example.retrofit2.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.retrofit2.R;
import com.example.retrofit2.adapter.Adapter;
import com.example.retrofit2.adapter.ItemAdapter;
import com.example.retrofit2.model.Feeds;
import com.example.retrofit2.model.Item;
import com.example.retrofit2.model.Items;
import com.example.retrofit2.network.GetDataService;
import com.example.retrofit2.network.RetrofitInstance;
import com.example.retrofit2.sqlite.Constant;
import com.example.retrofit2.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Adapter adapter;
    private ItemAdapter itemAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout pullToRefresh;
    private RecyclerView.LayoutManager layoutManager;
    private List<Items> items = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();
    private SQLiteDatabase dbWrite, dbRead;
    private Cursor cursor;
    private DBHelper objToCreateDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_image);
        pullToRefresh = findViewById(R.id.pull_to_refresh);

        objToCreateDB = new DBHelper(this);

        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);

        final Call<Feeds> call = service.getItems("json",1);

        call.enqueue(new Callback<Feeds>() {
            @Override
            public void onResponse(Call<Feeds> call, Response<Feeds> response) {
                generateItemsList(response.body().getItems());
            }

            @Override
            public void onFailure(Call<Feeds> call, Throwable t) {

                dbRead = objToCreateDB.getReadableDatabase();
                String[] columns = new String[]{Constant.LINK, Constant.MEDIA};
                cursor = dbRead.query(Constant.TB_NAME, columns, null, null, null, null,null);

                while(cursor.moveToNext()) {
                    String link = cursor.getString(0);
                    String media = cursor.getString(1);
                    Item item = new Item(link,media);
                    itemList.add(item);
                }

                itemAdapter = new ItemAdapter(itemList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(itemAdapter);

                Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
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

        for (int i=0; i < DataList.size(); i++)
        {
            dbWrite = objToCreateDB.getWritableDatabase();

            //dbWrite.delete(Constant.TB_NAME,null,null);

            String link = DataList.get(i).getLink();
            String media = DataList.get(i).getMedia().getM();

            ContentValues cv = new ContentValues();
            cv.put(Constant.LINK, link);
            cv.put(Constant.MEDIA, media);

           dbWrite = objToCreateDB.getWritableDatabase();
           dbWrite.insert(Constant.TB_NAME, null,cv);

        }

        adapter = new Adapter(DataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

    }

}
