package com.southeastideas.elasticsearch;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @see EsTransportClient
 *
 * Created by wangchengcheng on 2018-11-16
 */

public class DemoOfES {
    private static final Logger LOG = LoggerFactory.getLogger(DemoOfES.class);

    private static final String IP_LIST = "";//add first before run
    private static final int PORT = 9300;
    private static final String CLUSTER_NAME = "";//add first before run
    private static final String INDEX = "";//add first before run
    private static final String TYPE = "";//add first before run

    private static EsTransportClient esTransportClient = new EsTransportClient(IP_LIST, PORT, CLUSTER_NAME);
    private static Client client = esTransportClient.getClient();

    public static void main(String[] args) {
        insert();
        try {
            update();
        } catch (Exception e) {
            LOG.error("update failed");
            e.printStackTrace();
        }
        query();
        delete("1");
    }

    public static IndexResponse insert() {
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        IndexResponse response = client.prepareIndex(INDEX, TYPE).setSource(json, XContentType.JSON).get();
        LOG.info("indexResponse: {}", response);
        return response;
    }

    public static UpdateResponse update() throws IOException, ExecutionException, InterruptedException {
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, "1")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "Joe Smith")
                        .field("gender", "male")
                        .endObject());
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, "1")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("gender", "male")
                        .endObject())
                .upsert(indexRequest);//If the document does not exist, the one in indexRequest will be added
        UpdateResponse updateResponse = client.update(updateRequest).get();
        LOG.info("updateResponse: {}", updateResponse);
        return updateResponse;
    }

    public static List<Map<String, Object>> query() {
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("gender", "male"));
        int from = 0;
        int size = 5;
        SearchRequestBuilder builder = client
                .prepareSearch(INDEX)
                .setSource(SearchSourceBuilder.searchSource().fetchSource(new String[]{"*"}, new String[]{}))//包含所有字段
                .setQuery(queryBuilder)
                .setFrom(from)
                .setSize(size);
        LOG.debug("{}: {}", INDEX, builder);
        SearchResponse response = builder.execute().actionGet();
        LOG.debug("searchResponse: {}", response);
        List<Map<String, Object>> data = new ArrayList<>();
        SearchHit[] searchHits = response.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            LOG.info("{}:{}", searchHit.getId(), searchHit.getSource().toString());
            data.add(searchHit.getSource());
        }
        return data;
    }

    public static DeleteResponse delete(String id) {
        DeleteResponse deleteResponse = client.prepareDelete(INDEX, TYPE, id).get();
        LOG.info("deleteResponse: {}", deleteResponse);
        return deleteResponse;
    }
}
