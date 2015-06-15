package in.notwork.cassandra.util;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.UDTMapper;

/**
 * A singleton class that converts from UDT to custom object and vice versa.
 *
 * @author rishabh.
 */
public class UDTConverter {

    private static UDTConverter ourInstance = new UDTConverter();

    /**
     * @return an instance of UDTConverter
     */
    public static UDTConverter getInstance() {
        return ourInstance;
    }

    private UDTConverter() {
    }

    /**
     * Converts UDTValue to given object, specified by the clazz.
     *
     * @param value UDTValue received to convert
     * @param clazz The Class to be converted to.
     * @return an Vendor object.
     */
    public <T> T fromUDT(UDTValue value, Class<T> clazz) {
        Session session = SessionManager.getInstance().getSession();
        UDTMapper<T> mapper = new MappingManager(session).udtMapper(clazz);
        return mapper.fromUDT(value);
    }

    /**
     * Converts object to UDTValue.
     *
     * @param type
     * @param clazz The Class of the object being converted.
     * @return The UDTValue object.
     */
    public <T> UDTValue toUDT(T type, Class<T> clazz) {
        Session session = SessionManager.getInstance().getSession();
        UDTMapper<T> mapper = new MappingManager(session).udtMapper(clazz);
        return mapper.toUDT(type);
    }

}
