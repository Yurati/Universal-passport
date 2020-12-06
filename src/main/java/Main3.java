import blockchain.exceptions.InvalidRightsException;
import p2p.PeerInfo;
import p2p.units.EmbassyAgent;
import p2p.util.Const;
import p2p.util.Converter;

import java.io.IOException;

public class Main3 {
    public static void main(String[] args) {
        PeerInfo peerInfo = new PeerInfo(9091);
        EmbassyAgent embassyAgent = new EmbassyAgent(10, peerInfo);
        new Thread(embassyAgent::mainLoop).start();
        PeerInfo peerInfo2 = new PeerInfo(9090);
        try {
            embassyAgent.connectAndSend(peerInfo2, Const.ADD_PEAR, Converter.serialize(peerInfo), true);
        } catch (InvalidRightsException | IOException e) {
            e.printStackTrace();
        }
    }
}
