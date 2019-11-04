package ru.lokinCompany.LokEngine.Tools.Compression;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPCompression {

    public static byte[] compress(final String str) throws IOException {
        if ((str == null) || (str.length() == 0)) {
            return null;
        }
        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(str.getBytes());
        gzip.flush();
        gzip.close();
        return obj.toByteArray();
    }

    public static String decompress(final byte[] compressed) throws IOException {
        if ((compressed == null) || (compressed.length == 0)) return "";
        if (!isCompressed(compressed)) return new String(compressed);

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new GZIPInputStream(
                                new ByteArrayInputStream(compressed)
                        )
                )
        );

        return IOUtils.toString(bufferedReader);
    }

    public static boolean isCompressed(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }

}
