#include "HX711.h" //HX711로드셀 엠프 관련함수 호출
#define calibration_factor -7050.0 // 로드셀 스케일 값 선언
#define DOUT  4 //엠프 데이터 아웃 핀 넘버 선언
#define CLK  3  //엠프 클락 핀 넘버 
HX711 scale(DOUT, CLK); //엠프 핀 선언 
void setup() {
  Serial.begin(9600);  // 시리얼 통신 개방
  scale.set_scale(calibration_factor);  //스케일 지정 
  scale.tare();  //스케일 설정  
}
void loop() {
  while( scale.get_units() * 0.453592 < 2 ){
//    Serial.print("입질 감지중.. ");
//    Serial.print(scale.get_units() * 0.453592, 1);  //무제 출력 
//    Serial.print(" kg"); //단위
//    Serial.println();  
  }
  Serial.println("입질이 감지되었습니다!!");
  Serial.end();
}
