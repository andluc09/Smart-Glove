/**
 * FreeIMU library serial communication protocol
*/

#include <ADXL345.h>
#include <bma180.h>
#include <HMC58X3.h>
#include <ITG3200.h>
#include <MS561101BA.h>
#include <I2Cdev.h>
#include <MPU60X0.h>
#include <EEPROM.h>

//#define DEBUG
#include "DebugUtils.h"
#include "CommunicationUtils.h"
#include "FreeIMU.h"
#include <Wire.h>
#include <SPI.h>

// Free IMU variables
boolean sendGyroData = false;
float q[4];
int raw_values[9];
float ypr[3]; // yaw pitch roll
char str[256];
float val[9];
FreeIMU my3IMU = FreeIMU(); // Set the FreeIMU object

// Touch sensors variables
int sensorValue = 0;
int touchThr = 920;
int sensorPins[] = {A0, A1, A2, A3, A4};
typedef enum { NO_TOUCH, TOUCH } FState;
FState fingerState[] = {NO_TOUCH, NO_TOUCH, NO_TOUCH, NO_TOUCH, NO_TOUCH};

//The command from the PC
char cmd;

void setup() {
  Serial1.begin(115200);
  Wire.begin();
  
  my3IMU.init(true);
  my3IMU.init(MPU60X0_ADDRESS_AD0_HIGH, true);
}


