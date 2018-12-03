package com.wutu.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wutu.utils.EsUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@RequestMapping("/es")
@Controller
@ResponseBody
public class EsController {
    private static final Logger logger = LoggerFactory.getLogger(EsController.class);

    /**
     * 创建索引
     *
     * @return
     */
    @RequestMapping("/createIndex")
    @ResponseBody
    public String createIndex(String indexName) {
        if (!EsUtils.checkIndexExist(indexName)) {
            XContentBuilder builder = null;
            try {
                builder = JsonXContent.contentBuilder()
                        .startObject()
                            .startObject("mappings")
                                .startObject("word")
                                    .startObject("properties")
                                        .startObject("content")
                                            .field("type","text")
                                            .field("analyzer","ik_max_word")
                                            .field("search_analyzer","ik_max_word")
                                        .endObject()
                                        .startObject("brand")
                                            .field("type","nested")
                                            .startObject("properties")
                                                .startObject("name")
                                                    .field("type","keyword")
                                                .endObject()
                                            .endObject()
                                        .endObject()
                                    .endObject()
                                .endObject()
                            .endObject()
                        .endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (EsUtils.createIndex(indexName, builder)) {
                return "索引创建成功";
            } else {
                return "索引已经失败";
            }
        } else {
            return "索引已经存在";
        }
    }

    /**
     * 插入记录
     *
     * @return
     */
    @RequestMapping("/addData")
    @ResponseBody
    public String addData(String indexName, String esType,String jsonObject) {
        JSONObject object= JSON.parseObject(jsonObject);
        String id = EsUtils.addData(indexName, esType, object.getString("id"), object);
        if (!org.springframework.util.StringUtils.isEmpty(id)) {
            return "插入成功";
        } else {
            return "插入失败";
        }
    }

    /**
     * 查询所有
     *
     * @return
     */
    @RequestMapping("/queryAll")
    @ResponseBody
    public String queryAll(String indexName, String esType) {
        try {
            HttpEntity entity = new NStringEntity(
                    "{ \"query\": { \"match_all\": {}}}",
                    ContentType.APPLICATION_JSON);
            String endPoint = "/" + indexName + "/" + esType + "/_search";
            Request request=new Request("POST",endPoint);
            request.setEntity(entity);
            Response response = EsUtils.getLowLevelClient().performRequest(request);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "查询数据出错";
    }

    /**
     * 根据条件查询
     *
     * @return
     */
    @RequestMapping("/queryByMatch")
    @ResponseBody
    public String queryByMatch(String indexName, String esType) {
        try {
            String endPoint = "/" + indexName + "/" + esType + "/_search";

            IndexRequest indexRequest = new IndexRequest();
            XContentBuilder builder;
            try {
                builder = JsonXContent.contentBuilder()
                        .startObject()
                        .startObject("query")
                        .startObject("match")
                        .field("name.keyword", "zjj")
                        .endObject()
                        .endObject()
                        .endObject();
                indexRequest.source(builder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String source = indexRequest.source().utf8ToString();

            logger.info("source---->" + source);

            HttpEntity entity = new NStringEntity(source, ContentType.APPLICATION_JSON);

            Response response = EsUtils.getLowLevelClient().performRequest("POST", endPoint, Collections.<String, String>emptyMap(), entity);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "查询数据出错";
    }

    /**
     * 复合查询
     *
     * @return
     */
    @RequestMapping("/queryByCompound")
    @ResponseBody
    public String queryByCompound(String indexName, String esType) {
        try {
            String endPoint = "/" + indexName + "/" + esType + "/_search";

            IndexRequest indexRequest = new IndexRequest();
            XContentBuilder builder;
            try {
                /**
                 * 查询名字等于 liming
                 * 并且年龄在30和35之间
                 */
                builder = JsonXContent.contentBuilder()
                        .startObject()
                        .startObject("query")
                        .startObject("bool")
                        .startObject("must")
                        .startObject("match")
                        .field("name.keyword", "liming")
                        .endObject()
                        .endObject()
                        .startObject("filter")
                        .startObject("range")
                        .startObject("age")
                        .field("gte", "30")
                        .field("lte", "35")
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject();
                indexRequest.source(builder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String source = indexRequest.source().utf8ToString();

            logger.info("source---->" + source);

            HttpEntity entity = new NStringEntity(source, ContentType.APPLICATION_JSON);

            Response response = EsUtils.getLowLevelClient().performRequest("POST", endPoint, Collections.<String, String>emptyMap(), entity);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "查询数据出错";
    }

    /**
     * 删除查询的数据
     *
     * @return
     */
    @RequestMapping("/delByQuery")
    @ResponseBody
    public String delByQuery(String indexName, String esType) {

        String deleteText = "chy";

        String endPoint = "/" + indexName + "/" + esType + "/_delete_by_query";

        /**
         * 删除条件
         */
        IndexRequest indexRequest = new IndexRequest();
        XContentBuilder builder;
        try {
            builder = JsonXContent.contentBuilder()
                    .startObject()
                    .startObject("query")
                    .startObject("term")
                    //name中包含deleteText
                    .field("name.keyword", deleteText)
                    .endObject()
                    .endObject()
                    .endObject();
            indexRequest.source(builder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String source = indexRequest.source().utf8ToString();

        HttpEntity entity = new NStringEntity(source, ContentType.APPLICATION_JSON);
        try {
            Response response = EsUtils.getLowLevelClient().performRequest("POST", endPoint, Collections.<String, String>emptyMap(), entity);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "删除错误";
    }

    /**
     * 演示聚合统计
     *
     * @return
     */
    @RequestMapping("/aggregation")
    @ResponseBody
    public String aggregation(String indexName, String esType) {
        try {
            String endPoint = "/" + indexName + "/" + esType + "/_search";

            IndexRequest indexRequest = new IndexRequest();
            XContentBuilder builder;
            try {
                builder = JsonXContent.contentBuilder()
                        .startObject()
                        .startObject("aggs")
                        .startObject("名称分组结果")
                        .startObject("terms")
                        .field("field", "name.keyword")
                        .startArray("order")
                        .startObject()
                        .field("_count", "asc")
                        .endObject()
                        .endArray()
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject();
                indexRequest.source(builder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String source = indexRequest.source().utf8ToString();

            logger.info("source---->" + source);

            HttpEntity entity = new NStringEntity(source, ContentType.APPLICATION_JSON);

            Response response = EsUtils.getLowLevelClient().performRequest("POST", endPoint, Collections.<String, String>emptyMap(), entity);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "查询数据出错";
    }

    /**
     * 嵌套聚合查询
     * @param indexName
     * @param esType
     * @return
     */
    @RequestMapping("/aggrBrand")
    public String aggrBrand(String indexName, String esType) {
        try {
            String endPoint = "/" + indexName + "/" + esType + "/_search";
            IndexRequest indexRequest = new IndexRequest();
            XContentBuilder builder;
            try {
                builder = JsonXContent.contentBuilder()
                        .startObject()
                            .field("size",10)
                            .startObject("query")
                                .startObject("match")
                                    .field("content","实践")
                                .endObject()
                            .endObject()
                            .startObject("aggs")
                                .startObject("brand")
                                    .startObject("nested")
                                        .field("path","brand")
                                    .endObject()
                                    .startObject("aggs")
                                        .startObject("brands")
                                            .startObject("terms")
                                                .field("field","brand.name")
                                            .endObject()
                                        .endObject()
                                    .endObject()
                                .endObject()
                            .endObject()
                        .endObject();
                indexRequest.source(builder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String source = indexRequest.source().utf8ToString();
            HttpEntity entity = new NStringEntity(source, ContentType.APPLICATION_JSON);
            Request request=new Request("POST",endPoint);
            request.setEntity(entity);
            Response response = EsUtils.getLowLevelClient().performRequest(request);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "查询数据出错";
    }
    @RequestMapping("/queryByBrand")
    public String queryByBrand(String indexName, String esType,String content,String brandName) {
        try {
            String endPoint = "/" + indexName + "/" + esType + "/_search";
            IndexRequest indexRequest = new IndexRequest();
            XContentBuilder builder;
            try {
                builder = JsonXContent.contentBuilder()
                        .startObject()
                            .startObject("query")
                                .startObject("bool")
                                    .startArray("must")
                                        .startObject()
                                            .startObject("match")
                                                .field("content",content)
                                            .endObject()
                                        .endObject()
                                        .startObject()
                                            .startObject("nested")
                                                .field("path","brand")
                                                .startObject("query")
                                                    .startObject("match")
                                                        .field("brand.name",brandName)
                                                    .endObject()
                                                .endObject()
                                            .endObject()
                                        .endObject()
                                    .endArray()
                                .endObject()
                            .endObject()
                        .endObject();
                indexRequest.source(builder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String source = indexRequest.source().utf8ToString();
            HttpEntity entity = new NStringEntity(source, ContentType.APPLICATION_JSON);
            Request request=new Request("POST",endPoint);
            request.setEntity(entity);
            System.out.println("ssss");
            Response response = EsUtils.getLowLevelClient().performRequest(request);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "查询数据出错";
    }
}


