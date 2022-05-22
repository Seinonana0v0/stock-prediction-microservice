package edu.hhu.taoran.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.hhu.taoran.clients.DataSetClient;
import edu.hhu.taoran.clients.ModelClient;
import edu.hhu.taoran.entity.ModelStatus;
import edu.hhu.taoran.entity.PageResult;
import edu.hhu.taoran.entity.SearchCondition;
import edu.hhu.taoran.mapper.StockMapper;
import edu.hhu.taoran.pojo.Stock;
import edu.hhu.taoran.pojo.StockDataSet;
import edu.hhu.taoran.pojo.StockDoc;
import edu.hhu.taoran.pojo.StockModel;
import edu.hhu.taoran.service.StockService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private DataSetClient dataSetClient;

    @Autowired
    private ModelClient modelClient;

    private RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://81.69.240.39:9200")));

    @Override
    public Stock selectById(String id) {
        return stockMapper.selectById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public PageResult selectBySearchCondition(SearchCondition searchCondition) {
        PageHelper.startPage(searchCondition.getPage(),searchCondition.getSize());
        List<Stock> stockList = stockMapper.selectBySearchCondition(searchCondition);
        PageInfo<Stock> pageInfo = new PageInfo<>(stockList);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public PageResult selectBySearchConditionWithDataSet(SearchCondition searchCondition) {
        PageResult pageResult = this.selectBySearchCondition(searchCondition);
        Long total = pageResult.getTotal();
        List<Stock> stockList = pageResult.getRows();
        List<StockDataSet> rows = new ArrayList<>();
        for (Stock stock : stockList) {
            Integer dataSetStatus = dataSetClient.selectDataSetStatusById(stock.getId());
            rows.add(new StockDataSet(stock,dataSetStatus));
        }
        return new PageResult(total,rows);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public PageResult selectBySearchConditionWithModel(SearchCondition searchCondition) {
        PageResult pageResult = this.selectBySearchCondition(searchCondition);
        Long total = pageResult.getTotal();
        List<Stock> stockList = pageResult.getRows();
        List<StockModel> rows = new ArrayList<>();
        for (Stock stock : stockList) {
            ModelStatus modelStatus = modelClient.selectModelStatusById(stock.getId());
            rows.add(new StockModel(stock,modelStatus));
        }
        return new PageResult(total,rows);
    }



    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = false,rollbackFor = Exception.class)
    public void insertStockWithDataSet(Stock stock) {
        stockMapper.insertStock(stock);
        dataSetClient.insertWithDataSet(stock.getId());
        modelClient.insertModel(stock.getId());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = false,rollbackFor = Exception.class)
    public void insertStockWithoutDataSet(Stock stock) {
        stockMapper.insertStock(stock);
        dataSetClient.insertWithOutDataSet(stock.getId());
        modelClient.insertModel(stock.getId());
    }

    @Override
    public PageResult ESSearch(SearchCondition searchCondition) throws IOException {
        String classify = searchCondition.getClassify();
        String searchString = searchCondition.getSearchString();
        Integer page = searchCondition.getPage();
        Integer size = searchCondition.getSize();

        SearchRequest request = new SearchRequest("stock");
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(classify!=null&&!classify.equals("")){
            boolQuery.must(QueryBuilders.termQuery("classify",classify));
        }
        if(searchString!=null&&!searchString.equals("")){
            boolQuery.filter(QueryBuilders.multiMatchQuery(searchString,"id","name","industry","introduction"));
        }
        request.source().query(boolQuery);
        request.source().from(size*(page-1)).size(size);
        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false).field("industry").requireFieldMatch(false).field("introduction").requireFieldMatch(false));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();

        long total = searchHits.getTotalHits().value;
        SearchHit[] hits = searchHits.getHits();
        List<StockDoc> rows = new ArrayList<>();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            StockDoc stockDoc = JSON.parseObject(json,StockDoc.class);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if(!CollectionUtils.isEmpty(highlightFields)){
                HighlightField highlightName = highlightFields.get("name");
                HighlightField highlightIndustry = highlightFields.get("industry");
                HighlightField highlightIntroduction = highlightFields.get("introduction");
                if(highlightName!=null){
                    String name = highlightName.getFragments()[0].string();
                    stockDoc.setName(name);
                }
                if(highlightIndustry!=null){
                    String industry = highlightIndustry.getFragments()[0].string();
                    stockDoc.setIndustry(industry);
                }
                if(highlightIntroduction!=null){
                    String introduction = highlightIntroduction.getFragments()[0].string();
                    stockDoc.setIntroduction(introduction);
                }
            }
            rows.add(stockDoc);
        }
        return new PageResult(total,rows);
    }

    @Override
    public void insertToEs(String stockId) throws IOException {
        Stock stock = stockMapper.selectById(stockId);
        StockDoc stockDoc = new StockDoc(stock);
        String json = JSON.toJSONString(stockDoc);
        IndexRequest request = new IndexRequest("stock").id(stockId).source(json, XContentType.JSON);
        client.index(request,RequestOptions.DEFAULT);
    }

    @Override
    public String selectNameById(String id) {
        return stockMapper.selectNameById(id);
    }

    @Override
    public List<String> selectIndustrys() {
        return stockMapper.selectIndustrys();
    }


}
