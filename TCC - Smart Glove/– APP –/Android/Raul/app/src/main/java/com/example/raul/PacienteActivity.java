package com.example.raul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PacienteActivity extends AppCompatActivity {

    //variaveis
    EditText regcodigo, regrg, regnome_completo, regnascimento, regtelefone, regendereco, regemail;
    TextInputLayout regobservacoes;
    RadioButton regmasculino, regfeminino;
    Button cadastrarbtn;
    String genero;

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference("Paciente");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_pac);

        //todos os elementos do layout
        regcodigo = findViewById(R.id.codtxt);
        regrg = findViewById(R.id.editTextNumber2);
        regnome_completo = findViewById(R.id.usuariocadastrotxt3);
        regnascimento = findViewById(R.id.dataNascimento);
        regtelefone = findViewById(R.id.editTextPhone);
        regendereco = findViewById(R.id.editTextTextPostalAddress);
        regemail = findViewById(R.id.editTextTextEmailAddress2);
        regobservacoes = findViewById(R.id.textInputLayout4);
        regmasculino = findViewById(R.id.masculinotxt);
        regfeminino = findViewById(R.id.femininotxt);
        cadastrarbtn = findViewById(R.id.cadastrarbtn);

        //desmarca o outro genero quando o usuario clica no genero certo

        //masculino
        regmasculino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regfeminino.isChecked() == true){
                    regfeminino.setChecked(false);
                }
                genero = "0";
            }
        });

        //feminino
        regfeminino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regmasculino.isChecked() == true){
                    regmasculino.setChecked(false);
                }
                genero = "1";
            }
        });

        cadastrarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String codigo = regcodigo.getText().toString();
                String rg = regrg.getText().toString();
                String nome_completo = regnome_completo.getText().toString();
                String nascimento = regnascimento.getText().toString();
                String telefone = regtelefone.getText().toString();
                String endereco = regendereco.getText().toString();
                String email = regemail.getText().toString();
                String observacoes = regobservacoes.getEditText().getText().toString();

                //regmasculino.isChecked();
                //regfeminino.isChecked();

                if(codigo.equals("")||rg.equals("")||nome_completo.equals("")||nascimento.equals("")||telefone.equals("")||endereco.equals("")||email.equals("")){
                    Toast.makeText(PacienteActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();

                }
                else{
                    if (regmasculino.isChecked() || regfeminino.isChecked()){
                        //faz a comunicação com o banco
                        //Toast.makeText(PacienteActivity.this, "foi", Toast.LENGTH_SHORT).show();
                        getdata(codigo, rg, nome_completo, nascimento, telefone, endereco, email, observacoes, genero);
                    }
                    else{
                        Toast.makeText(PacienteActivity.this, "Por favor, selecione o gênero", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void getdata(String codigo, String rg, String nome_completo, String nascimento, String telefone, String endereco, String email, String observacoes, String genero){
        referencia.child(codigo).child("cod_pac").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                //verifica se já existe o registro
                if(snapshot.exists()){
                    Toast.makeText(PacienteActivity.this, "Não é possível cadastrar, pois esse código já está sendo utilizado", Toast.LENGTH_SHORT).show();
                }
                else{
                    referencia.child(codigo).child("cod_pac").setValue(codigo);
                    referencia.child(codigo).child("num_rg_pac").setValue(rg);
                    referencia.child(codigo).child("nom_pac").setValue(nome_completo);
                    referencia.child(codigo).child("dat_nasc_pac").setValue(nascimento);
                    referencia.child(codigo).child("gen_pac").setValue(genero);
                    referencia.child(codigo).child("tel_pac").setValue(telefone);
                    referencia.child(codigo).child("end_pac").setValue(endereco);
                    referencia.child(codigo).child("email_pac").setValue(email);
                    referencia.child(codigo).child("obs_pac").setValue(observacoes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PacienteActivity.this, "Houve uma falha durante a obtenção dos dados", Toast.LENGTH_SHORT).show();
            }
        });
    }
}