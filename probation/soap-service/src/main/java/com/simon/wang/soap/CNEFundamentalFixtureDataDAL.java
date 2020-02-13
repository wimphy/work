package com.simon.wang.soap;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;

@Component
public class CNEFundamentalFixtureDataDAL {
    @PostConstruct
    public void initData() {
    }

    public ResultSet getData() {
        ResultSet response = null;
        return response;
    }
}
