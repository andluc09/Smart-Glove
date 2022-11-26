package com.example.raul;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DadosTreinoAdapter extends RecyclerView.Adapter<DadosTreinoAdapter.ViewHolder> {

    Context context;
    List<DadosTreino> treino_list;

    public DadosTreinoAdapter(Context context, List<DadosTreino> treino_list) {
        this.context = context;
        this.treino_list = treino_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (treino_list != null && treino_list.size() > 0){
            DadosTreino treino = treino_list.get(position);
            holder.id_tv.setText(treino.getId());
            holder.name_tv.setText(treino.getName());
            holder.payment_tv.setText(treino.getPayment());
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return treino_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_tv, name_tv, payment_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_tv = itemView.findViewById(R.id.id_tv);
            name_tv = itemView.findViewById(R.id.name_tv);
            payment_tv = itemView.findViewById(R.id.payment_tv);
        }
    }
}
