import java.net.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.util.*;

// CONFIGURAÇÕES DE RUN:
// VM options: -Dnet.wimpi.modbus.debug=true
// Program arguments: localhost  <-----------------------------------------------!!!!!!!!!!!!!!!!!!!!

public class tcpMestre {

    public static void main(String[] args) {

        InetAddress addr = null;
        int port_plant = 5502;
        int port_plc = 5501;
        int repeat = 60; //a loop for repeating the transaction

        for (int i = 0; i < repeat; i++) {
            try {
                Thread.sleep(250);
            } catch(InterruptedException ie) {}

            try {
                //1. Setup the parameters
                addr = InetAddress.getByName(args[0]);

                //2. Open the connections
                TCPMasterConnection con_plant = tcpMestre.openConnection(addr,port_plant);
                TCPMasterConnection con_plc = tcpMestre.openConnection(addr,port_plc);

                con_plant.connect();

                //3. Prepare the requests




                //READING THE COILS (MOTORS)
                BitVector res_coils = tcpMestre.readCoils(con_plant, 0, 6);
                System.out.println("Coils Status = " + res_coils);

                //READING THE INPUTS (SENSORS)
                BitVector res_sensors = tcpMestre.readSensors(con_plant, 0, 3);
                System.out.println("Inputs Status = " + res_sensors);
                con_plant.close();

                //READING THE VARIABLES (PLC)
                ReadMultipleRegistersResponse res_variables = tcpMestre.readVariables(con_plc, 0, 2);
                System.out.println("Variables Status = " + res_variables.getRegisterValue(0) + ", " + res_variables.getRegisterValue(1));
                con_plc.connect();

                con_plc.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static TCPMasterConnection openConnection (InetAddress address, int port) {
        TCPMasterConnection con = new TCPMasterConnection(address);
        con.setPort(port);
        return con;
    }

    public static BitVector readCoils (TCPMasterConnection con, int ref, int count) {
        ReadCoilsResponse res = null;
        try {
            ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
            ReadCoilsRequest req = new ReadCoilsRequest(ref, count);
            trans.setRequest(req);
            trans.execute();
            res = (ReadCoilsResponse) trans.getResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res.getCoils();
    }

    public static BitVector readSensors (TCPMasterConnection con, int ref, int count) {
        ReadInputDiscretesResponse res = null;
        try {
            ModbusTCPTransaction trans = new ModbusTCPTransaction(con);

            ReadInputDiscretesRequest req = new ReadInputDiscretesRequest(ref, count);
            trans.setRequest(req);
            trans.execute();
            res = (ReadInputDiscretesResponse) trans.getResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res.getDiscretes();
    }

    public static ReadMultipleRegistersResponse readVariables (TCPMasterConnection con, int ref, int count) {
        ReadMultipleRegistersResponse res = null;
        try {
            ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
            ReadMultipleRegistersRequest req = new ReadMultipleRegistersRequest(ref, count);
            req.setUnitID(2);

            trans.setRequest(req);
            trans.execute();
            res = (ReadMultipleRegistersResponse) trans.getResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }
}

