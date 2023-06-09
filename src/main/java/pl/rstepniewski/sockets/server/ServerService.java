package pl.rstepniewski.sockets.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by rafal on 09.06.2023
 *
 * @author : rafal
 * @date : 09.06.2023
 * @project : Battleship
 */
public class ServerService {
    private static final int PORT = 6767;
    private final Server server;
    PrintWriter printWriter;
    BufferedReader bufferedReader;

    public ServerService(Server server) {
        this.server = server;
        startServer();
    }

    private void startServer(){
        try (Socket socket = startSocket())
        {
            startBufferedStreams(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Socket startSocket() throws IOException{
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket clientSocket = serverSocket.accept();
        return clientSocket;
    }

    private void startBufferedStreams(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}
