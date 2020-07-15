package com.simon.wang.soap;

import com.wang.simon.probation.soap.SoapTestResponse;
import com.wang.simon.probation.soap.StandardRequest;
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

    private final String ns = "http://www.simon.wang.com/probation/soap";

    @PayloadRoot(namespace = ns, localPart = "Soap_Test_Request")
    @ResponsePayload
    public SoapTestResponse GPort_1(@RequestPayload StandardRequest request) {
        SoapTestResponse retVal;

        try {
            if (!request.isGmon()) {

                // Get the data from the database
                ResultSet dataFromDB = dal.getData();

                // Map the data to the response
                retVal = mapper.DoMapping(dataFromDB);
            } else {
                retVal = new SoapTestResponse();
            }
            return retVal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
