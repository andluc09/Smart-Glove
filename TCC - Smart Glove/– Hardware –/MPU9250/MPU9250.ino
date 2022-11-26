#include "mpu9250.h"
#include "MPU9250.h"
#include "Wire.h"
#include "I2Cdev.h"
//#include "i2c_MPU9250.h"


// an MPU9250 object with the MPU-9250 sensor on I2C bus 0 with address 0x68

MPU9250 IMU(Wire,0x68);


int status; void setup()
{

  // serial to display data

  Serial.begin(115200);
  while(!Serial) {}   // start communication with IMU 
  status = IMU.begin();
  if (status < 0) {
    Serial.println("IMU initialization unsuccessful");
    Serial.println("Check IMU wiring or try cycling power");
    Serial.print("Status: ");
    Serial.println(status);
    while(1) {}
  }
}

void loop()
{
  // read the sensor
  IMU.readSensor();
  // display the data
  Serial.print(IMU.getAccelX_mss(),0);
  Serial.print("|");
  Serial.print(IMU.getAccelY_mss(),0);
  Serial.print("|");
  Serial.print(IMU.getAccelZ_mss(),0);
  Serial.print("|");
  Serial.print(IMU.getGyroX_rads(),1);
  Serial.print("|");
  Serial.print(IMU.getGyroY_rads(),1);
  Serial.print("|");
  Serial.print(IMU.getGyroZ_rads(),1);
  Serial.print("|");
  Serial.print(IMU.getMagX_uT(),0);
  Serial.print("|");
  Serial.print(IMU.getMagY_uT(),0);
  Serial.print("|");
  Serial.print(IMU.getMagZ_uT(),0);
  Serial.print("|");
  Serial.println(IMU.getTemperature_C(),2);
  delay(1000);
}
