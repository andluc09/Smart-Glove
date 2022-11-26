package com.example.raul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    ImageView btncadpac, btndados, btnmonitorar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btncadpac = findViewById(R.id.imgcadastropaciente);
        btnmonitorar = findViewById(R.id.imgmonitorar);
        btndados = findViewById(R.id.imgdados);
        Intent receiverIntent = getIntent();
        String nom_usu = receiverIntent.getStringExtra("KEY_NOM_USU_PRO");
        btncadpac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PacienteActivity.class);
                startActivity(intent);
            }
        });
        btnmonitorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MonitorarActivity.class);
                intent.putExtra("KEY_NOM_USU_PRO", nom_usu);
                startActivity(intent);
            }
        });
        btndados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegistroActivity.class);
                intent.putExtra("KEY_NOM_USU_PRO", nom_usu);
                startActivity(intent);
            }
        });



    }
}