package edu.hhu.taoran.utils;

import com.sun.scenario.effect.impl.sw.java.JSWBoxBlurPeer;
import edu.hhu.taoran.entity.SearchCondition;

public class StockSqlProvider {
    public String selectStockBySearchCondition(SearchCondition sc){
        StringBuilder sb =  new StringBuilder("select * from t_stock where 1=1 ");

        String classify = sc.getClassify();
        if(classify!=null&&!classify.equals("")){
            if(classify.equals("sh")){
                sb.append(" and id like '6%'");
            }

            if(classify.equals("sz")){
                sb.append(" and id like '0%'");
            }

            if(classify.equals("cyb")){
                sb.append(" and id like '3%'");
            }
        }
        String searchString = sc.getSearchString();
        if(searchString!=null&&!searchString.equals("")){
            sb.append("and id like '%"+searchString+"%' or name like '%"+searchString+"%' " );
        }

        return sb.toString();
    }

}
