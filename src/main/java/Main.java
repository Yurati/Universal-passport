import blockchain.chain.Block;
import blockchain.exceptions.TransactionsSizeException;
import p2p.PeerInfo;
import p2p.units.CustomsAgent;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PeerInfo peerInfo = new PeerInfo(9090);
        CustomsAgent customsAgent = new CustomsAgent(10, peerInfo);
        try {
            customsAgent.getBlockchain().addBlockToBlockchain(new Block(null, new ArrayList<>()));
        } catch (TransactionsSizeException e) {
            e.printStackTrace();
        }
        new Thread(customsAgent::mainLoop).start();
    }
}
