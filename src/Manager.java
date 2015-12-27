import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.util.BitVector;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

// VM OPTIONS:          -Dnet.wimpi.modbus.debug=true
// PROGRAM ARGUMENTS:   localhost

public class Manager {

    public static ArrayList<Order> orderList = new ArrayList<Order>();

    public static void main(String[] args) throws Exception {
        InetAddress addr;
        int port_plant = 5502, port_plc = 5501;
        TCPMasterConnection con_plant = null, con_plc = null;
        int init = -1; // init controls new pieces sent to the system. -1 for "system start", "0 for no recent piece", "1 for piece sending"
        int[] unload = {0,0,0};
        int orders = 0;
        long startTime = 0; // timer to control time difference between sending piece and it appearing on sensor

        // setup UDP communications
        Manager.listen();

        // estabilish connections to PLANT and PLC
        try {
            addr = InetAddress.getByName(args[0]);
            //CONNECTING TO THE PLANT
            con_plant = tcpMaster.openConnection(addr, port_plant);
            con_plant.connect();
            //CONNECTING TO THE PLC - ISAGRAF
            con_plc = tcpMaster.openConnection(addr, port_plc);
            //con_plc.connect();
        } catch (Exception ex) {ex.printStackTrace();}


        /*--------------------------------- MAIN CYCLE ---------------------------------*/
        while(true) {

            if(orderList.size() > orders) {
                System.out.println("New order: ");
                Order.printOrder(orderList.get(orderList.size() - 1));
                orders++;
            }

            try {
                if (init != -1)  // init = -1 is the first time the system runs
                    Thread.sleep(100);
            } catch (InterruptedException ie) {ie.printStackTrace();}

            //READING THE INPUTS (SENSORS)
            BitVector sensors = Manager.receivePlantInfo(con_plant, "in");
            //READING THE COILS (MOTORS)
            BitVector coils = Manager.receivePlantInfo(con_plant, "out");

            if (checkIfFree(sensors, coils, init)) {
                int[] write = new int[2];
                init++;
                tcpMaster.sendPiece(con_plant, 0, 2);
                write[0] = 2; write[1] = 8;
                //tcpMaster.updateFields(con_plc, 0, write);
                startTime = System.currentTimeMillis();
            }
            if(init == 1 && (startTime == 0 || System.currentTimeMillis()-startTime > 3500)){
                init--;
                tcpMaster.sendPiece(con_plant, 0, 0);
            }

            unload = checkForLoad(sensors, unload);
            if(unload[2] != 0)
                orderList.add(loadPieceOrder(unload[2]));

            if(init == -1) {
                init = 0;
                Manager.generateWindow();
            }

        }
    }

    public static void listen(){
        udpEscravo slave = new udpEscravo();
        Thread t = new Thread(slave);
        t.start();
    }
    public static void generateWindow() {
        GUI window = new GUI();
        Thread t = new Thread(window);
        t.start();
    }

    // Receive info from sfs plant. "info" decides if from sensors - "in" or coils - "out"
    private static BitVector receivePlantInfo(TCPMasterConnection con, String type) {
        int inputSensors = 136;
        int outputCoils = 189;
        BitVector res = null;

        if (type == "in")
            res = tcpMaster.readSensors(con, 0, inputSensors);
        else if(type == "out")
            res = tcpMaster.readCoils(con, 0, outputCoils);

        //System.out.println("Status = " + res);
        return res;
    }
    // Check if system is free to receive a piece on warehouse conveyor
    private static boolean checkIfFree(BitVector sensors, BitVector coils, int init) {
        if(init != 0)
            return false;

        boolean plantFree = !sensors.getBit(0);
        boolean entryMotor = coils.getBit(1);
        //System.out.println("Plant is free: " + plantFree);
        //System.out.println("Motor is on: " + entryMotor);

        if(plantFree && !entryMotor)
            return true;

        return false;
    }
    // Print all orders
    public static void printOrderList(List<Order> list) {
        for (int i = 0; i < list.size(); i++) {
            Order.printOrder(list.get(i));
        }
    }
    // Check for pieces in the loading conveyors
    public static int[] checkForLoad(BitVector sensors, int[] unload) {

        if(unload[2] != 0)
            unload[2] = 0;

        if(unload[0] == 1 && !sensors.getBit(123))
            unload[0] = 0;
        if(unload[1] == 1 && !sensors.getBit(134))
            unload[1] = 0;

        if(sensors.getBit(123) && unload[0] == 0) {
            unload[0] = 1;
            unload[2] = 1;
        }

        else if(sensors.getBit(134) && unload[1] == 0) {
            unload[1] = 1;
            unload[2] = 2;
        }

        return unload;
    }
    // Add a new Load Piece order to the list
    public static Order loadPieceOrder(int conveyor) {
        Order order = new Order();
        order.id = 'C';
        order.C = new Integer(conveyor);

        order.entry = System.currentTimeMillis();

        return order;
    }

}

