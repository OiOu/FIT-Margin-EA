package smartBot.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SerializationUtils {

    private static Lock SERIALIZE_LOCK = new ReentrantLock(true);
    private static Lock DESERIALIZE_LOCK = new ReentrantLock(true);

    /**
     * Serialize base64.
     *
     * @param o
     *          the o
     * @return the string
     * @throws IOException
     *           Signals that an I/O exception has occurred.
     */
    public static String serializeBase64(Object o) throws IOException {
        SERIALIZE_LOCK.lock();
        try {
            if (o instanceof byte[]) return Base64.encodeBase64String((byte[]) o);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(o);

            return Base64.encodeBase64String(baos.toByteArray());
        } finally {
            SERIALIZE_LOCK.unlock();
        }
    }

    /**
     * Deserialize base64.
     *
     * @param s
     *          the s
     * @return the object
     * @throws IOException
     *           Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException
     *           the class not found exception
     */
    public static Object deserializeBase64(String s) throws IOException, ClassNotFoundException {
        DESERIALIZE_LOCK.lock();
        try {
            byte[] bytes = Base64.decodeBase64(s);

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));

            return ois.readObject();
        } finally {
            DESERIALIZE_LOCK.unlock();
        }
    }

}
