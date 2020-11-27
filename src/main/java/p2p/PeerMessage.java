package p2p;

import p2p.socket.SocketInterface;

import java.io.IOException;

public class PeerMessage {
    private byte[] type;
    private byte[] data;

    public PeerMessage(byte[] type, byte[] data) {
        this.type = (byte[]) type.clone();
        this.data = (byte[]) data.clone();
    }

    public PeerMessage(String type, String data) {
        this(type.getBytes(), data.getBytes());
    }


    public PeerMessage(String type, byte[] data) {
        this(type.getBytes(), data);
    }

    public PeerMessage(SocketInterface s) throws IOException {
        type = new byte[4];
        byte[] thelen = new byte[4]; // for reading length of message data
        if (s.read(type) != 4)
            throw new IOException("EOF in PeerMessage constructor: type");
        if (s.read(thelen) != 4)
            throw new IOException("EOF in PeerMessage constructor: thelen");

        int len = byteArrayToInt(thelen);
        data = new byte[len];

        if (s.read(data) != len)
            throw new IOException("EOF in PeerMessage constructor: " +
                    "Unexpected message data length");
    }

    public static byte[] intToByteArray(final int integer) {
        int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer : integer)) / 8;
        byte[] byteArray = new byte[4];

        for (int n = 0; n < byteNum; n++)
            byteArray[3 - n] = (byte) (integer >>> (n * 8));

        return (byteArray);
    }

    public static int byteArrayToInt(byte[] byteArray) {
        int integer = 0;
        for (int n = 0; n < 4; n++) {
            integer = (integer << 8) | (((int) byteArray[n]) & 0xff);
        }
        return integer;
    }

    public String getMsgType() {
        return new String(type);
    }

    public byte[] getMsgTypeBytes() {
        return (byte[]) data.clone();
    }

    public String getMsgData() {
        return new String(data);
    }

    public byte[] getMsgDataBytes() {
        return (byte[]) data.clone();
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[4 + 4 + data.length];
        byte[] lenbytes = intToByteArray(data.length);

        System.arraycopy(type, 0, bytes, 0, 4);
        System.arraycopy(lenbytes, 0, bytes, 4, 4);
        System.arraycopy(data, 0, bytes, 8, data.length);

        return bytes;
    }

    public String toString() {
        return "PeerMessage[" + getMsgType() + ":" + getMsgData() + "]";
    }
}
