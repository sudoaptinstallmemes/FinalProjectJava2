//public class Blink extends JArduino {
//
//    public Blink(String port) {
//        super(port);
//    }
//
//    @Override
//    protected void setup() {
//        // initialize the digital pin as an output.
//        // Pin 13 has an LED connected on most Arduino boards:
//        pinMode(DigitalPin.PIN_12, PinMode.OUTPUT);
//    }
//
//    @Override
//    protected void loop() {
//        // set the LED on
//        digitalWrite(DigitalPin.PIN_12, DigitalState.HIGH);
//        delay(1000); // wait for a second
//        // set the LED off
//        digitalWrite(DigitalPin.PIN_12, DigitalState.LOW);
//        delay(1000); // wait for a second
//    }
//
//    public static void main(String[] args) {
//        String serialPort;
//        if (args.length == 1) {
//            serialPort = args[0];
//        } else {
//            serialPort = Serial4JArduino.selectSerialPort();
//        }
//        JArduino arduino = new Blink(serialPort);
//        arduino.runArduinoProcess();
//    }
//}