import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.*;
import java.util.Enumeration;

public class SerialTest implements SerialPortEventListener {
    SerialPort serialPort;
    /* Serial Port */
    private static String PORT_NAME[] = {"/dev/cu.usbmodem1411", "/dev/cu.usbmodem1421"};
    /*
     * A BufferedReader which will be fed by a InputStreamReader
     * converting the bytes into characters
     * making the displayed results codepage independent
     */
    private BufferedReader input;
    /* Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /* Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;
    private static OutputStream output;

    public void initialize() throws UnsatisfiedLinkError {

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAME.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAME) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /*
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /*
     * "Handle an event on the serial port. Read the data and print it."
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                System.out.println(inputLine);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }

    public static void main(String[] args) {

        SerialTest main = new SerialTest();
        main.initialize();
        //How do threads work? : https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html
        Thread t = new Thread() {
            public void run() {
//Below will keep the program listening for entered milliseconds.
                int seconds = 20;
                try {
                    Thread.sleep(seconds*10000);
                } catch (InterruptedException ie) {}

            }
        };
        t.start();
        System.out.println("Started");
        System.out.println(output);
//        do {
//            System.out.print(output);
//        }
//        while (!(output.equals("ARDUINO 20")));
//        main.close();
    }
}
