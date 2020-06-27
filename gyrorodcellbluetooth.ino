#include "I2Cdev.h"
#include "MPU6050_6Axis_MotionApps20.h"
#include "HX711.h" //HX711로드셀 엠프 관련함수 호출
#include <SoftwareSerial.h>
#define calibration_factor -7050.0 // 로드셀 스케일 값 선언
#define DOUT  4 //엠프 데이터 아웃 핀 넘버 선언
#define CLK  3  //엠프 클락 핀 넘버 
#define BT_RXD 4
#define BT_TXD 5
int bufferPosition;
byte buffer[1024];
/*char str[]="낚싯대를 거치대에 거치해주세요.";
char str2[]="거치중";
char str3[]="낚싯대 거치 완료!";
char str4[]="입질감지를 시작합니다.";
char str5[]="입질 감지중.. ";
char str6[]="입질 감지되었습니다!!";*/

SoftwareSerial bluetooth(BT_RXD, BT_TXD);
HX711 scale(DOUT, CLK); //엠프 핀 선언 

#if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
#include "Wire.h"
#endif

MPU6050 mpu;

#define OUTPUT_READABLE_YAWPITCHROLL
#define INTERRUPT_PIN 2  // use pin 2 on Arduino Uno & most boards
bool blinkState = false;
// MPU control/status vars
bool dmpReady = false;  // set true if DMP init was successful
uint8_t mpuIntStatus;   // holds actual interrupt status byte from MPU
uint8_t devStatus;      // return status after each device operation (0 = success, !0 = error)
uint16_t packetSize;    // expected DMP packet size (default is 42 bytes)
uint16_t fifoCount;     // count of all bytes currently in FIFO
uint8_t fifoBuffer[64]; // FIFO storage buffer

// orientation/motion vars
Quaternion q;           // [w, x, y, z]         quaternion container
VectorInt16 aa;         // [x, y, z]            accel sensor measurements
VectorInt16 aaReal;     // [x, y, z]            gravity-free accel sensor measurements
VectorInt16 aaWorld;    // [x, y, z]            world-frame accel sensor measurements
VectorFloat gravity;    // [x, y, z]            gravity vector
float ypr[3];           // [yaw, pitch, roll]   yaw/pitch/roll container and gravity vect

volatile bool mpuInterrupt = false;     // indicates whether MPU interrupt pin has gone high
void dmpDataReady() {
    mpuInterrupt = true;
}

