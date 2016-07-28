package models.interfaces;

import utils.queryexecutor.ComposeQueryWrapper;

import java.util.List;

/**
 * Created by picimaci on 2016.07.22..
 */
public interface Filterable {
    List<ComposeQueryWrapper> getQueryHelperList();

    String getDefaultSortField();

    boolean isDefaultSortOrderASC();
}
