
  ////////////////////////////// TCC - Smart Glove//////////////////////////////
  //  André Lucas de Macedo Santos                                            //
  //  Eduardo Augusto Prestes Júnior                                          //
  //  Luan Castelhano de Jesus                                                //
  //  Lucas Vinicius de Oliveira                                              //
  //  Raul Guilherme Gomes de Abreu Whitaker Salles                           //
  //////////////////////////////////////////////////////////////////////////////
  
  // Bibliotecas utilizadas pelo projeto:
  
  // Geral
  #include <stdio.h>
  #include <locale.h>
  #include <string.h>
  
  // Bluetooth
  #include <SoftwareSerial.h>
  
  // Definir pinos: RX e TX
  // RX -> 14
  // TX -> 15
  SoftwareSerial BT_HM10(14, 15); //SoftwareSerial(rxPin, txPin);
  
  // Giroscópio
  #include <Wire.h>
  #include <I2Cdev.h>
  
  // Constantes da MPU9250: Giroscópio
  
  #define    MPU9250_ADDRESS            0x69
  
  #define    GYRO_FULL_SCALE_250_DPS    0x00
  #define    GYRO_FULL_SCALE_500_DPS    0x08
  #define    GYRO_FULL_SCALE_1000_DPS   0x10
  #define    GYRO_FULL_SCALE_2000_DPS   0x18
  
  // Variáveis do Sensor Flex
  
  int sensorpinD1   = A0;
  int sensorpinD2   = A1;
  int sensorpinD3   = A2;
  int sensorpinD4   = A3;
  int sensorpinD5   = A4;
  int sensorvalorD1 = 0;
  int sensorvalorD2 = 0;
  int sensorvalorD3 = 0;
  int sensorvalorD4 = 0;
  int sensorvalorD5 = 0;
  int anguloD1      = 0;
  int anguloD2      = 0;
  int anguloD3      = 0;
  int anguloD4      = 0;
  int anguloD5      = 0;
  char strGrau[]    = "°";
  
  // Variáveis do Sensor de Força
  
  int SenvalDF1     = 0;
  int SenvalDF2     = 0;
  int SenvalDF3     = 0;
  int SenvalDF4     = 0;
  int SenvalDF5     = 0;
  int SenpinDF1     = A5;
  int SenpinDF2     = A6;
  int SenpinDF3     = A7;
  int SenpinDF4     = A8;
  int SenpinDF5     = A9;
  int EscalaDF1     = 0;
  int EscalaDF2     = 0;
  int EscalaDF3     = 0;
  int EscalaDF4     = 0;
  int EscalaDF5     = 0;
  char strPorcent[] = "%";
  
  // Variáveis da MPU9250: Giroscópio
  
  short giroscopio[4] = {};
  short Wangulo, Xangulo, Yangulo, Zangulo;

  //  I2C(Inter-Integrated Circuit) é um barramento serial: 
  //  Barramento multimestre desenvolvido pela Philips 
  //  que é usado para conectar periféricos de baixa velocidade 
  //  a uma placa mãe, a um sistema embarcado ou a um dispositivo celular.
  
  // Está função lê N bytes dos bytes do dispositivo I2C no endereço Address.
  // Aloca os bytes lidos iniciando o registrador Register no array Data (matriz de dados).
  void I2Cread(uint8_t Address, uint8_t Register, uint8_t Nbytes, uint8_t* Data)
  {
    // Definir endereço do registrador Register
    Wire.beginTransmission(Address);
    Wire.write(Register);
    Wire.endTransmission();
  
    // Lê N bytes
    Wire.requestFrom(Address, Nbytes);
    uint8_t index = 0;
    while (Wire.available())
      Data[index++] = Wire.read();
  }
  
  // Escreve o byte (Data ou Dado) no dispositivo (Address ou Endereço ou Endereçado) no registrador (Register)
  void I2CwriteByte(uint8_t Address, uint8_t Register, uint8_t Data)
  {
    // Definir endereço do registrador Register
    Wire.beginTransmission(Address);
    Wire.write(Register);
    Wire.write(Data);
    Wire.endTransmission();
  }
  
  //  Inicializações | Configurações dos Pinos como: Entrada ou Saída
  
  void setup() {
  
    // Sensor Flex: declaração dos pinos como entrada
    pinMode(sensorpinD1, INPUT);
    pinMode(sensorpinD2, INPUT);
    pinMode(sensorpinD3, INPUT);
    pinMode(sensorpinD4, INPUT);
    pinMode(sensorpinD5, INPUT);
  
    // Sensor de Força: declaração dos pinos como entrada
    pinMode(SenvalDF1, INPUT);
    pinMode(SenvalDF2, INPUT);
    pinMode(SenvalDF3, INPUT);
    pinMode(SenvalDF4, INPUT);
    pinMode(SenvalDF5, INPUT);

    // Inicializando biblioteca: Conexão (fio lógico)
    Wire.begin();

    // Inicializando Comunicação Serial 
    Serial.begin(115200); // 115.200 Bps (Bit por segundo)
    
    // Define o filtro passa-baixa do giroscópio em 5Hz
    I2CwriteByte(MPU9250_ADDRESS, 26, 0x06);
  
  
    // Configura o alcance do giroscópio
    I2CwriteByte(MPU9250_ADDRESS, 27, GYRO_FULL_SCALE_1000_DPS);
    
  }
  
  //  Rotina de LOOP: Execução Principal
  
  void loop() {
  
    // Código Principal de cada dedo para o Sensor Flex e Sensor de Força:

  
    //______________________________Dedo 1_________________________________//
  
    // Sensor Flex - Dedo 1
    sensorvalorD1 = analogRead(sensorpinD1);
  
    anguloD1 = map(sensorvalorD1, 274, 680, 0, 180);
    Serial.println("Dedo 1");
    Serial.print(" Valor: ");
    Serial.print(sensorvalorD1);
    Serial.print("  |  Ângulo: ");
    Serial.print(anguloD1);
    Serial.println(strGrau[1]);
    Serial.print("\n");
    delay(300);
  
    BT_HM10.print(anguloD1);     //Dado é enviado pelo bluetooth
    BT_HM10.println(strGrau[1]); // Dado é enviado pelo bluetooth
    
    // Sensor de Força - Dedo 1
  
    SenvalDF1 = analogRead(SenpinDF1);
  
    EscalaDF1 = map(SenvalDF1, 0, 66, 0, 100);
  
    Serial.println("Dedo 1");
    Serial.print(" Valor: ");
    Serial.print(SenvalDF1);
    Serial.print("  |  Força: ");
    Serial.print(EscalaDF1);
    Serial.println(strPorcent[1]);
    Serial.print("\n");
    delay(300);
  
    BT_HM10.print(EscalaDF1);       //Dado é enviado pelo bluetooth
    BT_HM10.println(strPorcent[1]); // Dado é enviado pelo bluetooth

    
    //______________________________Dedo 2_________________________________//
  
    // Sensor Flex - Dedo 2
  
    sensorvalorD2 = analogRead(sensorpinD2);
  
    anguloD2 = map(sensorvalorD2, 274, 680, 0, 180);
    Serial.println("Dedo 2");
    Serial.print(" Valor: ");
    Serial.print(sensorvalorD2);
    Serial.print("  |  Ângulo: ");
    Serial.print(anguloD2);
    Serial.println(strGrau[1]);
    Serial.print("\n");
    delay(300);
  
    BT_HM10.print(anguloD2);     //Dado é enviado pelo bluetooth
    BT_HM10.println(strGrau[1]); // Dado é enviado pelo bluetooth
    
    // Sensor de Força - Dedo 2
  
    SenvalDF2 = analogRead(SenpinDF2);
  
    EscalaDF2 = map(SenvalDF2, 0, 66, 0, 100);
  
    Serial.println("Dedo 2");
    Serial.print(" Valor: ");
    Serial.print(SenvalDF2);
    Serial.print("  |  Força: ");
    Serial.print(EscalaDF2);
    Serial.println(strPorcent[1]);
    Serial.print("\n");
    delay(300);
  
    BT_HM10.print(EscalaDF2);       //Dado é enviado pelo bluetooth
    BT_HM10.println(strPorcent[1]); // Dado é enviado pelo bluetooth

    
    //______________________________Dedo 3_________________________________//
  
    // Sensor Flex - Dedo 3
  
    sensorvalorD3 = analogRead(sensorpinD3);
  
    anguloD3 = map(sensorvalorD3, 274, 680, 0, 180);
    Serial.println("Dedo 3");
    Serial.print(" Valor: ");
    Serial.print(sensorvalorD3);
    Serial.print("  |  Ângulo: ");
    Serial.print(anguloD3);
    Serial.println(strGrau[1]);
    Serial.print("\n");
    delay(300);
  
    BT_HM10.print(anguloD3);     //Dado é enviado pelo bluetooth
    BT_HM10.println(strGrau[1]); // Dado é enviado pelo bluetooth
    
    // Sensor de Força - Dedo 3
  
    SenvalDF3 = analogRead(SenpinDF3);
  
    EscalaDF3 = map(SenvalDF3, 0, 66, 0, 100);
  
    Serial.println("Dedo 3");
    Serial.print(" Valor: ");
    Serial.print(SenvalDF3);
    Serial.print("  |  Força: ");
    Serial.print(EscalaDF3);
    Serial.println(strPorcent[1]);
    Serial.print("\n");
    delay(300);
  
    BT_HM10.print(EscalaDF3);       //Dado é enviado pelo bluetooth
    BT_HM10.println(strPorcent[1]); // Dado é enviado pelo bluetooth

    
    //______________________________Dedo 4_________________________________//
  
    // Sensor Flex - Dedo 4
  
    sensorvalorD4 = analogRead(sensorpinD4);
  
    anguloD4 = map(sensorvalorD4, 274, 680, 0, 180);
    Serial.println("Dedo 4");
    Serial.print(" Valor: ");
    Serial.print(sensorvalorD4);
    Serial.print("  |  Ângulo: ");
    Serial.print(anguloD4);
    Serial.println(strGrau[1]);
    Serial.print("\n");
    delay(300);
  
    BT_HM10.print(anguloD4);     //Dado é enviado pelo bluetooth
    BT_HM10.println(strGrau[1]); // Dado é enviado pelo bluetooth
    
    // Sensor de Força - Dedo 4
  
    SenvalDF4 = analogRead(SenpinDF4);
  
    EscalaDF4 = map(SenvalDF4, 0, 66, 0, 100);
  
    Serial.println("Dedo 4");
    Serial.print(" Valor: ");
    Serial.print(SenvalDF4);
    Serial.print("  |  Força: ");
    Serial.print(EscalaDF4);
    Serial.println(strPorcent[1]);
    Serial.print("\n");
    delay(300);
  
    BT_HM10.print(EscalaDF4);       //Dado é enviado pelo bluetooth
    BT_HM10.println(strPorcent[1]); // Dado é enviado pelo bluetooth

    
    //______________________________Dedo 5_________________________________//
  
    // Sensor Flex - Dedo 5
  
    sensorvalorD5 = analogRead(sensorpinD5);
  
    anguloD5 = map(sensorvalorD5, 274, 680, 0, 180);
    Serial.println("Dedo 5");
    Serial.print(" Valor: ");
    Serial.print(sensorvalorD5);
    Serial.print("  |  Ângulo: ");
    Serial.print(anguloD5);
    Serial.println(strGrau[1]);
    Serial.print("\n");
    delay(300);
  
    BT_HM10.print(anguloD5);     // Dado é enviado pelo bluetooth
    BT_HM10.println(strGrau[1]); // Dado é enviado pelo bluetooth
    
    // Sensor de Força - Dedo 5
  
    SenvalDF5 = analogRead(SenpinDF5);
  
    EscalaDF5 = map(SenvalDF5, 0, 66, 0, 100);
  
    Serial.println("Dedo 5");
    Serial.print(" Valor: ");
    Serial.print(SenvalDF5);
    Serial.print("  |  Força: ");
    Serial.print(EscalaDF5);
    Serial.println(strPorcent[1]);
    Serial.print("\n");
    delay(300);
  
    BT_HM10.print(EscalaDF5);       // Dado é enviado pelo bluetooth
    BT_HM10.println(strPorcent[1]); // Dado é enviado pelo bluetooth

    
    //::::::::::::::::::::::: MPU9250 - Giroscópio :::::::::::::::::::::::://
    
    // Ler giroscópio
    
    uint8_t Buf[14];
    I2Cread(MPU9250_ADDRESS, 0x3B, 14, Buf);

    // Giroscópio
    
    int16_t gw = (Buf[6] <<8 | Buf[7]);
    int16_t gx = -(Buf[8] << 8 | Buf[9]);
    int16_t gy = -(Buf[10] << 8 | Buf[11]);
    int16_t gz = (Buf[12] << 8 | Buf[13]);

    Wangulo = map(gw,-32768, 32767, -360, 360);

    Xangulo = map(gx,-32768, 32767, -360, 360);
  
    Yangulo = map(gy,-32768, 32767, -360, 360);
  
    Zangulo = map(gz,-32768, 32767, -360, 360);

    Serial.println("Giroscópio, ângulo: W, X, Y, Z");
  
    giroscopio[0] = Serial.print (Wangulo, DEC);
    Serial.print(strGrau[1]);
    Serial.print(",");

    BT_HM10.print(giroscopio[0]); // Dado é enviado pelo bluetooth
    BT_HM10.println(strGrau[1]);  // Dado é enviado pelo bluetooth
    
    giroscopio[1] = Serial.print (Xangulo, DEC);
    Serial.print(strGrau[1]);
    Serial.print(",");  

    BT_HM10.print(giroscopio[1]); // Dado é enviado pelo bluetooth
    BT_HM10.println(strGrau[1]);  // Dado é enviado pelo bluetooth
    
    giroscopio[2] = Serial.print (Yangulo, DEC);
    Serial.print(strGrau[1]);
    Serial.print(",");  

    BT_HM10.print(giroscopio[2]); // Dado é enviado pelo bluetooth
    BT_HM10.println(strGrau[1]);  // Dado é enviado pelo bluetooth
    
    giroscopio[3] = Serial.print (Zangulo, DEC);
    Serial.print(strGrau[1]);
    Serial.println(",");

    BT_HM10.print(giroscopio[3]); // Dado é enviado pelo bluetooth
    BT_HM10.println(strGrau[1]);  // Dado é enviado pelo bluetooth
    
  }
