import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.util.BitVector;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

// VM OPTIONS:          -Dnet.wimpi.modbus.debug=true
// PROGRAM ARGUMENTS:   localhost

public class Manager {

    public static ArrayList<Order> orderList = new ArrayList<Order>();
    public static ArrayList<Cell> cellList = new ArrayList<Cell>();
    public static ArrayList<Machine> machineList = new ArrayList<Machine>();
    public static BitVector sensors;
    public static BitVector coils;
    public static ReadMultipleRegistersResponse inputVariables;
    public static TCPMasterConnection con_plant;
    public static TCPMasterConnection con_plc;


    public static void main(String[] args) throws Exception {
        InetAddress addr;
        int port_plant = 5502, port_plc = 5501;
        int init = -1; // init controls new pieces sent to the system. -1 for "system start", "0 for no recent piece", "1 for piece sending"
        int[] unload = {0,0,0};
        int orders = 0;
        int sendIn = 0;
        int auxLoad = 0;

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
            con_plc.connect();
        } catch (Exception ex) {ex.printStackTrace();}

        /*------------------------- SET UP THE CELLS, MACHINES -------------------------*/
        for (int i = 0; i < 4; i++) {
            cellList.add(new Cell(i+1));
        }
        // cell 1
        machineList.add(new Machine("Ma", 1));
        machineList.add(new Machine("Mc", 1));
        // cell 2
        machineList.add(new Machine("Ma", 2));
        machineList.add(new Machine("Mb", 2));
        // cell 3
        machineList.add(new Machine("Ma", 3));
        machineList.add(new Machine("Mb", 3));
        // cell 4
        machineList.add(new Machine("Ma", 4));
        machineList.add(new Machine("Mb", 4));

        /*--------------------------------- MAIN CYCLE ---------------------------------*/
        // READING SENSORS, MOTORS FROM PLANT
        Manager.sensors = receivePlantInfo(con_plant, "in");
        Manager.coils = receivePlantInfo(con_plant, "out");

        // FOLLOW THE MACHINES
        trackMachines();

        while(true) {

            if (orderList.size() > orders) {
                System.out.println("New order: ");
                Order.printOrder(orderList.get(orderList.size() - 1));
                orders++;
            }

            try {
                if (init != -1)  // init = -1 is the first time the system runs
                    Thread.sleep(100);
            } catch (InterruptedException ie) {ie.printStackTrace();}

            // READING SENSORS, MOTORS FROM PLANT
            Manager.sensors = receivePlantInfo(con_plant, "in");
            Manager.coils = receivePlantInfo(con_plant, "out");
            //BitVector teste = tcpMaster.readFromPLC(con_plc, 3);
            inputVariables = tcpMaster.readInternalVariables();

            // STARTING THE NEXT POSSIBLE ORDER
            int nextOrder = getNextOrder();
            if (nextOrder != -1)
                startOrder(Manager.orderList.get(nextOrder));

            // CHECK FOR PIECES IN LOADING CONVEYORS
            unload = checkForLoad(sensors, unload);
            if (unload[2] != 0) {
                orderList.add(loadPieceOrder(unload[2]));
            }
            if(coils.getBit(189) && auxLoad == 0) {
                auxLoad = 1;
                for (int i = 0; i < orderList.size(); i++) {
                    if(orderList.get(i).id == 'T' && orderList.get(i).state == "pendente") {
                        orderList.get(i).state = "a processar";
                        orderList.get(i).ready++;
                        orderList.get(i).processing--;
                    }
                }
            }
            if(auxLoad == 1 && !coils.getBit(189))
                auxLoad = 0;

            // CHECK TO SEND PIECES INTO THE WAREHOUSE
            if(sensors.getBit(1)) {
                tcpMaster.loadIntoWarehouse(con_plant, 4, true);
                sendIn = 1;

            }
            if(!sensors.getBit(1) && sendIn == 1) {
                sendIn = 0;
                int aux = getComplete();
                orderList.get(aux).processing--;
                orderList.get(aux).ready++;
            }

            if (init == -1) {
                init = 0;
                tcpMaster.loadIntoWarehouse(con_plant, 4, false);

                tcpMaster.resetPiece(Manager.con_plant, 0, 0);
                Manager.generateWindow();
            }
        }
    }

    public static void startOrder(Order o) {
        Thread t = new Thread(o);
        t.start();
    }
    public static void listen() {
        udpEscravo slave = new udpEscravo();
        Thread t = new Thread(slave);
        t.start();
    }
    public static void generateWindow() {
        GUI window = new GUI();
        Thread t = new Thread(window);
        t.start();
    }
    public static void trackMachines(){
        MachineFollower mf = new MachineFollower();
        Thread t = new Thread(mf);
        t.start();
    }

    private static int getComplete() {
        for (int i = 0; i < orderList.size(); i++) {
            if(orderList.get(i).processing != 0)
                return i;
        }
        return orderList.size()-1;
    }
    // Determine the next Order to go into the system
    private static int getNextOrder() {
        int n = -1;

        for (int i = 0; i < orderList.size(); i++) {
            if(orderList.get(i).state == "pendente") {
                n = i;
            }
        }

        return n;
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
    public static boolean checkIfFree(BitVector sensors, BitVector coils, int init, int cmd) {
        if(init != 0)
            return false;

        if(cmd == 1 || cmd == 2 || cmd == 3 || cmd == 4
                )
            if(sensors.getBit(8) || (sensors.getBit(3)  || coils.getBit(14)))
                return false;

        boolean AT1S = !sensors.getBit(0);
        boolean AT1Mm = coils.getBit(1);
        //System.out.println("Plant is free: " + plantFree);
        //System.out.println("Motor is on: " + entryMotor);

        if(AT1S && !AT1Mm)
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
        order.processing++;

        order.entry = System.currentTimeMillis();

        return order;
    }

}

