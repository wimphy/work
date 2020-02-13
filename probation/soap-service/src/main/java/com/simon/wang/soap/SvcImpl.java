package com.simon.wang.soap;

import com.webservices.reuters.cnefundamental_1_gport_1.StandardRequest;
import com.webservices.reuters.cnefundamental_1_gport_1.CNEFundamental1GPort1Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.sql.ResultSet;

@Endpoint
public class SvcImpl {
    private @Autowired CNEFundamentalFixtureRefDAL dal;
    private @Autowired CNEFundamentalFixtureDataMapper mapper;

    private final String ns = "http://www.reuters.webservices.com/CNEFundamental_1_GPort_1";

    @PayloadRoot(namespace = ns, localPart = "CNEFundamental_1_GPort_1_Request")
    @ResponsePayload
    public CNEFundamental1GPort1Response GPort_1(@RequestPayload StandardRequest request) {
        CNEFundamental1GPort1Response retVal;

        try {
            if (!request.isGmon()) {

                // Get the data from the database
                ResultSet dataFromDB = dal.getData();

                // Map the data to the response
                retVal = mapper.DoMapping(dataFromDB);
            } else {
                retVal = new CNEFundamental1GPort1Response();
            }
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
