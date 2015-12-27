import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class udpEscravo implements Runnable {

    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(54321);
            byte[] receiveData = new byte[1024];
            byte[] sendData;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                //System.out.println("Listening...");
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());

                udpEscravo.decodeMessage(sentence);
            }
        } catch (Exception e) {}
    }

    public static void decodeMessage (String sentence /*, char trans, int no, int pIni, int pFin, int n*/) {
        Order order = new Order();

        order.id = sentence.substring(1,2).charAt(0);

        switch(order.id) {
            case 'T':
                order.NO = Integer.parseInt(sentence.substring(2, 5));
                order.PO = new Piece(Integer.parseInt(sentence.substring(5, 6)));
                order.PF = new Piece(Integer.parseInt(sentence.substring(6, 7)));
                order.n  = Integer.parseInt(sentence.substring(7, 9));
                break;
            case 'M':
                order.NO = Integer.parseInt(sentence.substring(2, 5));
                order.PB = new Piece(Integer.parseInt(sentence.substring(5, 6)));
                order.PC = new Piece(Integer.parseInt(sentence.substring(6, 7)));
                order.n  = Integer.parseInt(sentence.substring(7, 9));
                break;
            case 'U':
                order.NO = Integer.parseInt(sentence.substring(2, 5));
                order.P  = new Piece(Integer.parseInt(sentence.substring(5, 6)));
                order.D  = Integer.parseInt(sentence.substring(6, 7));
                order.n  = Integer.parseInt(sentence.substring(7, 9));
                break;
        }


        order.pending = Integer.parseInt(sentence.substring(7, 9));
        order.entry = System.currentTimeMillis();

        Manager.orderList.add(order);
    }

}