package in.notwork.cassandra.model;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author rishabh
 */
public abstract class BaseModel<T> extends Model {

    private final Log log = LogFactory.getLog(BaseModel.class);
}
