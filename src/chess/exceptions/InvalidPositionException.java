
package chess.exceptions;


public class InvalidPositionException extends RuntimeException {
    
    public InvalidPositionException(String message) {
        super(message);
    }

    public InvalidPositionException() {}
    
}
