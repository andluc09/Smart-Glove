package com.example.raul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MonitorarActivity extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Paciente");




    ImageView btnextensao, btnflexao, btnreposicao, btnoposicao, btnaducao, btnabducao, btnflexaopolegar, btnextensaopolegar, btnabducaopolegar, btnaducaopolegar;
    String exercicio = "Extensão", parametro;
    TextView tipo_pesquisa;
    Spinner spinpesquisa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitorar);

        btnextensao = findViewById(R.id.imgextensao);
        btnflexao = findViewById(R.id.imgflexao);
        btnreposicao = findViewById(R.id.imgreposicao);
        btnoposicao = findViewById(R.id.imgoposicao);
        btnaducao = findViewById(R.id.imgaducao);
        btnabducao = findViewById(R.id.imgabducao);
        btnflexaopolegar = findViewById(R.id.imgflexaopolegar);
        btnextensaopolegar = findViewById(R.id.imgextensaopolegar);
        btnabducaopolegar = findViewById(R.id.imgabducaopolegar);
        btnaducaopolegar = findViewById(R.id.imgaducaopolegar);
        tipo_pesquisa = findViewById(R.id.entpesquisapaciente);
        spinpesquisa = findViewById(R.id.texto_pesquisa);


        btnextensao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                exercicio = "Extensão";
                Toast.makeText(MonitorarActivity.this, "Selecionado: " + exercicio, Toast.LENGTH_SHORT).show();
            }
        });
        btnflexao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                exercicio = "Flexão";
                Toast.makeText(MonitorarActivity.this, "Selecionado: " + exercicio, Toast.LENGTH_SHORT).show();
            }
        });
        btnreposicao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                exercicio = "Reposição";
                Toast.makeText(MonitorarActivity.this, "Selecionado: " + exercicio, Toast.LENGTH_SHORT).show();
            }
        });
        btnoposicao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                exercicio = "Oposição";
                Toast.makeText(MonitorarActivity.this, "Selecionado: " + exercicio, Toast.LENGTH_SHORT).show();
            }
        });
        btnaducao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                exercicio = "Adução";
                Toast.makeText(MonitorarActivity.this, "Selecionado: " + exercicio, Toast.LENGTH_SHORT).show();
            }
        });
        btnabducao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                exercicio = "Abdução";
                Toast.makeText(MonitorarActivity.this, "Selecionado: " + exercicio, Toast.LENGTH_SHORT).show();
            }
        });
        btnflexaopolegar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                exercicio = "Flexão Polegar";
                Toast.makeText(MonitorarActivity.this, "Selecionado: " + exercicio, Toast.LENGTH_SHORT).show();
            }
        });
        btnextensaopolegar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                exercicio = "Extensão Polegar";
                Toast.makeText(MonitorarActivity.this, "Selecionado: " + exercicio, Toast.LENGTH_SHORT).show();
            }
        });
        btnabducaopolegar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                exercicio = "Abdução Polegar";
                Toast.makeText(MonitorarActivity.this, "Selecionado: " + exercicio, Toast.LENGTH_SHORT).show();
            }
        });
        btnaducaopolegar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                exercicio = "Adução Polegar";
                Toast.makeText(MonitorarActivity.this, "Selecionado: " + exercicio, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void procuraPaciente(String paciente_selecionado){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Boolean condicao = true;
                tipoSpin();
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    String pac = snapshot.child(parametro).getValue().toString();
                    if (paciente_selecionado.equals(pac)){
                        String cod_paciente = snapshot.child("cod_pac").getValue().toString();
                        Toast.makeText(MonitorarActivity.this, "Paciente selecionado: " + cod_paciente, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ExercicioActivity.class);
                        Intent receiverIntent = getIntent();
                        String nom_usu = receiverIntent.getStringExtra("KEY_NOM_USU_PRO");
                        intent.putExtra("KEY_SENDER", exercicio);
                        intent.putExtra("KEY_NOM_USU_PRO", nom_usu);
                        intent.putExtra("KEY_COD_PAC", cod_paciente);
                        startActivity(intent);
                        condicao = false;
                    }
                }
                if(condicao == true){
                    Toast.makeText(MonitorarActivity.this, "Não foi possível localizar o paciente", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonitorarActivity.this, "Houve uma falha durante a obtenção dos dados", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void tipoSpin(){
        if(spinpesquisa.getSelectedItem().toString().equals("Código")){
            parametro = "cod_pac";
        }
        else if(spinpesquisa.getSelectedItem().toString().equals("Número do RG")){
            parametro = "num_rg_pac";
        }
        else if(spinpesquisa.getSelectedItem().toString().equals("E-mail")){
            parametro = "email_pac";
        }
        else if(spinpesquisa.getSelectedItem().toString().equals("N° do Telefone")){
            parametro = "tel_pac";
        }
        else{
            parametro = "nom_pac";
        }
    }



    public void enviarInformação(View v){
        String paciente_selecionado = tipo_pesquisa.getText().toString();
        if (paciente_selecionado.equals("")){
            Toast.makeText(this, "Por favor, preencha o campo de pesquisa", Toast.LENGTH_SHORT).show();
        }
        else{
            tipoSpin();
            procuraPaciente(paciente_selecionado);
        }
    }

}
