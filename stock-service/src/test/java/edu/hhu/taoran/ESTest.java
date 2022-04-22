package edu.hhu.taoran;
import com.alibaba.fastjson.JSON;
import edu.hhu.taoran.mapper.StockMapper;
import edu.hhu.taoran.pojo.Stock;
import edu.hhu.taoran.pojo.StockDoc;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= StockServiceStater.class)
public class ESTest {
    @Autowired
    private StockMapper stockMapper;

    private RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://81.69.240.39:9200")));

    @Test
    public void testBulk() throws IOException{
        BulkRequest request = new BulkRequest();
        List<Stock> list = stockMapper.findAll();
        for (Stock stock : list) {
            StockDoc stockDoc = new StockDoc(stock);
            String json = JSON.toJSONString(stockDoc);
            request.add(new IndexRequest("stock").id(stock.getId()).source(json, XContentType.JSON));
        }
        client.bulk(request, RequestOptions.DEFAULT);
    }


    @Test
    public void TestSearch() throws IOException{
        String queryString = "";
        SearchRequest request = new SearchRequest("stock");
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        request.source().query(boolQuery);
        request.source().from(9).size(10);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;
        System.out.println(total);
        SearchHit[] hits = searchHits.getHits();
        System.out.println(hits.length);
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            StockDoc stockDoc = JSON.parseObject(json,StockDoc.class);
            System.out.println(stockDoc);
        }

    }

}
