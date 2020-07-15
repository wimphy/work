package com.simon.wang.soap;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.ResultSet;

@Component
public class CNEFundamentalFixtureRefDAL {
    @PostConstruct
    public void initData() {
    }

    public ResultSet getData() {
        ResultSet response = null;
        return response;
    }

    @PreDestroy
    public void destroy(){

    }
}
