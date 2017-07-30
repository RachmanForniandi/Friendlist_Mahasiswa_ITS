package com.example.android.friendlist_mahasiswa_its;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.friendlist_mahasiswa_its.api.RegisterAPI;
import com.example.android.friendlist_mahasiswa_its.model.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://192.168.1.13/crud_android2/";
    private RadioButton radioJK_Btn;
    private ProgressDialog progress;
    String nrp,nama,jurusan;

    @BindView(R.id.et_NRP) EditText editTextNRP;
    @BindView(R.id.et_Nama)EditText editTextNama;
    @BindView(R.id.et_Jurusan)EditText editTextJurusan;
    @BindView(R.id.radio_JenisKelamin) RadioGroup radioGroup;
    @OnClick(com.example.android.friendlist_mahasiswa_its.R.id.btn_Daftar) void daftar(){

        //utk menampilkan progress dialog
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        nrp = editTextNRP.getText().toString();
        nama = editTextNama.getText().toString();
        jurusan = editTextJurusan.getText().toString();

        int selectedId = radioGroup.getCheckedRadioButtonId();

        radioJK_Btn = (RadioButton)findViewById(selectedId);
        String jenis_kelamin = radioJK_Btn.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.daftar(nrp,nama,jurusan,jenis_kelamin);

        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();

                if (value.equals("1")){
                    Toast.makeText(MainActivity.this, message,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                Toast.makeText(MainActivity.this,"Jaringan sedang error!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_Lihat)void lihat(){
        Intent pindah = new Intent(MainActivity.this, ViewActivity.class);
        startActivity(pindah);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