void setup() {
    // join I2C bus (I2Cdev library doesn't do this automatically)
    #if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
        Wire.begin();
        Wire.setClock(400000); // 400kHz I2C clock. Comment this line if having compilation difficulties
    #elif I2CDEV_IMPLEMENTATION == I2CDEV_BUILTIN_FASTWIRE
        Fastwire::setup(400, true);
    #endif

    Serial.begin(9600);
    bluetooth.begin(9600);
    bufferPosition=0;
    while (!Serial); // wait for Leonardo enumeration, others continue immediately
    
    scale.set_scale(calibration_factor);  //스케일 지정 
    scale.tare();  //스케일 설정  
    
    // initialize device
    //Serial.println(F("Initializing I2C devices..."));
    mpu.initialize();
    pinMode(INTERRUPT_PIN, INPUT);

    // verify connection
    //Serial.println(F("Testing device connections..."));
    //Serial.println(mpu.testConnection() ? F("MPU6050 connection successful") : F("MPU6050 connection failed"));

    // wait for ready
    Serial.println(F("입질감지모드를 시작하겠습니까?? (y/s): "));
    while (Serial.available() && Serial.read()); // empty buffer
    while (!Serial.available());                 // wait for data
    while (Serial.available() && Serial.read()); // empty buffer again
    /*if(bluetooth.available(){
    //Serial.println("\n낚싯대를 거치대에 거치해주세요.");   
      char data = bluetooth.read();
      Serial.write(data);
      buffer[bufferPosition]='\0';
      buffer=(byte)str;
      bluetooth.write(buffer,bufferPosition);
    }*/

    // load and configure the DMP
    devStatus = mpu.dmpInitialize();

    // supply your own gyro offsets here, scaled for min sensitivity
    mpu.setXGyroOffset(220);
    mpu.setYGyroOffset(76);
    mpu.setZGyroOffset(-85);
    mpu.setZAccelOffset(1788); // 1688 factory default for my test chip

    // make sure it worked (returns 0 if so)
    if (devStatus == 0) {
        // turn on the DMP, now that it's ready
        //Serial.println(F("Enabling DMP..."));
        mpu.setDMPEnabled(true);

        // enable Arduino interrupt detection
        attachInterrupt(digitalPinToInterrupt(INTERRUPT_PIN), dmpDataReady, RISING);
        mpuIntStatus = mpu.getIntStatus();

        // set our DMP Ready flag so the main loop() function knows it's okay to use it
        dmpReady = true;

        // get expected DMP packet size for later comparison
        packetSize = mpu.dmpGetFIFOPacketSize();
    } else {
        // ERROR!
        // 1 = initial memory load failed
        // 2 = DMP configuration updates failed
        // (if it's going to break, usually the code will be 1)
        Serial.print(F("DMP Initialization failed (code "));
        Serial.print(devStatus);
        Serial.println(F(")"));
    }
}
void deferment() {
  // if programming failed, don't try to do anything
    if (!dmpReady) return;

    // reset interrupt flag and get INT_STATUS byte
    mpuInterrupt = false;
    mpuIntStatus = mpu.getIntStatus();

    // get current FIFO count
    fifoCount = mpu.getFIFOCount();

    // check for overflow (this should never happen unless our code is too inefficient)
    if ((mpuIntStatus & 0x10) || fifoCount == 1024) {
        // reset so we can continue cleanly
        mpu.resetFIFO();
        //Serial.println(F("FIFO overflow!"));

    // otherwise, check for DMP data ready interrupt (this should happen frequently)
    } else if (mpuIntStatus & 0x02) {
        // wait for correct available data length, should be a VERY short wait
        while (fifoCount < packetSize) fifoCount = mpu.getFIFOCount();

        // read a packet from FIFO
        mpu.getFIFOBytes(fifoBuffer, packetSize);
        
        // track FIFO count here in case there is > 1 packet available
        // (this lets us immediately read more without waiting for an interrupt)
        fifoCount -= packetSize;        

        #ifdef OUTPUT_READABLE_YAWPITCHROLL
            // display Euler angles in degrees
            mpu.dmpGetQuaternion(&q, fifoBuffer);
            mpu.dmpGetGravity(&gravity, &q);
            mpu.dmpGetYawPitchRoll(ypr, &q, &gravity);
            Serial.print("거치중\t");
            
            buffer[bufferPosition]='\0';
            buffer[0]='b';
            bluetooth.write(buffer,bufferPosition);
            bufferPosition=0;
            
            Serial.println(ypr[1] * 180/M_PI);
        #endif
    }
}

void detection() {
    Serial.print("입질 감지중.. ");

    buffer[bufferPosition]='\0';
    buffer[0]='e';
    bluetooth.write(buffer,bufferPosition);
    bufferPosition=0;
        
    Serial.print(scale.get_units() * 0.453592, 1);  //무제 출력 
    Serial.print("kg"); //단위
    Serial.println();
}

int flag = 0;
long time = millis();

void loop() {
   if(bluetooth.available()){
      char data = bluetooth.read();
      Serial.write(data);
      buffer[bufferPosition++]=data;

      if(data == '\n'){
        
        if( ypr[1] * 180/M_PI > -62 && scale.get_units() * 0.453592 < 5 && flag == 0 ){
        
          buffer[bufferPosition]='\0';
          buffer[0]='a';
          bluetooth.write(buffer,bufferPosition);
          bufferPosition=0;
        
          deferment();   
        }
        else if( time + 30000 < millis() && ypr[1] * 180/M_PI < -62 && scale.get_units() * 0.453592 < 5 && flag == 0 ){
          flag = 1;
          Serial.println("낚싯대 거치 완료!");  
        
          buffer[bufferPosition]='\0';
          buffer[0]='c';
          bluetooth.write(buffer,bufferPosition);
          bufferPosition=0;
        }
        else if( ypr[1] * 180/M_PI < -62 && scale.get_units() * 0.453592 < 5 && flag == 1 ){
          flag = 2;
          delay(1000);
          Serial.println("");
          Serial.println("입질감지를 시작합니다.");  
        
          buffer[bufferPosition]='\0';
          buffer[0]='d';
          bluetooth.write(buffer,bufferPosition);
          bufferPosition=0;
         
          Serial.println();
          delay(3000);   
        }

        else if( ypr[1] * 180/M_PI < -62 && scale.get_units() * 0.453592 < 5 && flag == 2 ){
          detection();  
        }
      
        else if( ypr[1] * 180/M_PI < -62 && scale.get_units() * 0.453592 > 5 && flag == 2 ){
   
          Serial.println("\n입질이 감지되었습니다!!");

          buffer[bufferPosition]='\0';
          buffer[0]='f';
          bluetooth.write(buffer,bufferPosition);
          bufferPosition=0;  
        
        }  
      }
   }
}
