package controllers.DTOs.core;

import controllers.interceptors.Pagination;
import utils.RequestUtils;

import java.util.Map;

public class AbstractListFilterDTO {
    public int from;
    public int to;
    public String sortBy;
    public Boolean sortOrder;

    public void setBaseParameters(Map<String, String[]> query, Pagination pagination) {
        this.sortBy = RequestUtils.getParam(query, "sortBy");
        this.sortOrder = Boolean.parseBoolean(RequestUtils.getParam(query, "sortOrder"));

        this.from = pagination.from;
        this.to = pagination.to;
    }
}
