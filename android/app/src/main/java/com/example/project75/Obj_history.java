package com.example.project75;

public class Obj_history {
    private String list_name;
    private String code_table;
    private String total;
    private String date;

    public Obj_history(String list_name, String code_table, String total, String date){
        this.list_name = list_name;
        this.code_table = code_table;
        this.total = total;
        this.date = date;
    }

    public String getCode_table() {
        return code_table;
    }

    public String getList_name() {
        return list_name;
    }

    public String getDate() {
        return date;
    }

    public String getTotal() {
        return total;
    }
}
