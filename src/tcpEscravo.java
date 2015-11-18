import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.procimg.*;
import net.wimpi.modbus.ModbusCoupler;

import java.net.InetAddress;

public class tcpEscravo {

    public static void main(String[] args) {
        ModbusTCPListener listener = null;
        SimpleProcessImage spi = null;
        int port = Modbus.DEFAULT_PORT;
        InetAddress addr = null;

        try {
            //1. Set port number from commandline parameter
            if (args.length < 2) {
                System.exit(1);
            } else {
                try {
                    String astrAddr = args[0];
                    addr = InetAddress.getByName(astrAddr);
                    port = Integer.decode(args[1]).intValue();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }

            //2. Prepare a process image
            spi = new SimpleProcessImage();
            spi.addDigitalOut(new SimpleDigitalOut(true));
            spi.addDigitalOut(new SimpleDigitalOut(false));
            spi.addDigitalIn(new SimpleDigitalIn(false));
            spi.addDigitalIn(new SimpleDigitalIn(true));
            spi.addDigitalIn(new SimpleDigitalIn(false));
            spi.addDigitalIn(new SimpleDigitalIn(true));
            spi.addRegister(new SimpleRegister(251));
            spi.addInputRegister(new SimpleInputRegister(45));

            //3. Set the image on the coupler
            ModbusCoupler.getReference().setProcessImage(spi);
            ModbusCoupler.getReference().setMaster(false);
            ModbusCoupler.getReference().setUnitID(15);

            //4. Create a listener with 3 threads in pool
            listener = new ModbusTCPListener(3);
            listener.setPort(port);
            listener.setAddress(addr);
            listener.start();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
