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
        } catch (Exception ex) { ex.printStackTrace(); }
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

    public static void writeRegister (TCPMasterConnection con, int ref, int value, int slaveId) {
        try {
            Register r1 = new SimpleRegister(value);
            Register[] R = new Register[]{r1};

            ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
            WriteMultipleRegistersRequest req = new WriteMultipleRegistersRequest(ref, R);
            req.setUnitID(slaveId);
            trans.setRequest(req);
            trans.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendPiece(TCPMasterConnection con_plant, TCPMasterConnection con_plc, int type, int cmd) {
        writeRegister(con_plc, 50, type, 2);
        writeRegister(con_plc, 0, cmd, 2);

        writeRegister(con_plant, 0, type);
    }

    public static void resetPiece(TCPMasterConnection con, int ref, int value) {
        writeRegister(con, ref, value);
    }

    public static void loadIntoWarehouse(TCPMasterConnection con, int ref, boolean value) {

        try {
            ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
            WriteCoilRequest req = new WriteCoilRequest(ref, value);
            trans.setRequest(req);
            trans.execute();
        } catch (Exception ex) {ex.printStackTrace();}
    }

    public static BitVector readFromPLC(TCPMasterConnection con, int slaveId) {
        ReadCoilsResponse res = null;
        try {
            ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
            ReadCoilsRequest req = new ReadCoilsRequest(0, 1);
            req.setUnitID(slaveId);
            trans.setRequest(req);
            trans.execute();

            res = (ReadCoilsResponse) trans.getResponse();
        } catch (Exception ex) { ex.printStackTrace(); }

        return res.getCoils();
    }
}




