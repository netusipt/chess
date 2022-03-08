
package chess.utils;


import java.io.BufferedWriter;

public class Logger {

    private BufferedWriter fileWriter;

    public Logger(BufferedWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    //TODO: timestamp
    //TODO: file log
    public void error(String message) {
        System.err.println(message);
    }

    public void log(String message) {
        System.out.println(message);
    }
    
}
