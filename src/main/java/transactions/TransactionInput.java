package transactions;

public class TransactionInput {
    public String id;
    public TransactionOutput UTXO;

    public TransactionInput(String id) {
        this.id = id;
    }
}
