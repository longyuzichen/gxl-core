package com.longyuzichen.core.code;

import com.longyuzichen.core.util.FileUtil;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import java.io.*;

/**
 * com.longyuzichen.core.util
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc Base64加密工具类
 * @date 2017-03-24 22:48
 */
public class Base64 {

    private static final int END_OF_INPUT = -1;
    private static final int NON_BASE_64 = -1;
    private static final int NON_BASE_64_WHITESPACE = -2;
    private static final int NON_BASE_64_PADDING = -3;

    private Base64() {
    }

    protected static final byte[] base64Chars = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/',
    };
    protected static final byte[] reverseBase64Chars = new byte[0x100];

    static {
        // Fill in NON_BASE_64 for all characters to start with
        for (int i = 0; i < reverseBase64Chars.length; i++) {
            reverseBase64Chars[i] = NON_BASE_64;
        }
        // For characters that are base64Chars, adjust
        // the reverse lookup table.
        for (byte i = 0; i < base64Chars.length; i++) {
            reverseBase64Chars[base64Chars[i]] = i;
        }
        reverseBase64Chars[' '] = NON_BASE_64_WHITESPACE;
        reverseBase64Chars['\n'] = NON_BASE_64_WHITESPACE;
        reverseBase64Chars['\r'] = NON_BASE_64_WHITESPACE;
        reverseBase64Chars['\t'] = NON_BASE_64_WHITESPACE;
        reverseBase64Chars['\f'] = NON_BASE_64_WHITESPACE;
        reverseBase64Chars['='] = NON_BASE_64_PADDING;
    }

    public static final String version = "1.2";


    private static final int ACTION_GUESS = 0;
    private static final int ACTION_ENCODE = 1;
    private static final int ACTION_DECODE = 2;

    private static final int ARGUMENT_GUESS = 0;
    private static final int ARGUMENT_STRING = 1;
    private static final int ARGUMENT_FILE = 2;


    /**
     * @param string The data to encode.
     * @return An encoded String.
     * @since ostermillerutils 1.00.00
     */
    public static String encode(String string) {
        return new String(encode(string.getBytes()));
    }

    /**
     * @param string The data to encode.
     * @param enc    Character encoding to use when converting to and from bytes.
     * @return An encoded String.
     * @throws UnsupportedEncodingException if the character encoding specified is not supported.
     * @since ostermillerutils 1.00.00
     */
    public static String encode(String string, String enc) throws UnsupportedEncodingException {
        return new String(encode(string.getBytes(enc)), enc);
    }

    /**
     * Encode bytes in Base64.
     * No line breaks or other white space are inserted into the encoded data.
     *
     * @param bytes The data to encode.
     * @return Encoded bytes.
     * @since ostermillerutils 1.00.00
     */
    public static byte[] encode(byte[] bytes) {
        return encode(bytes, false);
    }

    /**
     * Encode bytes in Base64.
     *
     * @param bytes      The data to encode.
     * @param lineBreaks Whether to insert line breaks every 76 characters in the output.
     * @return Encoded bytes.
     * @since ostermillerutils 1.04.00
     */
    public static byte[] encode(byte[] bytes, boolean lineBreaks) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        int mod;
        int length = bytes.length;
        if ((mod = length % 3) != 0) {
            length += 3 - mod;
        }
        length = length * 4 / 3;
        ByteArrayOutputStream out = new ByteArrayOutputStream(length);
        try {
            encode(in, out, lineBreaks);
        } catch (IOException x) {
            throw new RuntimeException(x);
        }
        return out.toByteArray();
    }


    /**
     * Encode this file in Base64.
     *
     * @param fIn        File to be encoded.
     * @param fOut       File to which the results should be written (may be the same as fIn).
     * @param lineBreaks Whether to insert line breaks every 76 characters in the output.
     * @throws IOException if an input or output error occurs.
     * @since ostermillerutils 1.00.00
     */
    public static void encode(File fIn, File fOut, boolean lineBreaks) throws IOException {
        File temp = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(fIn));
            temp = File.createTempFile("Base64", null, null);
            out = new BufferedOutputStream(new FileOutputStream(temp));
            encode(in, out, lineBreaks);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            FileUtil.move(temp, fOut, true);
        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
            if (out != null) {
                out.flush();
                out.close();
                out = null;
            }
        }
    }


    /**
     * @param in         Stream from which to read data that needs to be encoded.
     * @param out        Stream to which to write encoded data.
     * @param lineBreaks Whether to insert line breaks every 76 characters in the output.
     * @throws IOException if there is a problem reading or writing.
     * @since ostermillerutils 1.00.00
     */
    public static void encode(InputStream in, OutputStream out, boolean lineBreaks) throws IOException {
        int[] inBuffer = new int[3];
        int lineCount = 0;

        boolean done = false;
        while (!done && (inBuffer[0] = in.read()) != END_OF_INPUT) {
            // Fill the buffer
            inBuffer[1] = in.read();
            inBuffer[2] = in.read();
            // A's: first six bits of first byte
            out.write(base64Chars[inBuffer[0] >> 2]);
            if (inBuffer[1] != END_OF_INPUT) {
                // B's: last two bits of first byte, first four bits of second byte
                out.write(base64Chars[((inBuffer[0] << 4) & 0x30) | (inBuffer[1] >> 4)]);
                if (inBuffer[2] != END_OF_INPUT) {
                    // C's: last four bits of second byte, first two bits of third byte
                    out.write(base64Chars[((inBuffer[1] << 2) & 0x3c) | (inBuffer[2] >> 6)]);
                    // D's: last six bits of third byte
                    out.write(base64Chars[inBuffer[2] & 0x3F]);
                } else {
                    // C's: last four bits of second byte
                    out.write(base64Chars[((inBuffer[1] << 2) & 0x3c)]);
                    // an equals sign for a character that is not a Base64 character
                    out.write('=');
                    done = true;
                }
            } else {
                // B's: last two bits of first byte
                out.write(base64Chars[((inBuffer[0] << 4) & 0x30)]);
                // an equal signs for characters that is not a Base64 characters
                out.write('=');
                out.write('=');
                done = true;
            }
            lineCount += 4;
            if (lineBreaks && lineCount >= 76) {
                out.write('\n');
                lineCount = 0;
            }
        }
        if (lineBreaks && lineCount >= 1) {
            out.write('\n');
            lineCount = 0;
        }
        out.flush();
    }

    /**
     * @param string The data to decode.
     * @return A decoded String.
     * @since ostermillerutils 1.00.00
     */
    public static String decode(String string) {
        return new String(decode(string.getBytes()));
    }

    /**
     * @param string The data to decode.
     * @param enc    Character encoding to use when converting to and from bytes.
     * @return A decoded String.
     * @throws UnsupportedEncodingException if the character encoding specified is not supported.
     * @since ostermillerutils 1.00.00
     */
    public static String decode(String string, String enc) throws UnsupportedEncodingException {
        return new String(decode(string.getBytes(enc)), enc);
    }

    /**
     * @param string The data to decode.
     * @param encIn  Character encoding to use when converting input to bytes (should not matter because Base64 data is designed to survive most character encodings)
     * @param encOut Character encoding to use when converting decoded bytes to output.
     * @return A decoded String.
     * @throws UnsupportedEncodingException if the character encoding specified is not supported.
     * @since ostermillerutils 1.00.00
     */
    public static String decode(String string, String encIn, String encOut) throws UnsupportedEncodingException {
        return new String(decode(string.getBytes(encIn)), encOut);
    }

    /**
     * @param bytes The data to decode.
     * @return Decoded bytes.
     * @since ostermillerutils 1.00.00
     */
    public static byte[] decode(byte[] bytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        int mod;
        int length = bytes.length;
        if ((mod = length % 4) != 0) {
            length += 4 - mod;
        }
        length = length * 3 / 4;
        ByteArrayOutputStream out = new ByteArrayOutputStream(length);
        try {
            decode(in, out, false);
        } catch (IOException x) {
            throw new RuntimeException(x);
        }
        return out.toByteArray();
    }

    /**
     * @param bytes The data to decode.
     * @param out   Stream to which to write decoded data.
     * @throws IOException if an IO error occurs.
     * @since ostermillerutils 1.00.00
     */
    public static void decode(byte[] bytes, OutputStream out) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        decode(in, out, false);
    }

    /**
     * @param bytes The data to decode.
     * @param out   Stream to which to write decoded data.
     * @throws IOException if an IO error occurs.
     * @since ostermillerutils 1.02.16
     */
    public static void decodeToStream(byte[] bytes, OutputStream out) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        decode(in, out, false);
    }

    /**
     * @param fIn File to be decoded (will be overwritten).
     * @throws IOException             if an IO error occurs.
     * @throws Base64DecodingException if unexpected data is encountered.
     * @since ostermillerutils 1.00.00
     */
    public static void decode(File fIn) throws IOException {
        decode(fIn, fIn, true);
    }

    /**
     * @param fIn             File to be decoded (will be overwritten).
     * @param throwExceptions Whether to throw exceptions when unexpected data is encountered.
     * @throws IOException             if an IO error occurs.
     * @throws Base64DecodingException if unexpected data is encountered when throwExceptions is specified.
     * @since ostermillerutils 1.00.00
     */
    public static void decode(File fIn, boolean throwExceptions) throws IOException {
        decode(fIn, fIn, throwExceptions);
    }

    /**
     * @param fIn  File to be decoded.
     * @param fOut File to which the results should be written (may be the same as fIn).
     * @throws IOException             if an IO error occurs.
     * @throws Base64DecodingException if unexpected data is encountered.
     * @since ostermillerutils 1.00.00
     */
    public static void decode(File fIn, File fOut) throws IOException {
        decode(fIn, fOut, true);
    }

    /**
     * @param fIn             File to be decoded.
     * @param fOut            File to which the results should be written (may be the same as fIn).
     * @param throwExceptions Whether to throw exceptions when unexpected data is encountered.
     * @throws IOException             if an IO error occurs.
     * @throws Base64DecodingException if unexpected data is encountered when throwExceptions is specified.
     * @since ostermillerutils 1.00.00
     */
    public static void decode(File fIn, File fOut, boolean throwExceptions) throws IOException {
        File temp = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(fIn));
            temp = File.createTempFile("Base64", null, null);
            out = new BufferedOutputStream(new FileOutputStream(temp));
            decode(in, out, throwExceptions);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            FileUtil.move(temp, fOut, true);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignore) {
                    if (throwExceptions) throw ignore;
                }
                in = null;
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException ignore) {
                    if (throwExceptions) throw ignore;
                }
                out = null;
            }
        }
    }

    /**
     * @param in              Stream from which bytes are read.
     * @param throwExceptions Throw an exception if an unexpected character is encountered.
     * @return the next Base64 character from the stream or -1 if there are no more Base64 characters on the stream.
     * @throws IOException             if an IO Error occurs.
     * @throws Base64DecodingException if unexpected data is encountered when throwExceptions is specified.
     * @since ostermillerutils 1.00.00
     */
    private static final int readBase64(InputStream in, boolean throwExceptions) throws IOException {
        int read;
        int numPadding = 0;
        do {
            read = in.read();
            if (read == END_OF_INPUT) return END_OF_INPUT;
            read = reverseBase64Chars[(byte) read];
            if (throwExceptions && (read == NON_BASE_64 || (numPadding > 0 && read > NON_BASE_64))) {

            }
            if (read == NON_BASE_64_PADDING) {
                numPadding++;
            }
        } while (read <= NON_BASE_64);
        return read;
    }

    /**
     * @param in Stream from which to read data that needs to be decoded.
     * @return decoded data.
     * @throws IOException if an IO error occurs.
     * @since ostermillerutils 1.00.00
     */
    public static byte[] decodeToBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        decode(in, out, false);
        return out.toByteArray();
    }

    /**
     * @param in Stream from which to read data that needs to be decoded.
     * @return decoded data.
     * @throws IOException if an IO error occurs.
     * @since ostermillerutils 1.02.16
     */
    public static String decodeToString(InputStream in) throws IOException {
        return new String(decodeToBytes(in));
    }

    /**
     * @param in  Stream from which to read data that needs to be decoded.
     * @param enc Character encoding to use when converting bytes to characters.
     * @return decoded data.
     * @throws IOException                  if an IO error occurs.Throws:
     * @throws UnsupportedEncodingException if the character encoding specified is not supported.
     * @since ostermillerutils 1.02.16
     */
    public static String decodeToString(InputStream in, String enc) throws IOException {
        return new String(decodeToBytes(in), enc);
    }

    /**
     * @param in  Stream from which to read data that needs to be decoded.
     * @param out Stream to which to write decoded data.
     * @throws IOException             if an IO error occurs.
     * @throws Base64DecodingException if unexpected data is encountered.
     * @since ostermillerutils 1.00.00
     */
    public static void decode(InputStream in, OutputStream out) throws IOException {
        decode(in, out, true);
    }

    /**
     * @param in              Stream from which to read data that needs to be decoded.
     * @param out             Stream to which to write decoded data.
     * @param throwExceptions Whether to throw exceptions when unexpected data is encountered.
     * @throws IOException             if an IO error occurs.
     * @throws Base64DecodingException if unexpected data is encountered when throwExceptions is specified.
     */
    public static void decode(InputStream in, OutputStream out, boolean throwExceptions) throws IOException {
        // Base64 decoding converts four bytes of input to three bytes of output
        int[] inBuffer = new int[4];

        boolean done = false;
        while (!done && (inBuffer[0] = readBase64(in, throwExceptions)) != END_OF_INPUT
                && (inBuffer[1] = readBase64(in, throwExceptions)) != END_OF_INPUT) {
            // Fill the buffer
            inBuffer[2] = readBase64(in, throwExceptions);
            inBuffer[3] = readBase64(in, throwExceptions);
            out.write(inBuffer[0] << 2 | inBuffer[1] >> 4);
            if (inBuffer[2] != END_OF_INPUT) {
                // four B and four C
                out.write(inBuffer[1] << 4 | inBuffer[2] >> 2);
                if (inBuffer[3] != END_OF_INPUT) {
                    // two C and six D
                    out.write(inBuffer[2] << 6 | inBuffer[3]);
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }
        out.flush();
    }
}
