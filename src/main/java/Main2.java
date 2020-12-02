import blockchain.exceptions.InvalidRightsException;
import blockchain.exceptions.TransactionsSizeException;
import p2p.PeerInfo;
import p2p.units.CustomsAgent;

import java.util.LinkedList;

public class Main2 {
    public static void main(String[] args) {
        PeerInfo peerInfo = new PeerInfo(9091);
        CustomsAgent customsAgent = new CustomsAgent(10, peerInfo);
        new Thread(new Runnable() {
            @Override
            public void run() {
                customsAgent.mainLoop();
            }
        }).start();
        PeerInfo peerInfo2 = new PeerInfo(9090);
        try {
            customsAgent.getBlockchain().mineBlock(new LinkedList<>());
            customsAgent.connectAndSendBlock(peerInfo2, customsAgent.getBlockchain().getLastBlock(), true);
        } catch (TransactionsSizeException | InvalidRightsException e) {
            e.printStackTrace();
        }
    }
}
