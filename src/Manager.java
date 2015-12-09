import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.util.BitVector;

import java.net.InetAddress;

/**
 * Created by Ruben on 09-12-2015.
 */
public class Manager {

    public static void main(String[] args) {
        InetAddress addr = null;
        int port_plant = 5502;
        int port_plc = 5501;

        try {
            addr = InetAddress.getByName(args[0]);

            TCPMasterConnection con_plant = tcpMestre.openConnection(addr, port_plant);
            //TCPMasterConnection con_plc = tcpMestre.openConnection(addr,port_plc);
            con_plant.connect();

            //READING THE INPUTS (SENSORS)
            BitVector sensorInfo = tcpMestre.readSensors(con_plant, 0, 136);
            System.out.println("Inputs Status = " + sensorInfo);
            System.out.println("Sensor Warehouse Out: " + sensorInfo.getBit(0));
            //READING THE COILS (MOTORS)
            BitVector actuatorInfo = tcpMestre.readCoils(con_plant, 0, 189);
            System.out.println("Coils Status = " + actuatorInfo);
            for (int i = 128; i <= 136; i++) {
                System.out.println("Coil " + i + "            : " + actuatorInfo.getBit(i));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void receivePlantInfo() {

    }
}
