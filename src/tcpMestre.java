import java.net.*;
import java.io.*;
import java.util.*;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.util.*;

// CONFIGURAÇÕES DE RUN:
// VM options: -Dnet.wimpi.modbus.debug=true
// Program arguments: localhost:5555 0 4 3

public class tcpMestre {

    public static void main(String[] args) {
        TCPMasterConnection con = null;
        ModbusTCPTransaction trans = null;
        ReadInputDiscretesRequest req_in = null;
        ReadCoilsRequest req_coils = null;
        ReadInputDiscretesResponse res_in = null;
        ReadCoilsResponse res_coils = null;

        InetAddress addr = null; //the slave's address
        int port = Modbus.DEFAULT_PORT;
        int ref = 0; //the reference; offset where to start reading from
        int count = 0; //the number of DI's to read
        int repeat = 1; //a loop for repeating the transaction

        for (int i = 0; i < 100; i++) {
            try {
                //sending the actual Thread of execution to sleep X milliseconds
                Thread.sleep(500);
            } catch(InterruptedException ie) {}
            try {
                //1. Setup the parameters
                if (args.length < 3) {
                    System.exit(1);
                } else {
                    try {
                        String astr = args[0];
                        int idx = astr.indexOf(':');
                        if (idx > 0) {
                            port = Integer.parseInt(astr.substring(idx + 1));
                            astr = astr.substring(0, idx);
                        }
                        addr = InetAddress.getByName(astr);
                        ref = Integer.decode(args[1]).intValue();
                        count = Integer.decode(args[2]).intValue();
                        if (args.length == 4) {
                            repeat = Integer.parseInt(args[3]);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.exit(1);
                    }
                }

                //2. Open the connection
                con = new TCPMasterConnection(addr);
                con.setPort(port);
                con.connect();


                //3. Prepare the request
                req_in = new ReadInputDiscretesRequest(ref, count);
                req_coils = new ReadCoilsRequest(0, 6);
                //4. Prepare the transaction
                trans = new ModbusTCPTransaction(con);
                trans.setRequest(req_coils);

                int k = 0;
                do {
                    trans.execute();
                    res_coils = (ReadCoilsResponse) trans.getResponse();
                    System.out.println("Coil Status = " + res_coils.getCoils());
                    k++;
                } while (k < repeat);

                //5. Execute the transaction repeat times
                trans.setRequest(req_in);
                k = 0;
                do {
                    trans.execute();
                    res_in = (ReadInputDiscretesResponse) trans.getResponse();
                    System.out.println("Inputs Status = " + res_in.getDiscretes().toString());
                    k++;
                } while (k < repeat);
                //------------------------------
                //trans = new ModbusTCPTransaction(con);


                //6. Close the connection
                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

