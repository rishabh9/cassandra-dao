package in.notwork.cassandra.model.udt;

import in.notwork.cassandra.model.Model;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author rishabh.
 */
public abstract class BaseUDT<T> extends Model {

    private final Log log = LogFactory.getLog(BaseUDT.class);

}
