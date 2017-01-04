package com.jd.core.es;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ylc on 2015/12/30.
 */
public class ElasticSearchUtil {

    public static List<Map<String, Object>> convertData(SearchHits hits) {
        List<Map<String, Object>> data = new ArrayList<>();
        for (SearchHit hit : hits) {
            if (hit.getSource()!=null) {
                Map dataSource = hit.getSource();
                //dataSource.put("type", hit.getType());
                data.add(dataSource);

            } else {
                Map<String, SearchHitField> fieldMap = hit.getFields();
                Map<String, Object> dataSource = new HashMap<>();
                //dataSource.put("type", hit.getType());
                for (Map.Entry<String, SearchHitField> entry : fieldMap.entrySet()) {
                    SearchHitField field = entry.getValue();
                    if (field.getValues().size() > 1) {
                        dataSource.put(field.getName(), field.getValues());
                    } else {
                        dataSource.put(field.getName(), field.getValue());
                    }
                }
                data.add(dataSource);
            }
        }
        return data;
    }

    public static List<String> dycolumn(SearchHits hits) {
        List<String> columns = new ArrayList<>();
        for (SearchHit hit : hits) {
            if (hit.getSource()!=null) {
                for (String str : hit.getSource().keySet()) {
                    columns.add(str);
                }
                break;
            } else if (!hit.getFields().isEmpty() && columns.isEmpty()) {
                for (String key : hit.getFields().keySet()) {
                    columns.add(key);
                }
                break;
            }
        }
        return columns;
    }
}
