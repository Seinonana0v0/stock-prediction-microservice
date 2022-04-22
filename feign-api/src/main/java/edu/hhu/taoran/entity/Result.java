package edu.hhu.taoran.entity;

public class Result {
    private Object data = null;
    private Boolean flag;

    public Result(Object data, Boolean flag) {
        this.data = data;
        this.flag = flag;
    }

    public Result(Boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}

