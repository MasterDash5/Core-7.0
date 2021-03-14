package xyz.dashnetwork.core.bungee.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class F3Utils {

    public static byte[] toBytes(String name) {
        ByteBuf buf = Unpooled.buffer();
        byte[] array = name.getBytes(StandardCharsets.UTF_8);

        writeVarInt(array.length, buf);

        buf.writeBytes(array);

        return buf.array();
    }

    private static void writeVarInt(int value, ByteBuf out) {
        int part;

        while (true) {
            part = value & 0x7F;
            value >>>= 7;

            if (value != 0)
                part |= 0x80;

            out.writeByte(part);

            if (value == 0)
                break;
        }
    }

}
