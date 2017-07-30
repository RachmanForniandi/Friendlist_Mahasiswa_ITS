package com.example.android.friendlist_mahasiswa_its.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.friendlist_mahasiswa_its.R;
import com.example.android.friendlist_mahasiswa_its.model.Mahasiswa;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lenovo on 7/29/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<Mahasiswa> mahasiswa;

    public RecyclerViewAdapter(Context context, List<Mahasiswa> mahasiswa){
        this.context = context;
        this.mahasiswa = mahasiswa;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        Mahasiswa mhs = mahasiswa.get(position);
        holder.textViewNRP.setText(mhs.getNrp());
        holder.textViewNama.setText(mhs.getNama());
        holder.textViewJurusan.setText(mhs.getJurusan());
        holder.textViewJenisKelamin.setText(mhs.getJenis_kelamin());
    }

    @Override
    public int getItemCount(){
        return mahasiswa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.txtNRP)TextView textViewNRP;
        @BindView(R.id.txtNama)TextView textViewNama;
        @BindView(R.id.txtJurusan)TextView textViewJurusan;
        @BindView(R.id.txtJenisKelamin)TextView textViewJenisKelamin;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }


}
