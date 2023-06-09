package src.main.java.pl.stepniewski.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ClientService {
    public static final int PORT = 6767;
    public static final String LOCALHOST = "localhost";
    private final Client client;
    PrintWriter printWriter;
    BufferedReader bufferedReader;

    public ClientService(Client client) {
        this.client = client;
        startClient();
    }

    private void startClient() {
        try ( Socket socket = connectToServer()
        ) {
            startBufferedStreams(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startBufferedStreams(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    private static Socket connectToServer() {
        Socket socket = null;
        try{
            socket = new Socket(LOCALHOST, PORT);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return socket;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}
