package com.zhy.test.entity;

import java.io.Serializable;

public class Pet implements Cloneable,Serializable{
    private String name;
    private int age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Object clone(){
        Pet o = null;
        try{
            o = (Pet)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
         return o;
    }
}
