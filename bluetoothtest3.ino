#include <SoftwareSerial.h>
#define calibration_factor -7050.0
#include "HX711.h" //HX711로드셀 엠프 관련함수 호출
#define DOUT  4 //엠프 데이터 아웃 핀 넘버 선언
#define CLK  3  //엠프 클락 핀 넘버 
HX711 scale(DOUT, CLK); //엠프 핀 선언 


 
int Tx=6; //전송
int Rx=7; //수신
int bufferPosition; 
byte buffer[1024];

SoftwareSerial btSerial(Tx, Rx);

void setup() 
{    
  Serial.begin(9600);
  btSerial.begin(9600);
  bufferPosition = 0; 

  scale.set_scale(calibration_factor);  //스케일 지정 
  scale.tare();  //스케일 설정  
}

void detection() {
    Serial.print("입질 감지중.. ");
    Serial.print(scale.get_units() * 0.453592, 1);  //무제 출력 
    Serial.print("kg"); //단위
    Serial.println();
}
int temp = 0;
void loop()
{
  if (btSerial.available()) { 
    // 수신 받은 데이터 저장
    char data = btSerial.read(); 
    // 수신된 데이터 시리얼 모니터로 출력
    Serial.write(data);
     
        // 수신 받은 데이터를 버퍼에 저장
    buffer[bufferPosition++] = data;
 
    // 문자열 종료 표시
    //if (data=='\n') { 

      // buffer[bufferPosition] = '\0';
       
       while(1){
        detection();
     
        if(scale.get_units() * 0.453592 < -100){
         
         //buffer[bufferPosition++] = scale.get_units() * 0.453592;
         //btSerial.write(buffer,bufferPosition);
         Serial.write(data);
         
         Serial.print("입질이 감지되었습니다~!~!!");
         
         //Serial.print("");
         break;
        }
       }
       
       btSerial.write(buffer,bufferPosition);
       //btSerial.write(Serial.read());
       bufferPosition = 0;

    //}
  }  
}
