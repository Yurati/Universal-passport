package blockchain.exceptions;

public class TransactionsSizeException extends Throwable {
    public TransactionsSizeException(String msg) {
        super(msg);
    }
}
