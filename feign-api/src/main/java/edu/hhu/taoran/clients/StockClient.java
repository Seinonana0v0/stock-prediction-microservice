package edu.hhu.taoran.clients;

import edu.hhu.taoran.entity.PageResult;
import edu.hhu.taoran.entity.Result;
import edu.hhu.taoran.entity.SearchCondition;
import edu.hhu.taoran.pojo.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@FeignClient("stockservice")
public interface StockClient {
}
