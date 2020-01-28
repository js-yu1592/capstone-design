int led = 9; //LED연결
 
void setup() {
  pinMode(led, OUTPUT); //LED 핀 출력으로 설정
}
 
void loop() {
  digitalWrite(led,HIGH); //LED ON
  delay(1000);            //1초 대기
  digitalWrite(led,LOW);  //LED OFF
  delay(1000);
}
