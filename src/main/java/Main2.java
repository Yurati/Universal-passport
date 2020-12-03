import blockchain.exceptions.InvalidRightsException;
import blockchain.exceptions.TransactionsSizeException;
import p2p.PeerInfo;
import p2p.units.ProvincialOfficeAgent;
import p2p.util.DataProvider;

public class Main2 {
    public static void main(String[] args) {
        PeerInfo peerInfo = new PeerInfo(9091);
        ProvincialOfficeAgent provincialOfficeAgent = new ProvincialOfficeAgent(10, peerInfo);
        new Thread(provincialOfficeAgent::mainLoop).start();
        PeerInfo peerInfo2 = new PeerInfo(9090);
        try {
            DataProvider dataProvider = new DataProvider();
            provincialOfficeAgent.createAddPassportTransaction(dataProvider.getPassportDefaultData());
            provincialOfficeAgent.addTransactionsToBlockchain();
            provincialOfficeAgent.connectAndSendBlock(peerInfo2, provincialOfficeAgent.getBlockchain().getLastBlock()
                    , true);
        } catch (TransactionsSizeException | InvalidRightsException e) {
            e.printStackTrace();
        }
    }
}
