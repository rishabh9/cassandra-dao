package in.notwork.cassandra.util;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.List;

/**
 * @author rishabh.
 */
public class ConfigurationLoader {

    private static ConfigurationLoader ourInstance = new ConfigurationLoader();

    public static ConfigurationLoader getInstance() {
        return ourInstance;
    }

    private final String[] files = {"cassandra.properties"};

    private final Log log = LogFactory.getLog(ConfigurationLoader.class);

    private CompositeConfiguration compositeConfig;

    private ConfigurationLoader() {
        init();
    }

    private void init() {
        try {
            compositeConfig = new CompositeConfiguration();
            PropertiesConfiguration configuration = null;

            for (String file : files) {
                log.info("Reading properties file ... " + file);
                configuration = new PropertiesConfiguration();
                configuration.load(ConfigurationLoader.class.getClassLoader().getResourceAsStream(file));
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
                compositeConfig.addConfiguration(configuration);
            }

            if (log.isDebugEnabled()) {
                log.debug("The following properties have been loaded...");
                Iterator<String> keys = compositeConfig.getKeys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    log.debug("    " + key + " = " + getPropList(key));
                }
            }
        } catch (ConfigurationException ce) {
            log.error("Error reading properties.", ce);
        }
    }

    /**
     * Get property for the given key.
     *
     * @param key The property key
     * @return The property value
     */
    public String getProperty(final String key) {
        return (String) compositeConfig.getProperty(key);
    }

    /**
     * Gets the int property.
     *
     * @param key the key
     * @return the int property
     */
    public int getIntProperty(final String key) {
        return compositeConfig.getInt(key);
    }

    /**
     * Gets the long property.
     *
     * @param key the key
     * @return the long property
     */
    public long getLongProperty(final String key) {
        return compositeConfig.getLong(key);
    }

    /**
     * Gets the string array.
     *
     * @param key the key
     * @return the string array
     */
    public String[] getStringArray(final String key) {
        return compositeConfig.getStringArray(key);
    }

    /**
     * Gets the prop list.
     *
     * @param key the key
     * @return the prop list
     */
    public List getPropList(final String key) {
        return compositeConfig.getList(key);
    }

    /**
     * Gets the boolean property.
     *
     * @param key the key
     * @return the boolean property
     */
    public Boolean getBooleanProperty(final String key) {
        return compositeConfig.getBoolean(key);
    }

}
