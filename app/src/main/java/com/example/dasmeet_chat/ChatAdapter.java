package com.example.dasmeet_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private String user;
    private Context context;
    private ArrayList<String> nombres=new ArrayList<String>();
    private ArrayList<String> mensajes=new ArrayList<String>();
    private ArrayList<String> horas=new ArrayList<String>();

    public ChatAdapter(String user, Context context, ArrayList<String> nombres, ArrayList<String> mensajes, ArrayList<String> horas) {
        this.user = user;
        this.context = context;
        this.nombres = nombres;
        this.mensajes = mensajes;
        this.horas = horas;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        if (nombres.get(position).equals(user)){
            holder.miLayout.setVisibility(View.VISIBLE);
            holder.miMsg.setText(mensajes.get(position));
            holder.miHora.setText(horas.get(position));
            holder.otroLayout.setVisibility(View.GONE);
        }else {
            holder.miLayout.setVisibility(View.GONE);
            holder.otroMsg.setText(mensajes.get(position));
            holder.otroHora.setText(horas.get(position));
            holder.otroLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout otroLayout,miLayout;
        private TextView otroMsg,miMsg;
        private TextView otroHora,miHora;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            otroLayout=itemView.findViewById(R.id.otroLayout);
            miLayout=itemView.findViewById(R.id.miLayout);
            otroMsg=itemView.findViewById(R.id.otroMessage);
            miMsg=itemView.findViewById(R.id.miMessage);
            otroHora=itemView.findViewById(R.id.otrofechora);
            miHora=itemView.findViewById(R.id.mifechora);
        }
    }
}
