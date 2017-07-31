package com.example.android.friendlist_mahasiswa_its;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.friendlist_mahasiswa_its.adapter.RecyclerViewAdapter;
import com.example.android.friendlist_mahasiswa_its.api.RegisterAPI;
import com.example.android.friendlist_mahasiswa_its.model.Mahasiswa;
import com.example.android.friendlist_mahasiswa_its.model.Value;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static final String URL ="http://192.168.1.13/crud_android2/";
    private List<Mahasiswa> mahasiswa = new ArrayList<>();
    private RecyclerViewAdapter viewAdapter;
    @BindView(R.id.recyclerView)RecyclerView recyclerView;
    @BindView(R.id.progress_bar)ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ButterKnife.bind(this);
        viewAdapter = new RecyclerViewAdapter(this, mahasiswa);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        loadDataMahasiwa();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadDataMahasiwa();
    }

    private void loadDataMahasiwa(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.view();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                if (value.equals("1")){
                    mahasiswa = response.body().getResult();
                    viewAdapter = new RecyclerViewAdapter(ViewActivity.this, mahasiswa);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });
    }
        @Override
        public boolean onCreateOptionsMenu(android.view.Menu menu){
            getMenuInflater().inflate(R.menu.menu_search, menu);
            final MenuItem item = menu.findItem(R.id.action_search);
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
            searchView.setQueryHint("Cari nama Mahasiswa");
            searchView.setIconified(false);
            searchView.setOnQueryTextListener(this);
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RegisterAPI api = retrofit.create(RegisterAPI.class);
            Call<Value> call = api.search(newText);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    if (value.equals("1")){
                        mahasiswa = response.body().getResult();
                        viewAdapter = new RecyclerViewAdapter(ViewActivity.this,mahasiswa);
                        recyclerView.setAdapter(viewAdapter);
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            return true;
        }
}
