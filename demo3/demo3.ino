#include <SoftwareSerial.h>
#define calibration_factor -7050.0
#include "HX711.h" //HX711로드셀 엠프 관련함수 호출
#define DOUT  4 //엠프 데이터 아웃 핀 넘버 선언
#define CLK  3  //엠프 클락 핀 넘버 
HX711 scale(DOUT, CLK); //엠프 핀 선언 
double temp = scale.get_units() * 0.453592;


int Tx=6; //전송
int Rx=7; //수신


SoftwareSerial btSerial(Tx, Rx);

void setup() 
{    
  Serial.begin(9600);
  btSerial.begin(9600);
  scale.set_scale(calibration_factor);  //스케일 지정 
  scale.tare();  //스케일 설정  
}

void detection() {
  Serial.print("입질 감지중.. ");
  Serial.print(-1* scale.get_units() * 0.453592,1);  //무제 출력 
  Serial.print("kg"); //단위
  Serial.println();
}


void loop()
{
  if(btSerial.available()){
    if( btSerial.read()=='s'){
      while(1){
        detection();
      
        if(scale.get_units() * 0.453592 < -1){
          btSerial.println("입질이 감지되었습니다!");
          delay(5000);
          btSerial.print(-1* scale.get_units() * 0.453592);
          btSerial.println("kg"); 
          break; 
        }
      }
    }
  }
} 
