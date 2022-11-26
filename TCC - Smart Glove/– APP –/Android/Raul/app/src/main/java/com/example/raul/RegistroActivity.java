package com.example.raul;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    DadosTreinoAdapter adapter;

    EditText regdatainicial, regdatafinal, regtextopesquisa;

    Button pesquisarbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        regdatainicial = findViewById(R.id.datainicialtxt);

        regdatafinal = findViewById(R.id.datafinaltxt);

        regtextopesquisa = findViewById(R.id.textopesuisatxt);

        pesquisarbtn = findViewById(R.id.pesquisarbtn);

        recycler_view = findViewById(R.id.recycler_view);

        String datainicial = regdatainicial.getText().toString();
        String datafinal = regdatafinal.getText().toString();
        String textopesquisa = regtextopesquisa.getText().toString();

        pesquisarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Date datainicialconvertida = format.parse(datainicial);
                    System.out.println(datainicialconvertida);
                } catch (ParseException e) {
                    Toast.makeText(RegistroActivity.this, "Formato inválido para data", Toast.LENGTH_SHORT).show();
                };

                try {
                    Date datafinalconvertida = format.parse(datafinal);
                    System.out.println(datafinalconvertida);
                } catch (ParseException e) {
                    Toast.makeText(RegistroActivity.this, "Formato inválido para data", Toast.LENGTH_SHORT).show();
                };

                if (datainicial.equals("") || datafinal.equals("")||textopesquisa.equals("")) {
                    Toast.makeText(RegistroActivity.this, "Favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{

                    setRecyclerView();
                }
            }
        });


    }

    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DadosTreinoAdapter(this,getList());
        recycler_view.setAdapter(adapter);
    }

    private List<DadosTreino> getList(){
        List<DadosTreino> treino_list = new ArrayList<>();
        treino_list.add(new DadosTreino("1","Raul","1000"));
        treino_list.add(new DadosTreino("2","Andre","2000"));
        treino_list.add(new DadosTreino("3","Luan","3000"));
        treino_list.add(new DadosTreino("4","Eduardo","4000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("1","Raul","1000"));
        treino_list.add(new DadosTreino("2","Andre","2000"));
        treino_list.add(new DadosTreino("3","Luan","3000"));
        treino_list.add(new DadosTreino("4","Eduardo","4000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));
        treino_list.add(new DadosTreino("5","Lucas","5000"));

        return treino_list;
    }
}
