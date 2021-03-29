package xyz.dashnetwork.core.bukkit.pain;

import xyz.dashnetwork.core.utils.SignatureUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Pain {

    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    public Pain(String channel) {
        try {
            socket = new Socket("panel.dashnetwork.xyz", 25454);
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());

            long time = System.currentTimeMillis();

            output.writeLong(time);
            output.writeUTF(channel);

            byte[] signed = SignatureUtils.sign((time + channel).getBytes());

            output.write(signed);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public DataOutputStream getOutput() {
        return output;
    }

    public DataInputStream getInput() {
        return input;
    }

    public void close() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
