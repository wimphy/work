package com.simon.wang.soap;

import com.webservices.reuters.cnefundamental_1_fixturedata_1.CNEFundamental1FixtureData1Response;
import com.webservices.reuters.cnefundamental_1_gport_1.CNEFundamental1GPort1Response;
import com.webservices.reuters.cnefundamental_1_gport_1.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@Component
public class CNEFundamentalFixtureDataMapper {

    public CNEFundamental1GPort1Response DoMapping(ResultSet dataFromDB) {
        return null;
    }
}
