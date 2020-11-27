package p2p;

import p2p.handlers.BaseHandler;
import p2p.socket.SimpleSocket;
import p2p.socket.SocketInterface;
import p2p.util.LoggerUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class Node {

    private static final int SOCKET_TIMEOUT = 2000; // milliseconds
    private PeerInfo peerInfo;
    private int maxPeers;
    private Hashtable<String, PeerInfo> peers;
    private Hashtable<String, BaseHandler> handlers;
    private RouterInterface router;
    private boolean shutdown;

    public Node(int maxPeers, PeerInfo info) {
        if (info.getHost() == null)
            info.setHost(getHostname());
        if (info.getId() == null)
            info.setId(info.getHost() + ":" + info.getPort());

        this.peerInfo = info;
        this.maxPeers = maxPeers;

        this.peers = new Hashtable<>();
        this.handlers = new Hashtable<>();
        this.router = null;

        this.shutdown = false;
    }

    public Node(int port) {
        this(0, new PeerInfo(port));
    }

    /*
     * Attempt to determine the name or IP address of the machine
     * this node is running on.
     */
    private String getHostname() {
        String host = "";
        try {
            Socket s = new Socket("www.google.com", 80);
            host = s.getLocalAddress().getHostAddress();
        } catch (UnknownHostException e) {
            LoggerUtil.getLogger().warning("Could not determine host: " + e);
        } catch (IOException e) {
            LoggerUtil.getLogger().warning(e.toString());
        }

        LoggerUtil.getLogger().config("Determined host: " + host);
        return host;
    }

    public ServerSocket makeServerSocket(int port) throws IOException {
        return makeServerSocket(port, 5);
    }

    public ServerSocket makeServerSocket(int port, int backlog)
            throws IOException {
        ServerSocket s = new ServerSocket(port, backlog);
        s.setReuseAddress(true);
        return s;
    }

    public List<PeerMessage> sendToPeer(String peerid, String msgtype,
                                        String msgdata, boolean waitreply) {
        PeerInfo pd = null;
        if (router != null)
            pd = router.route(peerid);
        if (pd == null) {
            LoggerUtil.getLogger().severe(
                    String.format("Unable to route %s to %s", msgtype, peerid));
            return new ArrayList<>();
        }

        return connectAndSend(pd, msgtype, msgdata, waitreply);
    }

    public List<PeerMessage> connectAndSend(PeerInfo pd, String msgtype,
                                            String msgdata, boolean waitreply) {
        List<PeerMessage> msgreply = new ArrayList<>();
        try {
            PeerConnection peerConnection = new PeerConnection(pd);
            PeerMessage toSend = new PeerMessage(msgtype, msgdata);
            peerConnection.sendData(toSend);
            LoggerUtil.getLogger().fine("Sent " + toSend + "/" + peerConnection);

            if (waitreply) {
                PeerMessage onereply = peerConnection.recvData();
                while (onereply != null) {
                    msgreply.add(onereply);
                    LoggerUtil.getLogger().fine("Got reply " + onereply);
                    onereply = peerConnection.recvData();
                }
            }

            peerConnection.close();
        } catch (IOException e) {
            LoggerUtil.getLogger().warning("Error: " + e + "/"
                    + pd + "/" + msgtype);
        }
        return msgreply;
    }

    public void mainLoop() {
        try {
            ServerSocket s = makeServerSocket(peerInfo.getPort());
            s.setSoTimeout(SOCKET_TIMEOUT);

            while (!shutdown) {
                LoggerUtil.getLogger().fine("Listening...");
                try {
                    Socket clientsock = s.accept();
                    clientsock.setSoTimeout(0);

                    PeerHandler ph = new PeerHandler(clientsock);
                    ph.start();
                } catch (SocketTimeoutException e) {
                    LoggerUtil.getLogger().fine("" + e);
                    continue;
                }
            }

            s.close();
        } catch (SocketException e) {
            LoggerUtil.getLogger().severe("Stopping main loop (SocketExc): " + e);
        } catch (IOException e) {
            LoggerUtil.getLogger().severe("Stopping main loop (IOExc): " + e);
        }

        shutdown = true;
    }

    public void addHandler(String msgtype, BaseHandler handler) {
        handlers.put(msgtype, handler);
    }

    public void addRouter(RouterInterface router) {
        this.router = router;
    }

    public boolean addPeer(PeerInfo pd) {
        return addPeer(pd.getId(), pd);
    }

    public boolean addPeer(String key, PeerInfo pd) {
        if ((maxPeers == 0 || peers.size() < maxPeers) &&
                !peers.containsKey(key)) {
            peers.put(key, pd);
            return true;
        }
        return false;
    }

    public PeerInfo getPeer(String key) {
        return peers.get(key);
    }

    public PeerInfo removePeer(String key) {
        return peers.remove(key);
    }

    public Set<String> getPeerKeys() {
        return peers.keySet();
    }

    public int getNumberOfPeers() {
        return peers.size();
    }

    public int getMaxPeers() {
        return maxPeers;
    }

    public boolean maxPeersReached() {
        return maxPeers > 0 && peers.size() == maxPeers;
    }

    public String getId() {
        return peerInfo.getId();
    }

    public String getHost() {
        return peerInfo.getHost();
    }

    public int getPort() {
        return peerInfo.getPort();
    }

    private class PeerHandler extends Thread {
        private final SocketInterface socket;

        public PeerHandler(Socket socket) throws IOException {
            this.socket = new SimpleSocket(socket);
        }

        public void run() {
            LoggerUtil.getLogger().fine("New PeerHandler: " + socket);

            PeerConnection peerConnection = new PeerConnection(null, socket);
            PeerMessage peerMessage = peerConnection.recvData();
            if (!handlers.containsKey(peerMessage.getMsgType())) {
                LoggerUtil.getLogger().fine("Not handled: " + peerMessage);
            } else {
                LoggerUtil.getLogger().finer("Handling: " + peerMessage);
                handlers.get(peerMessage.getMsgType()).handleMessage(peerConnection,
                        peerMessage);
            }
            LoggerUtil.getLogger().fine("Disconnecting incoming: " + peerConnection);
            peerConnection.close();
        }
    }

}
