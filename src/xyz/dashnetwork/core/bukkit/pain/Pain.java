package xyz.dashnetwork.core.bukkit.pain;

import xyz.dashnetwork.core.utils.SignatureUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Pain {

    public static void main(String[] args) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();

                Pain pain = new Pain("broadcast");
                DataOutputStream output = pain.getOutput();

                output.write(0);
                output.writeUTF(message);

                pain.close();
            } catch (Exception exception) {}
        }
    }

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
