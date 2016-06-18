const int Schalter1 = 2;
const int Schalter2 = 4;
long Startzeit = 0;
unsigned int Stopzeit = 0;
const int Sen = 12;
const int Sen2 = 11;
int Senp;
int Senp2;
const int Laut = 13;
double Endzeit;
unsigned int Sch1 = 0;
unsigned int Sch2 = 0;
double Geschwindigkeit;
const int ROT = 7;
const int GRUEN = 8;
void setup() {
  Serial.begin(300);
  pinMode(Schalter1, INPUT);
  pinMode(Schalter2, INPUT);
  pinMode(ROT, OUTPUT);
  pinMode(GRUEN, OUTPUT);
  pinMode(Sen, INPUT);
  pinMode(Laut, OUTPUT);
  pinMode(Sen2, OUTPUT);
}

void loop() {
  Sch1 = digitalRead(Schalter1);
  Sch2 = digitalRead(Schalter2);
  Senp = digitalRead(Sen);
  Senp2 = digitalRead(Sen2);
  if (Sch1 == HIGH) {
    Startzeit = millis();
  }
  delay(10);
  Stopzeit = (millis() - Startzeit) / 100;
  Serial.println(Geschwindigkeit);
  Serial.println(Stopzeit);
  if (Sch2 == HIGH) {
    Endzeit = Stopzeit;
    Geschwindigkeit = 50 / (Endzeit / 10) * 60 / 100;
    delay(100);
  }

if (Sch2 == HIGH){
  if (Geschwindigkeit > 5)
  { digitalWrite (ROT , HIGH);
    delay(1000);
    digitalWrite (ROT , LOW);
  }
  else
  { digitalWrite (GRUEN , HIGH);
    delay(1000);
    digitalWrite (GRUEN , LOW);
  }}
  
 if(Senp==HIGH && Senp2==HIGH)
 {tone(Laut,500,100);
 delay(100);
 noTone(Laut);}
 else
 {noTone(Laut);} 
  
  
  }


