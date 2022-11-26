package com.example.raul;

import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReceivingData extends AppCompatActivity {

    private BluetoothSocket mmSocket;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private Thread workerThread;
    private byte[] readBuffer;
    private int readBufferPosition;
    private volatile boolean stopWorker;

    private ConexaoBluetooth connection = ConexaoBluetooth.getInstance(null, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_data);
        mmSocket = connection.getConection();

        try {
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
            mmOutputStream.write("AT".getBytes());


            beginListenForData();
            mmOutputStream.write("OK".getBytes());

            Toast.makeText(ReceivingData.this, "Recebendo dados do Bluetooth", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void beginListenForData() {

        final BufferedReader reader = connection.getmBufferedReader();
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        String info = reader.readLine(); // recebe os dados da comunicação
                        Toast.makeText(ReceivingData.this, "Mensagem recebida: " + info, Toast.LENGTH_SHORT).show();

                        mmOutputStream.write("OK".getBytes()); // envia dados.


                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }
}