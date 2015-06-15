package in.notwork.cassandra.dao.impl;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import in.notwork.cassandra.dao.BaseDao;
import in.notwork.cassandra.model.BaseModel;
import in.notwork.cassandra.util.SessionManager;
import in.notwork.cassandra.util.UDTConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * @author rishabh.
 */
public abstract class BaseDaoCassandra<T extends BaseModel, PK extends Serializable> implements BaseDao<T, PK> {

    private final Log log = LogFactory.getLog(BaseDaoCassandra.class);

    protected final Session session;
    protected final UDTConverter converter;
    protected final Mapper<T> mapper;

    public BaseDaoCassandra(final Class<T> persistentClass) {
        converter = UDTConverter.getInstance();
        session = SessionManager.getInstance().getSession();
        mapper = new MappingManager(session).mapper(persistentClass);
        initPreparedStatements();
    }

    /**
     * Initialize all prepared statements in this method.
     * Keep this method empty if there are no prepared statements for this DAO.
     */
    protected void initPreparedStatements() {
        // Keeping it empty as there are no prepared statements.
        // Needs to be override.
    }

    public T get(PK id) {
        if (log.isDebugEnabled()) {
            log.debug("Retrieving entity with id: " + id);
        }
        return mapper.get(id);
    }

    public void save(T entity) {
        if (log.isDebugEnabled()) {
            log.debug("Saving entity...");
        }
        mapper.save(entity);
    }

    public void remove(T entity) {
        if (log.isDebugEnabled()) {
            log.debug("Removing entity from database...");
        }
        mapper.delete(entity);
    }

    public void remove(PK id) {
        if (log.isDebugEnabled()) {
            log.debug("Removing entity with id: " + id + " from database...");
        }
        mapper.delete(id);
    }

    public void update(T entity) {
        save(entity);
    }

}