void loop() {
  
  // read the value from the touch sensor:
  for ( char i=0; i<5; i++ ) {
    sensorValue = analogRead( sensorPins[i] );
    switch ( fingerState[i] ) {
    case NO_TOUCH:
      if(sensorValue < touchThr) { // finger press
        fingerState[i] = TOUCH;
        sprintf(str, "fp%d", i);
        Serial1.print(str);
        Serial1.print('\n');
      }
      break;
      
    case TOUCH:
      if(sensorValue > touchThr) { // finger release
        fingerState[i] = NO_TOUCH;
        sprintf(str, "fr%d", i);
        Serial1.print(str);
        Serial1.print('\n');
      }
    }
  }
  
  if( sendGyroData ) {
    my3IMU.getQ(q);
    Serial1.print("q");
    Serial1.print(q[0]);
    Serial1.print(",");
    Serial1.print(q[1]);
    Serial1.print(",");
    Serial1.print(q[2]);
    Serial1.print(",");
    Serial1.print(q[3]);
    Serial1.print("\n");
  }
  
  if(Serial1.available()) {
    cmd = Serial1.read();
    if(cmd=='v') {
      sprintf(str, "FreeIMU library by %s, FREQ:%s, LIB_VERSION: %s, IMU: %s", FREEIMU_DEVELOPER, FREEIMU_FREQ, FREEIMU_LIB_VERSION, FREEIMU_ID);
      Serial1.print(str);
      Serial1.print('\n');
    }
    else if(cmd=='r') {
      uint8_t count = serial_busy_wait();
      for(uint8_t i=0; i<count; i++) {
        my3IMU.getRawValues(raw_values);
        sprintf(str, "%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,", raw_values[0], raw_values[1], raw_values[2], raw_values[3], raw_values[4], raw_values[5], raw_values[6], raw_values[7], raw_values[8], raw_values[9], raw_values[10]);
        Serial1.print(str);
        Serial1.print('\n');
      }
    }
    else if(cmd=='b') {
      uint8_t count = serial_busy_wait();
      for(uint8_t i=0; i<count; i++) {
        #if HAS_ITG3200()
          my3IMU.acc.readAccel(&raw_values[0], &raw_values[1], &raw_values[2]);
          my3IMU.gyro.readGyroRaw(&raw_values[3], &raw_values[4], &raw_values[5]);
        #else // MPU6050
          my3IMU.accgyro.getMotion6(&raw_values[0], &raw_values[1], &raw_values[2], &raw_values[3], &raw_values[4], &raw_values[5]);
        #endif
        writeArr(raw_values, 6, sizeof(int)); // writes accelerometer and gyro values
        #if IS_9DOM()
          my3IMU.magn.getValues(&raw_values[0], &raw_values[1], &raw_values[2]);
          writeArr(raw_values, 3, sizeof(int));
        #endif
        Serial1.println();
      }
    }
    /*else if(cmd == 'q') {
      uint8_t count = serial_busy_wait();
      for(uint8_t i=0; i<count; i++) {
        my3IMU.getQ(q);
        serialPrintFloatArr(q, 4);
        Serial1.println("");
      }
    }*/
      else if(cmd == 'y') { // yaw pitch roll
        my3IMU.getYawPitchRoll(ypr);
        Serial1.print('y');
        Serial1.print(ypr[0]);
        Serial1.print(",");
        Serial1.print(ypr[1]);
        Serial1.print(",");
        Serial1.print(ypr[2]);
        Serial1.println("");
      }
    #ifndef CALIBRATION_H
    else if(cmd == 'c') {
      const uint8_t eepromsize = sizeof(float) * 6 + sizeof(int) * 6;
      while(Serial1.available() < eepromsize) ; // wait until all calibration data are received
      EEPROM.write(FREEIMU_EEPROM_BASE, FREEIMU_EEPROM_SIGNATURE);
      for(uint8_t i = 1; i<(eepromsize + 1); i++) {
        EEPROM.write(FREEIMU_EEPROM_BASE + i, (char) Serial1.read());
      }
      my3IMU.calLoad(); // reload calibration
    }
    else if(cmd == 'x') {
      EEPROM.write(FREEIMU_EEPROM_BASE, 0); // reset signature
      my3IMU.calLoad(); // reload calibration
    }
    #endif
    else if(cmd == 'C') { // check calibration values
      Serial1.print("acc offset: ");
      Serial1.print(my3IMU.acc_off_x);
      Serial1.print(",");
      Serial1.print(my3IMU.acc_off_y);
      Serial1.print(",");
      Serial1.print(my3IMU.acc_off_z);
      Serial1.print("\n");
      
      Serial1.print("magn offset: ");
      Serial1.print(my3IMU.magn_off_x);
      Serial1.print(",");
      Serial1.print(my3IMU.magn_off_y);
      Serial1.print(",");
      Serial1.print(my3IMU.magn_off_z);
      Serial1.print("\n");
      
      Serial1.print("acc scale: ");
      Serial1.print(my3IMU.acc_scale_x);
      Serial1.print(",");
      Serial1.print(my3IMU.acc_scale_y);
      Serial1.print(",");
      Serial1.print(my3IMU.acc_scale_z);
      Serial1.print("\n");
      
      Serial1.print("magn scale: ");
      Serial1.print(my3IMU.magn_scale_x);
      Serial1.print(",");
      Serial1.print(my3IMU.magn_scale_y);
      Serial1.print(",");
      Serial1.print(my3IMU.magn_scale_z);
      Serial1.print("\n");
    }
    else if(cmd == 'd') { // debugging outputs
      while(1) {
        my3IMU.getRawValues(raw_values);
        sprintf(str, "%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,", raw_values[0], raw_values[1], raw_values[2], raw_values[3], raw_values[4], raw_values[5], raw_values[6], raw_values[7], raw_values[8], raw_values[9], raw_values[10]);
        Serial1.print(str);
        Serial1.print('\n');
        my3IMU.getQ(q);
        serialPrintFloatArr(q, 4);
        Serial1.println("");
        my3IMU.getYawPitchRoll(ypr);
        Serial1.print("Yaw: ");
        Serial1.print(ypr[0]);
        Serial1.print(" Pitch: ");
        Serial1.print(ypr[1]);
        Serial1.print(" Roll: ");
        Serial1.print(ypr[2]);
        Serial1.println("");
      }
    }
    else if(cmd == 'q') { // quaternion
      sendGyroData = true;
    }
    else if(cmd == 'e') { // quaternion
      sendGyroData = false;
    }
  }
  
  delay ( 33 );
  
}

char serial_busy_wait() {
  while(!Serial1.available()) {
    ; // do nothing until ready
  }
  return Serial1.read();
}

const int EEPROM_MIN_ADDR = 0;
const int EEPROM_MAX_ADDR = 511;

void eeprom_serial_dump_column() {
  // counter
  int i;

  // byte read from eeprom
  byte b;

  // buffer used by sprintf
  char buf[10];

  for (i = EEPROM_MIN_ADDR; i <= EEPROM_MAX_ADDR; i++) {
    b = EEPROM.read(i);
    sprintf(buf, "%03X: %02X", i, b);
    Serial1.println(buf);
  }
}
