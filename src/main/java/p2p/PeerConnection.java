package p2p;

import p2p.socket.SimpleSocket;
import p2p.socket.SocketInterface;
import p2p.util.LoggerUtil;

import java.io.IOException;

public class PeerConnection {
    private final PeerInfo peerInfo;
    private SocketInterface socket;

    public PeerConnection(PeerInfo info)
            throws IOException {
        peerInfo = info;
        socket = new SimpleSocket(peerInfo.getHost(),
                peerInfo.getPort());
    }

    public PeerConnection(PeerInfo info, SocketInterface socket) {
        peerInfo = info;
        this.socket = socket;
    }

    public void sendData(PeerMessage msg) {
        try {
            socket.write(msg.toBytes());
        } catch (IOException e) {
            LoggerUtil.getLogger().warning("Error sending message: " + e);
        }
    }

    public PeerMessage recvData() {
        try {
            PeerMessage msg = new PeerMessage(socket);
            return msg;
        } catch (IOException e) {

            if (!e.getMessage().equals("EOF in PeerMessage constructor: type"))
                LoggerUtil.getLogger().warning("Error receiving message: " + e);
            else
                LoggerUtil.getLogger().finest("Error receiving message: " + e);
            return null;
        }
    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                LoggerUtil.getLogger().warning("Error closing: " + e);
            }
            socket = null;
        }
    }

    public PeerInfo getPeerInfo() {
        return peerInfo;
    }

    public String toString() {
        return "PeerConnection[" + peerInfo + "]";
    }

}
