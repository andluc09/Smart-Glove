package com.example.raul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.bumptech.glide.Glide;

public class ExercicioActivity extends AppCompatActivity {

    TextView textView;
    EditText regeixox, regeixoy, regeixoz;
    EditText regforcaindicador, regforcamedio, regforcaanelar, regforcaminimo, regforcapolegar;
    EditText regcurvaturaindicador, regcurvaturamedio, regcurvaturaanelar, regcurvaturaminimo, regcurvaturapolegar;
    Button btncancelar, btnconcluir;
    String horario;
    ImageView gifexercicio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monit);
        gifexercicio = findViewById(R.id.imgexercicio);

        final DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        final Calendar cal = Calendar.getInstance();
        //System.out.println(df.format(cal.getTime()));


        //todos os elementos do layout
        regeixox = findViewById(R.id.xtxt);
        regeixoy = findViewById(R.id.ytxt);
        regeixoz = findViewById(R.id.ztxt);

        regforcaindicador = findViewById(R.id.sensorforçaindicadortxt);
        regforcamedio = findViewById(R.id.sensorforçamédiotxt);
        regforcaanelar = findViewById(R.id.sensorforçaanelartxt);
        regforcaminimo = findViewById(R.id.sensorforçaminimotxt);
        regforcapolegar = findViewById(R.id.sensorforçapolegartxt);

        regcurvaturaindicador = findViewById(R.id.sensorflexindicadortxt);
        regcurvaturamedio = findViewById(R.id.sensorflexmediotxt);
        regcurvaturaanelar = findViewById(R.id.sensorflexanelartxt);
        regcurvaturaminimo = findViewById(R.id.sensorflexminimotxt);
        regcurvaturapolegar = findViewById(R.id.sensorflexpolegartxt);



        btncancelar = findViewById(R.id.cancelbtn);
        btnconcluir = findViewById(R.id.concluirbtn);

        Intent receiverIntentuser = getIntent();
        //Recebe o nome de usuario do profissional
        String nom_usu = receiverIntentuser.getStringExtra("KEY_NOM_USU_PRO");
        //Recebe o codigo do paciente
        String cod_pac = receiverIntentuser.getStringExtra("KEY_COD_PAC");

        //Recebe o nome do exercicio e muda na tela
        textView = (TextView) findViewById(R.id.lblexercicio);

        Intent receiverIntent = getIntent();
        String exercicio = receiverIntent.getStringExtra("KEY_SENDER");
        textView.setText(exercicio);

        if (exercicio.equals("Flexão")){
            Glide.with(ExercicioActivity.this).load(R.drawable.flexao).into(gifexercicio);
            System.out.println(exercicio);
        }

        else if (exercicio.equals("Extensão")){
            Glide.with(ExercicioActivity.this).load(R.drawable.extensao).into(gifexercicio);
            System.out.println(exercicio);
        }

        else if (exercicio.equals("Reposição")){
            Glide.with(ExercicioActivity.this).load(R.drawable.reposicao).into(gifexercicio);
            System.out.println(exercicio);
        }

        else if (exercicio.equals("Oposição")){
            Glide.with(ExercicioActivity.this).load(R.drawable.oposicao).into(gifexercicio);
            System.out.println(exercicio);
        }

        else if (exercicio.equals("Adução")){
            Glide.with(ExercicioActivity.this).load(R.drawable.aducao).into(gifexercicio);
            System.out.println(exercicio);
        }

        else if (exercicio.equals("Abdução")){
            Glide.with(ExercicioActivity.this).load(R.drawable.abducao).into(gifexercicio);
            System.out.println(exercicio);
        }

        else if (exercicio.equals("Flexão Polegar")){
            Glide.with(ExercicioActivity.this).load(R.drawable.flexao_polegar).into(gifexercicio);
            System.out.println(exercicio);
        }

        else if (exercicio.equals("Extensão Polegar")){
            Glide.with(ExercicioActivity.this).load(R.drawable.extensao_polegar).into(gifexercicio);
            System.out.println(exercicio);
        }

        else if (exercicio.equals("Abdução Polegar")){
            Glide.with(ExercicioActivity.this).load(R.drawable.abducao_polegar).into(gifexercicio);
            System.out.println(exercicio);
        }

        else if (exercicio.equals("Adução Polegar")){
            Glide.with(ExercicioActivity.this).load(R.drawable.aducao_polegar).into(gifexercicio);
            System.out.println(exercicio);
        }

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnconcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference referencia = FirebaseDatabase.getInstance().getReference("Treino");

                String eixox = regeixox.getText().toString();
                String eixoy = regeixoy.getText().toString();
                String eixoz = regeixoz.getText().toString();

                String forcaindicador = regforcaindicador.getText().toString();
                String forcamedio = regforcamedio.getText().toString();
                String forcaanelar = regforcaanelar.getText().toString();
                String forcaminimo = regforcaminimo.getText().toString();
                String forcapolegar = regforcapolegar.getText().toString();

                String curvaturaindicador = regcurvaturaindicador.getText().toString();
                String curvaturamedio = regcurvaturamedio.getText().toString();
                String curvaturaanelar = regcurvaturaanelar.getText().toString();
                String curvaturaminimo = regcurvaturaminimo.getText().toString();
                String curvaturapolegar = regcurvaturapolegar.getText().toString();

                horario = df.format(cal.getTime());

                referencia.child(horario).child("Paciente").setValue(cod_pac);
                referencia.child(horario).child("Profissional").setValue(nom_usu);
                referencia.child(horario).child("nome_treino").setValue(exercicio);
                referencia.child(horario).child("eixox").setValue(eixox);
                referencia.child(horario).child("eixoy").setValue(eixoy);
                referencia.child(horario).child("eixoz").setValue(eixoz);
                referencia.child(horario).child("forcaindicador").setValue(forcaindicador);
                referencia.child(horario).child("forcamedio").setValue(forcamedio);
                referencia.child(horario).child("forcaanelar").setValue(forcaanelar);
                referencia.child(horario).child("forcaminimo").setValue(forcaminimo);
                referencia.child(horario).child("forcapolegar").setValue(forcapolegar);
                referencia.child(horario).child("curvaturaindicador").setValue(curvaturaindicador);
                referencia.child(horario).child("curvaturamedio").setValue(curvaturamedio);
                referencia.child(horario).child("curvaturaanelar").setValue(curvaturaanelar);
                referencia.child(horario).child("curvaturaminimo").setValue(curvaturaminimo);
                referencia.child(horario).child("curvaturapolegar").setValue(curvaturapolegar);

                Toast.makeText(ExercicioActivity.this, "Registro gravado no banco de dados", Toast.LENGTH_SHORT).show();
                finish();

            }
        });





    }
}
