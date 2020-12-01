import p2p.PeerInfo;
import p2p.units.CustomsAgent;
import p2p.util.Const;

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
        customsAgent.getBlockchain().mineBlock(new LinkedList<>());
        customsAgent.connectAndSendBlock(peerInfo2, Const.NEW_BLOCK, customsAgent.getBlockchain().getBlockchain().get(customsAgent.getBlockchain().getBlockchain().size() - 1), true);
    }
}
