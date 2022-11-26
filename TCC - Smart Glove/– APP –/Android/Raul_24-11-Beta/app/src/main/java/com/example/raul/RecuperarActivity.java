package com.example.raul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecuperarActivity extends AppCompatActivity {

    Button concluirbtn, cancelbtn;
    EditText lblpin, lblusuario;

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference("Profissional");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        concluirbtn = findViewById(R.id.recuperarconcluirbtn);

        cancelbtn = findViewById(R.id.cancelbtn);

        lblpin = findViewById(R.id.pincadastradotxt);
        lblusuario = findViewById(R.id.usuariocadastradotxt);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        concluirbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pin = lblpin.getText().toString();
                String usuario = lblusuario.getText().toString();

                if(usuario.equals("")||pin.equals(""))
                    Toast.makeText(RecuperarActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();

                else{
                    getdata(usuario, pin);
                }
            }
        });
    }

    private void getdata(String usuario, String pin){
        referencia.child(usuario).child("cod_pin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if (snapshot.exists()){
                    if (value.equals(pin)){
                        referencia.child(usuario).child("sen_pro").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String value = snapshot.getValue(String.class);
                                Toast.makeText(RecuperarActivity.this, "A senha é: "+value, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(RecuperarActivity.this, "Houve uma falha durante a obtenção dos dados", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{
                    Toast.makeText(RecuperarActivity.this, "Parâmetros incorretos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RecuperarActivity.this, "Houve uma falha durante a obtenção dos dados", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
