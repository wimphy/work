package com.simon.wang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoapApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapApplication.class, args);
	}

	/*
	curl --location --request POST 'http://localhost:8080/CNEFundamental' \
    --header 'Content-Type: text/xml' \
    --data-raw '<soap:Envelope xmlns:pf="http://www.reuters.webservices.com/PublicForm" xmlns:wsa="http://schemas.xmlsoap.org/ws/2004/08/addressing" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:ts="http://schemas.reuters.com/ns/2005/08/infrastructure/tornado_soap" xmlns:cpu="http://schemas.reuters.com/ns/2007/10/cp/user_identity" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
        <soap:Header>
            <timings  xmlns="http://schemas.reuters.com/ns/2009/08/infrastructure/timings" TTL="0" />
        </soap:Header>
        <soap:Body>
			<Soap_Test_Request xmlns="http://www.simon.wang.com/probation/soap">
				<Gmon>false</Gmon>
				<PortRIC>1</PortRIC>
			</Soap_Test_Request>
		</soap:Body>
	</soap:Envelope>'
*/
}
