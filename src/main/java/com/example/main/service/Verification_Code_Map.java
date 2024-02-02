package com.example.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Verification_Code_Map {
    private final Map<String, String> storagePool;
    @Autowired
    public Verification_Code_Map(@Qualifier("storagePool") Map<String, String> storagePool) {
        /*
        * MyService 类的构造函数接受一个名为 storagePool 的 Map<String, Object> 对象，
        * 该对象通过 @Autowired 自动注入。@Qualifier 注解用于指定要注入的 Bean 的名称，
        * 这里指定为 "storagePool"，以匹配配置类中定义的 Bean 名称。
         */
        this.storagePool = storagePool;
    }
    public void PutValue(String key, String value) {
        storagePool.put(key, value);
    }

    public String GetValue(String key) {
        return storagePool.get(key);
    }

    public void delete(String key) {
        storagePool.remove(key);
    }
}
