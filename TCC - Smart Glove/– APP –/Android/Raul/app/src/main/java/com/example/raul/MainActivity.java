package com.example.raul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //variaveis
    EditText regusuario, regsenha;
    Button loginbtn;
    TextView recuperar_senha;

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference("Profissional");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //todos os elementos do layout
        regusuario = findViewById(R.id.usuariocadastrologintxt);
        regsenha = findViewById(R.id.senhacadastradotxt);
        loginbtn = findViewById(R.id.logarbtn);

        recuperar_senha = findViewById(R.id.casdastrartxt);

        recuperar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecuperarActivity.class);
                startActivity(intent);
            }
        });


        //referencia.child("usuarios").child("33").child("nome").setValue("pp9");

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = regusuario.getText().toString();
                String pass = regsenha.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();

                else{
                    getdata(user, pass);
                }


            }
        });


    }

    private void getdata(String user, String pass) {
        referencia.child(user).child("sen_pro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if (snapshot.exists()) {
                    if (value.equals(pass)) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("KEY_NOM_USU_PRO", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Parâmetros incorretos", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Parâmetros incorretos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Houve uma falha durante a obtenção dos dados", Toast.LENGTH_SHORT).show();
            }
        });
    }
}