package seshat.seshatlabel.print;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.zebra.sdk.comm.BluetoothConnectionInsecure;
import com.zebra.sdk.comm.Connection;

import java.net.ConnectException;

import seshat.seshatlabel.R;
import seshat.seshatlabel.models.LabelModel;


public class BluetoothPrinter {

    private Context context;
    private static String PRINTER_MAC_ADDRESS;

    public BluetoothPrinter(Context context) {
        this.context = context;
        BluetoothPrinter.PRINTER_MAC_ADDRESS = this.context.getResources().getString(R.string.printerMac);
    }

    public void print(final LabelModel labelModel) {
        Thread t = new Thread(new Runnable() {
            String label = labelModel.getLabel();
            String project = labelModel.getProject().toUpperCase();
            String year = labelModel.getYear();
            public void run() {
                try {
                    // Instantiate insecure connection for given Bluetooth&reg; MAC Address.
                    Connection thePrinterConn = new BluetoothConnectionInsecure(BluetoothPrinter.PRINTER_MAC_ADDRESS);

                    // Initialize
                    Looper.prepare();

                    // Open the connection - physical connection is established here.
                    thePrinterConn.open();

                    // This example prints "This is a ZPL test." near the top of the label.
                    String zplData = "^XA\n" +
                            "^CF0,32\n" +
                            "^FO210,50^FD" + label + "^FS\n" +
                            "^CF0,32\n" +
                            "^FO210,105^FD" + year + "^FS\n" +
                            "^FO210,160^FD" + project + "^FS\n" +
                            "^FO40,30^BQN,2,6^FD" + label + "\n" +
                            "^XZ";

                    // Send the data to printer as a byte array.
                    thePrinterConn.write(zplData.getBytes());

                    // Make sure the data got to the printer before closing the connection
                    Thread.sleep(500);

                    // Close the insecure connection to release resources.
                    thePrinterConn.close();

                    Looper.myLooper().quit();
                } catch (Exception e) {
                    // Handle communications error here.
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void smallPrint(final LabelModel labelModel) {
        new Thread(new Runnable() {
            String label = labelModel.getLabel();
            String project = labelModel.getProject().toUpperCase();
            String year = labelModel.getYear();
            public void run() {
                try {
                    // Instantiate insecure connection for given Bluetooth&reg; MAC Address.
                    Connection thePrinterConn = new BluetoothConnectionInsecure(BluetoothPrinter.PRINTER_MAC_ADDRESS);

                    // Initialize
                    Looper.prepare();

                    // Open the connection - physical connection is established here.
                    thePrinterConn.open();

                    // This example prints "This is a ZPL test." near the top of the label.
                    String zplData = "^XA\n" +
                            "^CF0,35\n" +
                            "^FO140,30^FD" + label + "^FS\n" +
                            "^CF0,25\n" +
                            "^FO140,55^FD" + year + "^FS\n" +
                            "^FO140,80^FD" + project + "^FS\n" +
                            "^FO20,10^BQN,2,4^FD" + label + "\n" +
                            "^XZ";

                    // Send the data to printer as a byte array.
                    thePrinterConn.write(zplData.getBytes());

                    // Make sure the data got to the printer before closing the connection
                    Thread.sleep(500);

                    // Close the insecure connection to release resources.
                    thePrinterConn.close();

                    Looper.myLooper().quit();
                } catch (Exception e) {
                    // Handle communications error here.
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
