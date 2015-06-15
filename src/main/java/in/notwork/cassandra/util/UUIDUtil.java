package in.notwork.cassandra.util;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

/**
 * @author rishabh.
 */
public class UUIDUtil {

    private static UUIDUtil ourInstance = new UUIDUtil();

    public static UUIDUtil getInstance() {
        return ourInstance;
    }

    private TimeBasedGenerator generators;

    private UUIDUtil() {
        generators = Generators.timeBasedGenerator();
    }

    /**
     * @return Time based UUID (Type 1 UUID)
     */
    public UUID getUUID() {
        return generators.generate();
    }
}
