package com.assignment.collpoll.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.assignment.collpoll.Interface.CheckList;
import com.assignment.collpoll.R;
import com.assignment.collpoll.WeatherDatabase;
import com.assignment.collpoll.adapter.WeatherAdapter;
import com.assignment.collpoll.model.WeatherDetails;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements CheckList {

    private RecyclerView recyclerView;
    private WeatherAdapter mAdapter;
    private List<WeatherDetails> myList = new ArrayList<>();
    private WeatherDatabase weatherDatabase = null;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        tv = findViewById(R.id.data_tv);
        mAdapter = new WeatherAdapter(this, myList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                weatherDatabase = WeatherDatabase.getInstance(getApplicationContext());
                myList = weatherDatabase.weatherDao().getWeatherDetailsList();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(myList);
                    }
                });
            }
        }).start();

    }

    private void updateUI(List<WeatherDetails> myList) {
        Log.d("asdf","update ui");
        if (myList.size()>0) {
            mAdapter.updateNewList(myList);
            tv.setVisibility(View.GONE);
        }else {
            tv.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            startActivity(new Intent(getApplicationContext(), SearchCityActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void checkListSize() {
        tv.setVisibility(View.VISIBLE);
    }
}
