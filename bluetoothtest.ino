#include <SoftwareSerial.h> // 블루투스 통신을 위한 SoftwareSerial 라이브러리를 불러온다.
#include "Wire.h"
#define BT_RXD 4
#define BT_TXD 5
SoftwareSerial bluetooth(BT_RXD, BT_TXD);
  byte buffer[1024]; // 데이터를 수신 받을 버퍼
  int bufferPosition; // 버퍼에 데이타를 저장할 때 기록할 위치
  boolean temp = 0;
  
void setup(){
  bluetooth.begin(9600); 
  Serial.begin(9600); 
  
  bufferPosition = 0; // 버퍼 위치 초기화
}

void loop(){
  if (bluetooth.available()){ // 블루투스로 데이터 수신
    byte data = bluetooth.read(); // 수신 받은 데이터 저장
    Serial.write(data); // 수신된 데이터 시리얼 모니터로 출력
    buffer[bufferPosition++] = data; // 수신 받은 데이터를 버퍼에 저장
  
    if(data == '입질 감지 시작'){  // 블루투스를 통해 '1' 이 들어오면
      if(temp == 0){  // LED가 꺼있을 경우 LED를 켭니다.
        Serial.print("낚싯대를 거치해주세요");
        bluetooth.print("낚싯대를 거치해주세요");
        temp = 1;
      if(temp == 1){
        delay(2000);
        Serial.print("거치가 완료되었습니다!");
        bluetooth.print("거치가 완료되었습니다!");
        temp = 2;
      }
      if(temp == 2){
        delay(2000);
        Serial.print("입질감지를 시작합니다");
        bluetooth.print("입질감지를 시작합니다");
        temp == 3;
      }
      if(temp == 3){
        delay(3000);
        Serial.print("입질이 감지되었습니다!!");
        bluetooth.print("입질이 감지되었습니다!!");
      }
      }else{          // LED가 켜져있을 경우 LED를 끕니다.
        delay(2000);
        temp = 0;
      }
    }
      
    if(data == '\n'){ // 문자열 종료 표시
      buffer[bufferPosition] = '\0';
      bufferPosition = 0;
    }  
  }
}
