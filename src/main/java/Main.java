import blockchain.units.CustomsAgent;
import p2p.PeerInfo;

public class Main {
    public static void main(String[] args) {
        PeerInfo peerInfo = new PeerInfo(9090);
        CustomsAgent customsAgent = new CustomsAgent(10, peerInfo);
        new Thread(new Runnable() {
            @Override
            public void run() {
                customsAgent.mainLoop();
            }
        }).start();
    }
}
