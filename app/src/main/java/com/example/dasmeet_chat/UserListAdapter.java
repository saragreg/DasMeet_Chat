package com.example.dasmeet_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {
    private Context contexto;
    private LayoutInflater inflater;
    private ArrayList<String> nombres;
    private ArrayList<String> imagenes;

    public UserListAdapter(Context pcontext, ArrayList<String> pnombres, ArrayList<String> pimagenes){
        contexto = pcontext;
        nombres = pnombres;
        imagenes=pimagenes;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return nombres.size();
    }

    @Override
    public Object getItem(int i) {
        return nombres.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.adapter_user_list,null);
        TextView nombre= (TextView) view.findViewById(R.id.nombre);
        ImageView imagen=(ImageView) view.findViewById(R.id.imagen);
        nombre.setText(nombres.get(i));
        return view;
    }
}
