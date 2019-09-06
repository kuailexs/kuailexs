package com.kuailexs.mirror.ubports.web.constant;

public enum BlogType {
    QA(1,"问答"),
    BLOG(2,"博客");
    int code;
    String name;
    BlogType(int code, String name) {
        this.code = code;
        this.name = name;
    }
    public BlogType getByCode(int code){
        for(BlogType blogType : BlogType.values()){
            if(blogType.code == code){
                return blogType;
            }
        }
        return null;
    }
    public BlogType getByName(String name){
        for(BlogType blogType : BlogType.values()){
            if(blogType.name.equals(name)){
                return blogType;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
