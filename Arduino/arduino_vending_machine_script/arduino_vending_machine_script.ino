#include <SPI.h>
#include <MFRC522.h>
#include <SoftwareSerial.h> 
#define PIN_ESPIRAL_A 7
#define PIN_ESPIRAL_B 8
#define PIN_ESPIRAL_C 4

MFRC522 mfrc522(10,9);//RFID
SoftwareSerial BT(5,6);//Bluetooth TX,RX

/*
 Abstracción sobre el Objeto espiral.
 Representa cada motor conectado a un relay
 y sus comportamientos
*/

class Espiral{
  private: 
    int pinMotor;
  public:
    Espiral(int pinMotor){
      this->pinMotor=pinMotor;
    }

    void setup(){
      pinMode(this->pinMotor,OUTPUT);  
      this->apagar();
    }
    
    void encender(){
      digitalWrite(this->pinMotor,LOW);
    }

    void apagar(){
      digitalWrite(this->pinMotor,HIGH);
    }

    void soltarProducto(){
      Serial.println("Soltando producto...");
      this->encender();
      Serial.println("encendiendo");
      delay(1000);
      this->apagar();
      Serial.println("apagando");
      
    }
};

/*
 Abstracción sobre el Objeto RFIDScanner.
 Representa a un scanner RFID  de verdad
 sus comportamientos y atributos
*/
class RFIDScanner{
  
  public: 
    static void setup(){
      SPI.begin();        //Iniciamos el Bus SPI
      mfrc522.PCD_Init(); // Iniciamos  el MFRC522
    }

    static boolean cardDetectada(){
      return  mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial();
    }

    static void limpiarLectura(){
      mfrc522.PICC_HaltA();
    }

    static int lecturaSize(){
      return mfrc522.uid.size;
    }
    
    static void enviarUIDCARD(){
          // Enviamos serialemente su UID
          Serial.println("Tarjeta detectada!");
          BT.print("x");
          for (byte i = 0; i < RFIDScanner::lecturaSize(); i++) {
            BT.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
            BT.print(mfrc522.uid.uidByte[i], HEX);   
          } 
          BT.print("y");
          // Terminamos la lectura de la tarjeta  actual
          RFIDScanner::limpiarLectura();           
    }
};

/*
 Abstracción sobre el Objeto BluetoothDevice.
 encapsula el objeto bluetooth de la librería
 Software serial
*/
class BluetoothDevice{
  public:
    static void setup(){
      BT.begin(9600);
    }

    static boolean hayPaquete(){
       return BT.available();
    }

    static char obtenerPaquete(){
      char paquete=BT.read();
      return paquete;  
    }
};

Espiral espiralA(PIN_ESPIRAL_A);
Espiral espiralB(PIN_ESPIRAL_B);

void setup() {
  //siempre escuchando por 9600, 
  //el arduino mantendrá una comunicación serial siempre con
  //el celular, que este le estará informando sobre la elección
  //del usuario
  Serial.begin(9600);
  //iniciar RFID
  RFIDScanner::setup();
  //Bluetooth
  BluetoothDevice::setup();
  //iniciar Espirales
  espiralA.setup();
  espiralB.setup();
}

void loop() {
  //escanear tarjeta cuando sea necesario
  scanearRFIDCard();
  //Entregar producto dependiendo del comando recibido
  entregarProducto();
}

//Escanear RFID Card
/*
espera que se detecte una tarjeta RFID 
que esté cerca del lector para poder así 
usar el método de envío del UID de la tarjeta 
RFID a la interfaz.
*/
void scanearRFIDCard(){
  if ( RFIDScanner::cardDetectada()) {
    RFIDScanner::enviarUIDCARD();
  }
}


//soltar producto
/*
una rutina que tiene como función recibir manipular un motor 
(espiral con productos) dependiendo del comando recibido 
desde la interfaz (dispositivo android) para entregar el producto
 que el usuario ha escogido
*/
void entregarProducto(){

  if(BluetoothDevice::hayPaquete()){
    switch(BluetoothDevice::obtenerPaquete()){
      case 'a':
       Serial.println("Espiral-A");
       espiralA.soltarProducto();
      break;
      case 'b':
       Serial.println("Espiral-B");
       espiralB.soltarProducto();  
      break;  
    }   
  }
 
}
