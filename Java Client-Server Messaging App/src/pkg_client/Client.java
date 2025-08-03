package pkg_client;

/**
 *
 * @author samet
 */
import pkg_server.Frm_Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    // her clientın bir soketi olamlı
    private Socket socket;
    // gönderilecek alınacak bilgileri byte dizisine çevirmek için
    private DataInputStream sInput;
    //private DataInputStream sInput;
    private DataOutputStream sOutput;

    // server adresi 
    private String server;
    // port numarası
    private int port;
    boolean isListening = false;

    //yapıcı metod
    public Client(String server, int port) {

        this.server = server;
        this.port = port;

    }

    // client başlatma
    public boolean ConnectToServer() {

        try {
            // Client Soket nesnesi
            socket = new Socket(this.server, this.port);

            sInput = new DataInputStream(socket.getInputStream());
            sOutput = new DataOutputStream(socket.getOutputStream());
            this.Listen();
            System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());

        } catch (Exception err) {
            System.out.println("Error connecting to server: " + err);
        }
        return true;
    }

    public void Listen() {
        if (isListening) {
            return;
        }
        this.isListening = true;
        Thread t1 = new Thread(this);
        t1.start();

    }

    @Override
    public void run() {
        try {
            while (this.isListening) {
                byte[] messageByte = new byte[1024];
                int bytesRead = sInput.read(messageByte);
                String message = new String(messageByte, 0, bytesRead);
                //System.out.println(message);
              
                Frm_Client.lst_messagesFromServer_model.addElement(message);

            }

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //mesaj gönderme fonksiyonu
    public void SendMessage(byte[] msg) {
        try {
            //mesaj göndermek için daha once oluşmuii output nesnesi kullanılıyor
            /* msg[msg.length-3]=0x6;
            msg[msg.length-2]=0xD;
            msg[msg.length-1]=0xA;*/
            msg[msg.length - 1] = 0x14;
            sOutput.write(msg);
        } catch (IOException err) {
            System.out.println("Exception writing to server: " + err);
        }
    }

    //clientı kapatan fonksiyon
    public void disconnect() {
        try {
            //tüm nesneleri kapatıyoruz
            if (sInput != null) {
                sInput.close();
            }
            if (sOutput != null) {
                sOutput.close();
            }

            if (socket != null) {
                socket.close();
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

    }

}
