package com.techprimers.elastic.builder;

import com.techprimers.elastic.model.Users;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchQueryBuilder {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //private final static String FIELD_MYFIELD = "teamName";
        // int[] idades = new int[10];
        //     for(int i = 0;i<10;i++)
        //     {
        //             idades[i] = i * 10;
        //     }
    private final static String[] test = { "name", "teamName", "salary" };

    public List<Users> getAll(String text) {

        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.queryStringQuery("*" + text + "*")
                        .lenient(true)
                        .field("teamName")
                        .field("name")
                        );

        SourceFilter sourceFilter = new FetchSourceFilter( test, null);

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withSourceFilter(sourceFilter)
                .build();

        List<Users> userses = elasticsearchTemplate.queryForList(build, Users.class);

        return userses;
    }
}
