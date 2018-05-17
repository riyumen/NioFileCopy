package com.baizhi.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioFileCopy {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        FileInputStream in = new FileInputStream("e:/a/1.zip");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        FileOutputStream out = new FileOutputStream("e:/1.zip");
        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();
        while (true) {
            int readnum = inChannel.read(buffer);
            if (readnum == -1) break;
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        out.close();
        in.close();
    }
}

class test2 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        FileChannel in = FileChannel.open(Paths.get("e:/a/1.zip"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("e:/2.zip"), StandardOpenOption.WRITE
                , StandardOpenOption.READ, StandardOpenOption.CREATE);
        MappedByteBuffer inmap = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
        MappedByteBuffer outmap = out.map(FileChannel.MapMode.READ_WRITE, 0, in.size());
        byte[] b = new byte[inmap.limit()];
        inmap.get(b);
        outmap.put(b);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        out.close();
        in.close();

    }
}