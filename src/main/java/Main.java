import p2p.PeerInfo;
import p2p.network.BlockchainNode;

public class Main {
    public static void main(String[] args) {
        PeerInfo peerInfo = new PeerInfo(9090);
        BlockchainNode blockchainNode = new BlockchainNode(10, peerInfo);
        new Thread(new Runnable() {
            @Override
            public void run() {
                blockchainNode.mainLoop();
            }
        }).start();
    }
}
