package com.us.example.springel;

/**
 * @Author:Dave
 * @Description:
 * @Date: 2017/8/31 9:50
 */
public class Item {

    private String name;
    private int qty;

    public Item(String name,int qty){
        super();
        this.name = name;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
