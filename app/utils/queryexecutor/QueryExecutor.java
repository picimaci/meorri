package utils.queryexecutor;

import controllers.DTOs.core.AbstractListFilterDTO;
import exceptions.GetResultListError;
import exceptions.GetSingleResultError;
import models.interfaces.Filterable;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public class QueryExecutor {
    public static <T extends Filterable> List<T> getResultList(String selectHeader, Map<String, Object> filters, AbstractListFilterDTO otherConditions, Class<T> clazz) {
        String queryString = selectHeader + composeWhere(filters, clazz) + composeOrderBy(otherConditions.sortBy, otherConditions.sortOrder, clazz);
        TypedQuery<T> query = JPA.em().createQuery(queryString, clazz);

        for (String key : filters.keySet()) {
            Object value = filters.get(key);
            if (value != null) {
                query.setParameter(key, value);
            }
        }

        query.setFirstResult(otherConditions.from);
        query.setMaxResults(otherConditions.to - otherConditions.from + 1);

        try {
            return query.getResultList();
        } catch (Throwable throwable) {
            throw new GetResultListError(throwable);
        }

    }

    public static <T extends Filterable, L> L getSingleResult(String selectHeader, Map<String, Object> filters, Class<T> queryClass, Class<L> returnClass) {
        String queryString = selectHeader + composeWhere(filters, queryClass);
        TypedQuery<L> query = JPA.em().createQuery(queryString, returnClass);

        for (String key : filters.keySet()) {
            Object value = filters.get(key);
            if (value != null) {
                query.setParameter(key, value);
            }
        }
        try {
            return query.getSingleResult();
        } catch (Throwable throwable) {
            throw new GetSingleResultError(throwable);
        }
    }

    public static <L> L getSingleResultOrNull(TypedQuery<L> query) {
        List<L> results = query.getResultList();
        L foundEntity = null;
        if (!results.isEmpty()) {
            foundEntity = results.get(0);
        }
        if (results.size() > 1) {
            for (L result : results) {
                if (result != foundEntity) {
                    throw new NonUniqueResultException();
                }
            }
        }
        return foundEntity;
    }

    private static <T extends Filterable> String composeWhere(Map<String, Object> filters, Class<T> clazz) {
        List<ComposeQueryWrapper> wList;
        try {
            wList = (clazz.newInstance()).getQueryHelperList();

            String delimiter = " WHERE ";
            String where = "";

            for (ComposeQueryWrapper wrapper : wList) {
                if (filters.containsKey(wrapper.key)) {
                    where += delimiter + wrapper.where;
                    delimiter = " AND ";
                }
            }
            return where;
        } catch (InstantiationException | IllegalAccessException e) {
            Logger.error("QueryHelper failed to instantiate class " + clazz.getName() + " or failed to get HelperList", e);
            return "";
        }
    }

    private static <T extends Filterable> String composeOrderBy(String sortBy, boolean ascending, Class<T> clazz) {
        try {
            T instance = clazz.newInstance();
            boolean asc;
            String orderBy;
            String sortField = null;
            List<ComposeQueryWrapper> wList = instance.getQueryHelperList();
            String key;

            if(sortBy == null || "".equals(sortBy)) {
                key = instance.getDefaultSortField();
                asc = instance.isDefaultSortOrderASC();
            } else {
                key = sortBy;
                asc = ascending;
            }

            for (ComposeQueryWrapper wrapper : wList) {
                if (key.equals(wrapper.key)) {
                    sortField = wrapper.order;
                }
            }

            orderBy = " ORDER BY " + sortField + (asc ? " ASC" : " DESC");
            return orderBy;
        } catch (InstantiationException | IllegalAccessException e) {
            Logger.error("QueryHelper failed to instantiate class " + clazz.getName() + " or failed to get HelperList", e);
            return "";
        }
    }
}
