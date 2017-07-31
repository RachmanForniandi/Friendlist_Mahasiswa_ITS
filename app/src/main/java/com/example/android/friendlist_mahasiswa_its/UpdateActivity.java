package com.example.android.friendlist_mahasiswa_its;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

public class UpdateActivity extends AppCompatActivity {
    public static final String URL = "http://192.168.1.13/crud_android2/";
    private RadioButton radioJK_Btn;
    private ProgressDialog progress;
    String nrp, nama, jurusan;

    @BindView(R.id.et_NRP) EditText editTextNRP;
    @BindView(R.id.et_Nama) EditText editTextNama;
    @BindView(R.id.et_Jurusan) EditText editTextJurusan;
    @BindView(R.id.radio_JenisKelamin) RadioGroup radioGroup;
    @BindView(R.id.Laki_laki) RadioButton radioButtonLaki;
    @BindView(R.id.Perempuan) RadioButton radioButtonPerempuan;

    @OnClick(R.id.btn_Update) void update() {

        //utk menampilkan progress dialog
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        nrp = editTextNRP.getText().toString();
        nama = editTextNama.getText().toString();
        jurusan = editTextJurusan.getText().toString();

        int selectedId = radioGroup.getCheckedRadioButtonId();

        radioJK_Btn = (RadioButton) findViewById(selectedId);
        String jenis_kelamin = radioJK_Btn.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.update(nrp, nama, jurusan, jenis_kelamin);

        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();

                if (value.equals("1")) {
                    Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                Toast.makeText(UpdateActivity.this, "Jaringan sedang error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Data");

        Intent intent = getIntent();
        String nrp = intent.getStringExtra("nrp");
        String nama = intent.getStringExtra("nama");
        String jurusan = intent.getStringExtra("jurusan");
        String jenis_kelamin = intent.getStringExtra("jenis_kelamin");

        editTextNRP.setText(nrp);
        editTextNama.setText(nama);
        editTextJurusan.setText(jurusan);

        if (jenis_kelamin.equals("Laki - Laki")) {
            radioButtonLaki.setChecked(true);
        } else {
            radioButtonPerempuan.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_delete:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Peringatan");
                alertDialogBuilder
                        .setMessage("Apakah anda yakin ingin menghapus data ini?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String nrp = editTextNRP.getText().toString();
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                RegisterAPI api = retrofit.create(RegisterAPI.class);
                                Call<Value> call = api.hapus(nrp);
                                call.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(Call<Value> call, Response<Value> response) {
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")) {
                                            Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Value> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(UpdateActivity.this, "Jaringan Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


