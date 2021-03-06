package org.bonitasoft.engine.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class QueryOptionsTest {

    @Test
    public void getNextPageShouldPreserveOrderOptions() throws Exception {
        // given:
        final QueryOptions queryOptions = new QueryOptions(0, 10, list(new OrderByOption(PersistentObject.class, "fieldName", OrderByType.ASC)),
                list(new FilterOption(PersistentObject.class, "fieldName")), null);

        // when:
        final QueryOptions nextPage = QueryOptions.getNextPage(queryOptions);

        // then:
        assertThat(nextPage.getOrderByOptions()).isNotNull();
        assertThat(nextPage.getOrderByOptions()).hasSize(1);
    }

    @Test
    public void getNextPageShouldPreserveFilters() throws Exception {
        // given:
        final QueryOptions queryOptions = new QueryOptions(0, 10, list(new OrderByOption(PersistentObject.class, "fieldName", OrderByType.ASC)),
                list(new FilterOption(PersistentObject.class, "fieldName")), null);

        // when:
        final QueryOptions nextPage = QueryOptions.getNextPage(queryOptions);

        // then:
        assertThat(nextPage.getFilters()).isNotNull();
        assertThat(nextPage.getFilters()).hasSize(1);
    }

    @Test
    public void getNextPageShouldPreserveSearchFields() throws Exception {
        // given:
        final QueryOptions queryOptions = new QueryOptions(0, 10, list(new OrderByOption(PersistentObject.class, "fieldName", OrderByType.ASC)),
                list(new FilterOption(PersistentObject.class, "fieldName")), new SearchFields(null, null));

        // when:
        final QueryOptions nextPage = QueryOptions.getNextPage(queryOptions);

        // then:
        assertThat(nextPage.getMultipleFilter()).isNotNull();
    }

    private List list(final Object o) {
        return Arrays.asList(o);
    }
}
