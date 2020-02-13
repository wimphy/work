package com.simon.wang.soap;

import com.webservices.reuters.cnefundamental_1_fixturedata_1.CNEFundamental1FixtureData1Response;
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
