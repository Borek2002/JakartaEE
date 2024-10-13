package pl.edu.pg.eti.kask.serialization.component;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.*;

@Log
public class CloningUtility {

    /**
     * Created deep copy of provided object using serialization.
     *
     * @param object object to be cloned
     * @param <T>    type of the object
     * @return deep copy of the object
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T clone(T object) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(writeObject(object).toByteArray());
             ObjectInputStream ois = new ObjectInputStream(is)) {
            return (T) ois.readObject();
        }

    }

    /**
     * @param object object to be cloned
     *               * @param <T>    type of the object
     * @return close {@link ByteArrayOutputStream} with serialized object
     * @throws IOException on IO error
     */
    private <T extends Serializable> ByteArrayOutputStream writeObject(T object) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(object);
            return os;
        }
    }

}
