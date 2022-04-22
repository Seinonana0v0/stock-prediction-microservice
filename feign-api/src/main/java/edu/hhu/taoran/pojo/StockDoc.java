package edu.hhu.taoran.pojo;

public class StockDoc {
    private String id;
    private String name;
    private String industry;
    private String introduction;
    private String classify;

    public StockDoc(Stock stock){
        if(stock.getId().startsWith("6")){
            this.classify = "sh";
        }
        if(stock.getId().startsWith("0")){
            this.classify = "sz";
        }
        if(stock.getId().startsWith("3")){
            this.classify = "cyb";
        }
        this.id = stock.getId();
        this.name = stock.getName();
        this.industry = stock.getIndustry();
        this.introduction = stock.getIntroduction();
    }

    public StockDoc(String id, String name, String industry, String introduction, String classify) {
        this.id = id;
        this.name = name;
        this.industry = industry;
        this.introduction = introduction;
        this.classify = classify;
    }

    public StockDoc() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    @Override
    public String toString() {
        return "StockDoc{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", industry='" + industry + '\'' +
                ", introduction='" + introduction + '\'' +
                ", classify='" + classify + '\'' +
                '}';
    }
}
