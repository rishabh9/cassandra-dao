package in.notwork.cassandra.util;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import in.notwork.cassandra.constants.NodeConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * @author rishabh.
 */
public class SessionManager {

    private static SessionManager ourInstance = new SessionManager();

    public static SessionManager getInstance() {
        return ourInstance;
    }

    private SessionManager() {
        keyspace = ConfigurationLoader.getInstance().getProperty(NodeConstants.CASSANDRA_SCHEMA);
        connect();
        setKeyspace();
    }

    private String keyspace;

    private final Log log = LogFactory.getLog(SessionManager.class);

    private Cluster cluster;
    private Session session;

    public Session getSession() {

        // Session instances are thread-safe and usually a single instance
        // is all you need per application. However, a given session can only
        // be set to one keyspace at a time, so one instance per keyspace is
        // necessary. Your application typically only needs a single cluster
        // object, unless you're dealing with multiple physical clusters.

        return this.session;
    }

    private void connect() {
        if (log.isDebugEnabled()) {
            log.debug("Connecting to Cassandra...");
        }
        String node = ConfigurationLoader.getInstance().getProperty(NodeConstants.CASSANDRA_NODE);
        Assert.hasText(node, "Cassandra node needs to be configured in cassandra.properties.");
        cluster = Cluster.builder()
                .addContactPoint(node)
                .withRetryPolicy(DefaultRetryPolicy.INSTANCE)
                .withLoadBalancingPolicy(
                        new TokenAwarePolicy(new DCAwareRoundRobinPolicy()))
                .build();
        Metadata metadata = getMetadata();
        if (log.isDebugEnabled()) {
            log.debug("Connected to cluster: " + metadata.getClusterName());
        }
        for (Host host : metadata.getAllHosts()) {
            if (log.isDebugEnabled()) {
                log.debug("Datacenter: " + host.getDatacenter() +
                        ", Host: " + host.getAddress() +
                        ", Rack: " + host.getRack());
            }
        }
        session = cluster.connect();
    }

    private void setKeyspace() {
        if (log.isDebugEnabled()) {
            log.debug("Setting keyspace...");
        }
        getSession().execute("USE " + ConfigurationLoader.getInstance().getProperty(NodeConstants.CASSANDRA_SCHEMA) + ";");
    }

    public Metadata getMetadata() {
        return cluster.getMetadata();
    }

    public UserType getUserType(String type) {
        if (log.isDebugEnabled()) {
            log.debug("Retrieving user type - " + type);
        }
        return getMetadata().getKeyspace(keyspace).getUserType(type);
    }

    public void close() {
        if (log.isDebugEnabled()) {
            log.debug("Closing connection...");
        }
        session.close();
        cluster.close();
    }

    protected void finalize() {
        if (log.isDebugEnabled()) {
            log.debug("Finalizing the SessionManager...");
        }
        close();
    }
}
