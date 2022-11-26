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

//VARIAVEIS
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //variaveis
    EditText regusuario, regsenha;
    Button loginbtn;
    TextView recuperar_senha;

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference("Profissional");
    private BluetoothAdapter BA;
    private final String nomeDispositivo = "beMyEyes"; //Mude beMyEyes para o nome do seu módulo Bluetooth.
    private final int REQUEST_ENABLE_BT = 1; // Código padrão para o requerimento em tempo de execuxão.
    private ConexaoBluetooth conexao;
    private IntentFilter it = null;
    private final String[] PermissionsLocation = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}; //Array de permissões relacionadas ao Bluetooth no Android 6.0 ou maior
    private final int ResquestLocationId = 0; // Código padrão para o requerimento em tempo de execução.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bluetooth
        while (true) {
            it = new IntentFilter(); // Instancia o filtro declarado logo após o onCreate().
            it.addAction(BluetoothDevice.ACTION_FOUND);
            it.addCategory(Intent.CATEGORY_DEFAULT);
            registerReceiver(mReceiver, it); // Registra um Receiver para o App.
            break;
        }

        BA = BluetoothAdapter.getDefaultAdapter();
        BtEnable();

    }

    //bluetooth
    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // Quando a ação "discover" achar um dispositivo
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                try {
                    if (device.getName().trim().equals(nomeDispositivo)) {
                        conexao = ConexaoBluetooth.getInstance(device, true);
                        if (conexao.isConnected()) {
                            Toast.makeText(MainActivity.this, "Conectado ao " + device.getName(), Toast.LENGTH_SHORT).show();
                            changeActivity(); // chama a ReceivingData
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //bluetooth
    private void changeActivity() {

        Intent i = new Intent(this, ReceivingData.class);
        startActivity(i);
    }

    public void BtEnable() {
        //liga o bluetooth
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, REQUEST_ENABLE_BT);
            Toast.makeText(MainActivity.this, "Bluetooth Ligado", Toast.LENGTH_SHORT).show();

        } else {
            lookFor();
        }
        // Essa if em especial, verifica se a versão Android é 6.0 ou maior, pois caso seja, uma permissão para localização, além das relacionadas ao Bluetooth, sao necessárias.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(PermissionsLocation, ResquestLocationId);
            }
        }
    }

    protected void lookFor() { // Procura por dispositivos
        if (BA.startDiscovery()) {
        } else
            ;
    }

    private void changeActivity() {

        Intent i = new Intent(this,ReceivingData.class);
        startActivity(i);
    }

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
