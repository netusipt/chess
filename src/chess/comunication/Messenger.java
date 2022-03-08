package chess.comunication;

import chess.utils.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Messenger {

    private ServerSocket server;
    private Socket client;
    private BufferedReader input;
    private BufferedWriter output;

    private Logger logger;
    private MessageHandler messageHandler;

    public Messenger(Logger logger, MessageHandler messageHandler)
    {
        this.logger = logger;
        this.messageHandler = messageHandler;
    }

    public boolean start(int port) {
        try {
            this.server = new ServerSocket(port);
            this.client = this.server.accept();

            this.output = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
            this.input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

            this.messageHandler.handle(input.readLine());

        } catch (IOException e) {
            this.logger.error(e.getMessage());
            return false;
        }

        this.logger.log("Server connected!");
        return true;
    }

    public boolean stop() {
        try {
            this.input.close();
            this.output.close();
            this.client.close();
            this.server.close();

        } catch (IOException e) {
            this.logger.error(e.getMessage());
            return false;
        }

        this.logger.log("Server disconnected!");
        return true;
    }
}
