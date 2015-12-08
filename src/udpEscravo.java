import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class udpEscravo {

    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(54321);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Listening...");
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            System.out.println("RECEIVED: " + sentence);

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }

    public static void decodeMessage (String message, char trans, int no, int pIni, int pFin, int n) {

    }
}