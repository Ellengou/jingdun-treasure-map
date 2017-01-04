package com.jd.core.es;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ylc on 2015/12/25.
 */
@Component
public class ElasticSearchServer {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired(required = false)
    private TransportClient client;

    public SearchHits search(ElasticSearchQuery query) {
        if (query == null) {
            //return null;
        }
        if (query.getFields() == null) {
            //return null;
        }
        SearchRequestBuilder searchRequestBuilder;
        /**设置索引 */
        if (query.getIndices() != null && StringUtils.isNotBlank(query.getIndices()[0])) {
            searchRequestBuilder = client.prepareSearch(query.getIndices());
        } else {
            searchRequestBuilder = client.prepareSearch();
        }
        if (query.getTypes() != null && StringUtils.isNotBlank(query.getTypes()[0])) {
            searchRequestBuilder.setTypes(query.getTypes());
        }
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        if (query.getStart() != null && query.getStart() >= 0) {
            searchRequestBuilder.setFrom(query.getStart());
        }
        if (query.getSize() != null && query.getSize() >= 0) {
            searchRequestBuilder.setSize(query.getSize());
        }
        searchRequestBuilder.setExplain(false);

        if (query.getQueryBuilder() == null) {
            return null;
        }
        searchRequestBuilder.setQuery(query.getQueryBuilder());

        if (query.getTypes().length != 0 && StringUtils.isNotBlank(query.getTypes()[0]) && query.getSortBuilder() != null) {
            for (FieldSortBuilder sortBuilder : query.getSortBuilder()) {
                searchRequestBuilder.addSort(sortBuilder);
            }
        }
        if (query.getFields() != null && query.getFields().length > 0) {
            searchRequestBuilder.addFields(query.getFields());
        }
        return searchRequestBuilder.execute().actionGet().getHits();
    }

    public String searchJSON(ElasticSearchQuery query) {
        SearchHits hits = this.search(query);
        if (hits == null || hits.getHits().length == 0) {
            return null;
        }
        List<Map<String, Object>> data = ElasticSearchUtil.convertData(hits);
        return JSON.toJSONString(data);
    }

    public <T> List<T> get(String column, Class<T> classz, ElasticSearchQuery query) {
        List<T> list = new ArrayList<>();

        SearchHits hits = this.search(query);
        if (hits == null || hits.getHits().length == 0) {
            return list;
        }
        for (SearchHit hit : hits) {
            Map<String, SearchHitField> map = hit.getFields();
            if (map.containsKey(column)) {
                Object object = map.get(column).getValue();
                if (object.getClass().equals(classz)) {
                    list.add((T) object);
                }
            }
        }
        return list;
    }


    /**
     * 查询索引列表
     *
     * @return
     */
    public String[] getIndices() {
        ClusterStateResponse response = client.admin().cluster()
                .prepareState()
                .execute().actionGet();
        //获取所有索引
        String[] indices = response.getState().getMetaData().getConcreteAllIndices();
        return indices;
    }

    /**
     * 查询指定 index下的所有type
     *
     * @param indexName
     * @return
     */
    public String[] getTypes(String indexName) {
        if (StringUtils.isBlank(indexName)) {
            return new String[0];
        }
        GetMappingsResponse mapping = client.admin().indices().prepareGetMappings(indexName).get();
        ImmutableOpenMap<String, MappingMetaData> data = mapping.getMappings().get(indexName);
        Object[] keys = data.keys().toArray();
        String[] types = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            types[i] = String.valueOf(keys[i]);
        }
        return types;
    }

    public Map<String, String> getFields(String indexName, String typeName) {
        Map<String, String> fields = new HashMap<>();
        try {
            GetMappingsResponse mapping = client.admin().indices().prepareGetMappings(indexName).get();
            Map props = mapping.getMappings().get(indexName).get(typeName).getSourceAsMap();
            if (props == null || !props.containsKey("properties")) {
                return fields;
            }
            Map<String, Object> fieldMappings = (Map) props.get("properties");
            for (Map.Entry<String, Object> entry : fieldMappings.entrySet()) {
                Map fieldIndexRecordMapping = (Map) fieldMappings.get(entry.getKey());
                String type = String.valueOf(fieldIndexRecordMapping.get("type"));
                fields.put(entry.getKey(), type);
            }
        } catch (Exception e) {
            logger.error(e);
            throw new RuntimeException("es字段解析失败", e);
        }
        return fields;
    }

}
