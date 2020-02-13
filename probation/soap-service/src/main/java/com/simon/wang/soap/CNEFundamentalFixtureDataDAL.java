package com.simon.wang.soap;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;

@Component
@RestController
public class CNEFundamentalFixtureDataDAL {
    @PostConstruct
    public void initData() {
    }

    //@ApiOperation(value = "getData")
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResultSet getData() {
        ResultSet response = null;
        return response;
    }

    @RequestMapping(value = "/gport", method = RequestMethod.GET)
    @ApiOperation(value = "Port Cost")
//    public String test( @ApiParam(value = "Calc Type")CalcType calcType,
//                        @ApiParam(value = "Port Ric")String portRIC,
//                        @ApiParam(value = "UNLOCODE")String unloCode,
//                        @ApiParam(value = "Vessel DWT")int vesselDWT,
//                        @ApiParam(value = "Event Type")EventType eventType,
//                        @ApiParam(value = "Cargo")String cargo,
//                        @ApiParam(value = "Field")String field) {
    public String test( CalcType calcType,
                        String portRIC,
                        String unloCode,
                        int vesselDWT,
                        EventType eventType,
                        String cargo,
                        String field) {
        return "12345 US$";
    }


}
