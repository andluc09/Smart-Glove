/**
 Acquire the touches event of the Gravity Glove.
 by: Jonathan Besuchet
 date: November 14, 2013
 license: Public domain

This sketch uses the anlog to digital converter (ADC) of the Arduino to measure
the voltage across a force sensitive resistor. For the purpose of the "Gravity
Glove" project on www.instructables.com, we use 5 force sensitive resistors (one
for each finger), the corresponding Arduino pins are A0-A4.
The voltage to measure goes from 5V (no force on the resistor) to 1V (full
pressure). As the ADC is 10 bits based, 5V corresponds to 1024 whereas 1V
corresponds to 1024/5V * 1V = 205. We are intersested only to detect and send
the touch events, so we introduce a threasold value "touchThr" above which we
consider the state "NO_TOUCH" and below which we consider the state "TOUCH".
*/

char str[256];

// Touch sensors variables
int sensorValue = 0;
int touchThr = 920;
int sensorPins[] = {A0, A1, A2, A3, A4};
typedef enum { NO_TOUCH, TOUCH } FState;
FState fingerState[] = {NO_TOUCH, NO_TOUCH, NO_TOUCH, NO_TOUCH, NO_TOUCH};

void setup() {
  
  Serial.begin(115200);  // Begin the serial port at 115200bps

}


void loop() {
  
  // read the value from the touch sensor:
  for ( char i=0; i<5; i++ ) {
    sensorValue = analogRead( sensorPins[i] ); // read the ADC for given port.
    switch ( fingerState[i] ) {
    case NO_TOUCH:
      if(sensorValue < touchThr) { // finger press
        fingerState[i] = TOUCH;
        sprintf(str, "fp%d", i);
        Serial.print(str);
        Serial.print('\n');
      }
      break;
      
    case TOUCH:
      if(sensorValue >= touchThr) { // finger release
        fingerState[i] = NO_TOUCH;
        sprintf(str, "fr%d", i);
        Serial.print(str);
        Serial.print('\n');
      }
    }
  }
  
  delay ( 33 );
  
}
