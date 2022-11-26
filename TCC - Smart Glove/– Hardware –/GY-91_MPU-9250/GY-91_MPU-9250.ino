#include <MPU9250_asukiaaa.h>

#include <Adafruit_BMP280.h>

Adafruit_BMP280 bme; // I2C

MPU9250_asukiaaa mySensor;

float aX, aY, aZ, aSqrt, gX, gY, gZ, mDirection, mX, mY, mZ;

void setup () {

Serial.begin (115200);

while (! Serial);

Wire.begin (SDA_PIN, SCL_PIN);

mySensor.setWire (& Wire);


bme.begin ();

mySensor.beginAccel ();

mySensor.beginGyro ();

mySensor.beginMag ();

// Você pode definir seu próprio deslocamento para valores mag

// mySensor.magXOffset = -50;

// mySensor.magYOffset = -55;

// mySensor.magZOffset = -10;

}

void loop () {

if (mySensor.accelUpdate () == 0) {

aX = mySensor.accelX ();

aY = mySensor.accelY ();

aZ = mySensor.accelZ ();

aSqrt = mySensor.accelSqrt ();

Serial.print ("accelX:" + String (aX));

Serial.print ("\ taccelY:" + String (aY));

Serial.print ("\ taccelZ:" + String (aZ));

Serial.print ("\ taccelSqrt:" + String (aSqrt));

}

if (mySensor.gyroUpdate () == 0) {

gX = mySensor.gyroX ();

gY = meuSensor.gyroY ();

gZ = mySensor.gyroZ ();

Serial.print ("\ tgyroX:" + String (gX));

Serial.print ("\ tgyroY:" + String (gY));

Serial.print ("\ tgyroZ:" + String (gZ));

}

if (mySensor.magUpdate () == 0) {

mX = mySensor.magX ();

mY = mySensor.magY ();

mZ = mySensor.magZ ();

mDirection = mySensor.magHorizDirection ();

Serial.print ("\ tmagX:" + String (mX));

Serial.print ("\ tmaxY:" + String (mY));

Serial.print ("\ tmagZ:" + String (mZ));

Serial.print ("\ thorizontalDirection:" + String (mDirection));

}

Serial.print ("\ tTemperatura (* C):");

Serial.print (bme.readTemperature ());

Serial.print ("\ tPressure (Polegadas (Hg)):");

Serial.print (bme.readPressure () / 3377);

Serial.print ("\ tApproxAltitude (m):");

Serial.print (bme.readAltitude (1013,25)); // isso deve ser ajustado para sua forcase local

Serial.println (""); // Adicione uma linha vazia

}
