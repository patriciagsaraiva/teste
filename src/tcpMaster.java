import java.net.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleRegister;
import net.wimpi.modbus.util.*;

// CONFIGURAÇÕES DE RUN:
// VM options: -Dnet.wimpi.modbus.debug=true
// Program arguments: localhost  <-----------------------------------------------!!!!!!!!!!!!!!!!!!!!

public class tcpMaster {

    public static void main(String[] args) {

        InetAddress addr = null;
        int port_plant = 5502;
        int port_plc = 5501;
        int repeat = 20; //a loop for repeating the transaction
        int aux = 0, init = 0;
        int teste = 0;
        for (int i = 0; i < repeat; i++) {
            try {
                if(init > 0)
                    Thread.sleep(500);
            } catch(InterruptedException ie) {}

            try {
                if(init == 0)
                    init++;
                //1. Setup the parameters
                addr = InetAddress.getByName(args[0]);

                //2. Open the connections
                TCPMasterConnection con_plant = tcpMaster.openConnection(addr, port_plant);

                con_plant.connect();

                //READING THE COILS (MOTORS)
                BitVector res_coils = tcpMaster.readCoils(con_plant, 0, 0);
                System.out.println("Coils Status = " + res_coils);

                //READING THE INPUTS (SENSORS)
                BitVector res_sensors = tcpMaster.readSensors(con_plant, 0, 136);
                System.out.println("Inputs Status = " + res_sensors);
                System.out.println("Sensor Warehouse Out: " + res_sensors.getBit(0));
                System.out.println("Sensor Warehouse Out: " + res_sensors.getBit(2));

                //READ THE R VARIABLE (PIECES)
                /*if(aux == 0) {
                    tcpMaster.sendPiece(con_plant, 0, 6);
                    aux = 1;
                }
                else if (teste == 1) {
                    tcpMaster.sendPiece(con_plant, 0, 2);
                    aux = 1;
                    teste = 0;
                }
                else if(aux == 1) {
                    teste = 1;
                    aux = 2;
                    tcpMaster.sendPiece(con_plant, 0, 0);
                }*/

                ReadMultipleRegistersResponse res_variables = tcpMaster.readVariables(con_plant, 0, 1);
                System.out.println("Piece: " + res_variables.getRegisterValue(0));


                con_plant.close();

                //READING THE VARIABLES (PLC)
                //TCPMasterConnection con_plc = tcpMaster.openConnection(addr,port_plc);
                //con_plc.connect();

                //ReadMultipleRegistersResponse res_variables = tcpMaster.readVariables(con_plc, 0, 2, 2);
                //System.out.println("Variables Status = " + res_variables.getRegisterValue(0) + ", " + res_variables.getRegisterValue(1));

                //con_plc.close();
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
            //req.setUnitID(0);

            trans.setRequest(req);
            trans.execute();
            res = (ReadMultipleRegistersResponse) trans.getResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static ReadMultipleRegistersResponse readVariables (TCPMasterConnection con, int ref, int count, int id) {
        ReadMultipleRegistersResponse res = null;
        try {
            ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
            ReadMultipleRegistersRequest req = new ReadMultipleRegistersRequest(ref, count);
            req.setUnitID(id);

            trans.setRequest(req);
            trans.execute();
            res = (ReadMultipleRegistersResponse) trans.getResponse();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static void writeRegister (TCPMasterConnection con, int ref, int value) {
        try {
            Register r = new SimpleRegister(0);
            r.setValue(value);

            ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
            WriteSingleRegisterRequest req = new WriteSingleRegisterRequest(ref, r);
            trans.setRequest(req);
            trans.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendPiece(TCPMasterConnection con, int ref, int value) {
        writeRegister(con, ref, value);
    }
}




