#include <string.h>      
 // Selecciona el pin de entrada analoga a leer.
 int pin = 15;
 // variable para guardar el valor de frecuencia.
 int frecC = 0;
 int stressC = 0;
 int pressureC = 0;
 // variable para las unidadeds
 String frecUnit = "bpm";
 String stressUnit = "mm";
 String pressureUnit = "mmHg";
 // arreglo de chars para envio final del dato del sensor.
 String frecArray[2] = {"", ""};
 String stressArray[2] = {"",""};
 String pressureArray[2] = {"",""};
 // variable temporal de conteo
 int i = 0;
 // preparacion del proceso
 void setup() { 
   // Abre puerto serial y lo configura a 9600 bps
   Serial.begin(9600);
   // se fija la unidad de trabajo del sensor
   frecArray[1] = frecUnit;
   stressArray[1] = stressUnit;
   pressureArray[1] = pressureUnit;
 }
 // ejecuta el procesamiento del sensor
 void loop() {
   // se fijan valores aleatorios
   frecC=analogRead(pin);
//   frecC = random(70,190);
   stressC = random(1,5);
   pressureC = random(80,150);
   // se transforma el dato a string
   frecArray[0] = String(frecC);
   stressArray[0] = String(stressC);
   pressureArray[0] = String(pressureC);
   // Envia los datos uno por uno del arreglo del sensor por puerto serial
   for (i=0; i<2; i++){
     // imprime el elemento del arreglo por el puerto serial
     Serial.print(frecArray[i]);
     // espacio para diferenciar elementos en el arreglo
     if (i < 1){
       Serial.print("\t");
     }
   }
   Serial.println("");
   for (i=0; i<2; i++){
     // imprime el elemento del arreglo por el puerto serial
     Serial.print(stressArray[i]);
     // espacio para diferenciar elementos en el arreglo
     if (i < 1){
       Serial.print("\t");      
     }
   }
   Serial.println("");
   for (i=0; i<2; i++){
     // imprime el elemento del arreglo por el puerto serial
     Serial.print(pressureArray[i]);
     // espacio para diferenciar elementos en el arreglo
     if (i < 1){
       Serial.print("\t");      
     }
   }
  Serial.println("");
   // espera 1 segundo para repetir el procedimiento
   delay(1000);
 }