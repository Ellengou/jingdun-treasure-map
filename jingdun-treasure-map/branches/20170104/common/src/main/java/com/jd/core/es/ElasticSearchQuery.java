package com.jd.core.es;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchIllegalArgumentException;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ylc on 2015/12/25.
 */
public class ElasticSearchQuery {


    private String[] indices;

    private String[] types;

    private String[] fields;

    private List<FieldSortBuilder> sortBuilder;

    private BoolQueryBuilder queryBuilder;

    private Integer start = 0;

    private Integer size = 10;

    public ElasticSearchQuery() {
        queryBuilder = QueryBuilders.boolQuery();
        sortBuilder = new ArrayList<>();
    }


    /**
     * 设置索引相当于数据库中的"数据库"，不设置搜索所有，设置则不能为空
     *
     * @param indices
     * @return
     */
    public ElasticSearchQuery setIndices(String... indices) {
        if (indices == null) {
            throw new ElasticsearchIllegalArgumentException("indices must not be null");
        }
        this.indices = indices;
        return this;
    }

    /**
     * 设置类型，相当于数据库的表。
     *
     * @param types
     * @return
     */
    public ElasticSearchQuery setTypes(String... types) {
        this.types = types;
        return this;
    }

    /**
     * 设置查询字段
     *
     * @param fields 列表字段列表
     * @return
     */
    public ElasticSearchQuery setFields(String... fields) {
        if (fields == null || fields.length == 0) {
            return this;
        }
        this.fields = fields;
        return this;
    }


    /**
     * 增加排序字段
     *
     * @param field     字段名
     * @param orderType 排序类型 asc,desc
     * @return
     */
    public ElasticSearchQuery addSort(String field, String orderType) {
        if (StringUtils.isNotBlank(field)) {
            SortOrder sortOrder = SortOrder.ASC;
            if (SortOrder.DESC.name().equalsIgnoreCase(orderType)) {
                sortOrder = SortOrder.DESC;
            }
            FieldSortBuilder sortBuilder = new FieldSortBuilder(field).order(sortOrder);
            this.sortBuilder.add(sortBuilder);
        }

        return this;
    }

    /**
     * 增加查询条件
     *
     * @param value       查询内容
     * @param fuzzy       是否模糊匹配
     * @param boost       质量度
     * @param searchLogic 查询模式
     * @return
     */
    public ElasticSearchQuery addQuery(String value, Boolean fuzzy, Float boost, SearchLogic searchLogic) {
        return addQuery("", value, fuzzy, boost, searchLogic);
    }


    public ElasticSearchQuery addQuery(String field, Object from, Object to, Float boost, SearchLogic searchLogic) {
        if (StringUtils.isBlank(field) || (from == null && to == null)) {
            return this;
        }
        RangeQueryBuilder builder = QueryBuilders.rangeQuery(field);
        if (from != null) {
            builder.gte(from);
        }
        if (to != null) {
            builder.lt(to);
        }
        if (boost != null) {
            builder.boost(boost);
        }
        addQueryBuilder(builder, searchLogic);
        return this;
    }


    /**
     * 增加查询条件
     *
     * @param field       查询字段
     * @param value       查询内容
     * @param fuzzy       是否模糊匹配
     * @param boost       质量度
     * @param searchLogic 查询模式
     * @return
     */
    public ElasticSearchQuery addQuery(String field, String value, Boolean fuzzy, Float boost, SearchLogic searchLogic) {
        if (StringUtils.isBlank(value)) {
            return this;
        }
        if (fuzzy) {
            value = "*" + value + "*";
        }

        if (StringUtils.isNotBlank(field)) {
            WildcardQueryBuilder builder = QueryBuilders.wildcardQuery(field, value);
            if (boost != null) {
                builder.boost(boost);
            }
            addQueryBuilder(builder, searchLogic);
        } else {
            QueryStringQueryBuilder builder = QueryBuilders.queryStringQuery(value);
            if (boost != null) {
                builder.boost(boost);
            }
            addQueryBuilder(builder, searchLogic);
        }
        return this;
    }

    /**
     * 增加查询条件
     *
     * @param field       查询字段
     * @param values      查询内容
     * @param fuzzy       是否模糊匹配
     * @param boost       质量度
     * @param searchLogic 查询模式
     * @return
     */
    public ElasticSearchQuery addQuery(String field, String values[], Boolean fuzzy, Float boost, SearchLogic searchLogic) {
        if (StringUtils.isBlank(field) || values == null || values.length < 1) {
            return this;
        }
        for (String value : values) {
            addQuery(field, value, fuzzy, boost, searchLogic);
        }
        return this;
    }

    /**
     * 增加查询条件
     *
     * @param fields      查询字段
     * @param value       查询内容
     * @param fuzzy       是否模糊匹配
     * @param boost       质量度
     * @param searchLogic 查询模式
     * @return
     */
    public ElasticSearchQuery addQuery(String[] fields, String value, Boolean fuzzy, Float boost, SearchLogic searchLogic) {
        if (StringUtils.isBlank(value) || fields == null || fields.length < 1) {
            return this;
        }
        for (String field : fields) {
            addQuery(field, value, fuzzy, boost, searchLogic);
        }
        return this;
    }

    private void addQueryBuilder(BaseQueryBuilder builder, SearchLogic searchLogic) {
        if (searchLogic == SearchLogic.SHOULD) {
            this.queryBuilder.should(builder);
        } else if (searchLogic == SearchLogic.MUSTNOT) {
            this.queryBuilder.mustNot(builder);
        } else if (searchLogic == SearchLogic.MUST) {
            this.queryBuilder.must(builder);
        }
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String[] getIndices() {
        return indices;
    }

    public String[] getTypes() {
        return types;
    }

    public String[] getFields() {
        return fields;
    }

    public List<FieldSortBuilder> getSortBuilder() {
        return sortBuilder;
    }

    public BoolQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getSize() {
        return size;
    }

}
