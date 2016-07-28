package daos;

import models.Model;
import play.Logger;

import java.util.List;
import java.util.Optional;

public class BaseDAO {
    protected static <T extends Model> Optional<List<T>> withContainmentCheck(List<Long> ids, boolean mustContainAll, List<T> ret) {
        if(ret.size() == ids.size()) {
            return Optional.of(ret);
        } else {
            Logger.warn("Couldn't find all " + ret.getClass().getName() + " type with ids: " + ids);
            if(mustContainAll) {
                return Optional.empty();
            } else {
                return Optional.of(ret);
            }
        }
    }
}
