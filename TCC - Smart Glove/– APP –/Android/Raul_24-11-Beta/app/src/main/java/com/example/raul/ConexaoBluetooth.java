package com.example.raul;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class ConexaoBluetooth {

    private static ConexaoBluetooth conexao;
    private final BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice  BTDevice;
    private final BluetoothSocket  BTSocket;
    private BufferedReader mBufferedReader = null;
    private final UUID activeUUID= UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //ID padrão da porta serial.
    private boolean isConnected = true;
    private final int REQUEST_ENABLE_BT = 1;

    public BufferedReader getmBufferedReader() {
        return mBufferedReader;
    }

    private ConexaoBluetooth(BluetoothDevice device) {
        BTDevice = device;
        BluetoothSocket temp = null;

        try
        {
            temp = BTDevice.createRfcommSocketToServiceRecord(activeUUID);
        }
        catch (IOException io)
        {
            Log.i("LOG", "Erro IO");
        }
        BTSocket = temp;

        if(BTAdapter.isDiscovering())
            BTAdapter.cancelDiscovery();

        try {

            InputStream aStream = null;
            InputStreamReader aReader = null;
            BTSocket.connect();

            aStream = BTSocket.getInputStream();
            aReader = new InputStreamReader( aStream );
            mBufferedReader = new BufferedReader( aReader );
        }catch (IOException e) {
            isConnected = false;
            return;
        }
    }

    public static ConexaoBluetooth getInstance(BluetoothDevice d, boolean subrescrever) {
        if (conexao == null)
            conexao = new ConexaoBluetooth(d);
        else
        if(subrescrever)
        {
            conexao = new ConexaoBluetooth(d);
            Log.i( "ConexaoBluetooth","Sobrescreveu a conexão");
        }
        return conexao;
    }

    public boolean isConnected () {
        return BTAdapter.isEnabled();
    }


    public BluetoothSocket getConection() {
        return BTSocket;
    }

    public boolean finish() {
        try {
            BTSocket.close();
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }


}