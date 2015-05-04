/*
Arduino driver for Android app remote control.
This sketch listens to instructions on the Serial port
then activates motors as required
Then sends back confirmation to the app

Motor shield DFRduino 1A based on L239B
Pin 	        Function
Digital 4 	Motor 2 Direction control
Digital 5 	Motor 2 PWM control
Digital 6 	Motor 1 PWM control
Digital 7 	Motor 1 Direction control
 */
 
 #include<SoftwareSerial.h>

int const PWMA = 6; 
int const PWMB = 5; 
int const dirA = 7; 
int const dirB = 4;
int led=13;
int switcher=0;
int tempPin=0;
float temp=0;

int Tx = 8;
int Rx = 9;

SoftwareSerial bluetooth(Rx,Tx);
char character;
String content="";

void setup() {
  pinMode(PWMA, OUTPUT);
  pinMode(PWMB, OUTPUT);
  pinMode(dirA, OUTPUT);  
  pinMode(dirB, OUTPUT);
  pinMode(led,OUTPUT);
  
  //initial set up straight forward, no speed
  digitalWrite(dirA, HIGH);
  digitalWrite(dirB, HIGH);
  analogWrite(PWMA, 0);
  analogWrite(PWMB, 0);
  Serial.begin(9600);
  bluetooth.begin(115200);
  //bluetooth.print("$$$");
  delay(100);
  bluetooth.begin(9600); 
}

void loop() {
  
   while(Serial.available()>0) {
      character = Serial.read();
      content.concat(character);
      bluetooth.println(content);
  }
  if (content != "") {
    Serial.println(content);
    //bluetooth.println(content);
    
  }
    content="";
        
        //digitalWrite(led,HIGH);
  
  
  
  // see if there's incoming serial data:
  if (bluetooth.available() > 0) {
    // read the oldest byte in the serial buffer:
    int incomingByte = bluetooth.read();
    
    // action depending on the instruction
    // as well as sending a confirmation back to the app
    switch (incomingByte) {
      case 'H':
       switcher=1;
         //digitalWrite(led,HIGH);
        break;
      case 'L':
        switcher=0;
        //digitalWrite(led,LOW);
        break;
      default: 
        // if nothing matches, do nothing
        break;
    }
    
  }
  
  
    if(switcher==1){
        //temp = analogRead(tempPin);
         //temp = temp * 0.48828125;
         //bluetooth.println(temp);
         digitalWrite(led,HIGH);
    }
    if(switcher==0){
      
      digitalWrite(led,LOW);
    
    }
}


