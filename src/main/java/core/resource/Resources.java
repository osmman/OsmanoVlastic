/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package core.resource;

import client.bank.BankServiceFactory;
import client.bank.secured.BankService;
import client.exchange.RateExchangeClient;
import client.exchange.RateExchangeClientImpl;
import client.flight.AosFlightDistanceClient;
import client.flight.FlightDistanceClient;
import client.maps.GoogleMapsClient;
import client.maps.MapsClient;
import client.print.bottomup.PrintServiceAdapter;
import client.print.topdown.PrintServiceV2Adapter;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 * <p/>
 * <p>
 * Example injection on a managed bean seats:
 * </p>
 * <p/>
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class Resources
{
    // use @SuppressWarnings to tell IDE to ignore warnings about seats not being referenced directly
    @SuppressWarnings("unused")
    @Produces
    @PersistenceContext(name = "primary")
    private EntityManager em;

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint)
    {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Produces
    public MapsClient produceMapsClient()
    {
        return new GoogleMapsClient();
    }

    @Produces
    public FlightDistanceClient produceDistanceClient()
    {
        return new AosFlightDistanceClient();
    }

    @Produces
    @Default
    public BankService produceBankClient()
    {
        return BankServiceFactory.create();
    }

    @Produces
    public RateExchangeClient produceExchangeClient()
    {
        return new RateExchangeClientImpl();
    }

    @Produces
    public PrintServiceAdapter producePrintClient()
    {
        PrintServiceAdapter ps = new PrintServiceAdapter();
        ps.setServiceUrl("http://127.0.0.1:8080/osmanvlastic/PrintService?Wsdl");
        return ps;
    }

    @Produces
    public PrintServiceV2Adapter producePrintClientV2()
    {
        PrintServiceV2Adapter ps = new PrintServiceV2Adapter();
        ps.setServiceUrl("http://127.0.0.1:8080/osmanvlastic/printServiceV2?Wsdl");
        return ps;
    }
}
