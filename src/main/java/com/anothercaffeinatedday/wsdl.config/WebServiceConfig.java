/*
 * Copyright (c) 2020 Timothy Stone.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.anothercaffeinatedday.wsdl.config;

import com.anothercaffeinatedday.wsdl.CustomerOrdersWSImpl;
import com.anothercaffeinatedday.wsdl.handlers.SiteHandler;
import java.util.ArrayList;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.SOAPBinding;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Configuration of the Web Service.
 *
 * <p>The endpoint of the WSDL First WS is presented. Additionally, the use of a JAX-WS Handler is presented.</p>
 */
@Configuration
public class WebServiceConfig {

  @Autowired
  private Bus bus;

  /**
   * The Web Service endpoint URL.
   *
   * <p>The CustomerOrders service endpoint.</p>
   *
   * @return Endpoint the customerwebservice endpoint with a site handler
   */
  @Bean
  public Endpoint endpoint() {
    Endpoint endpoint = new EndpointImpl(bus, new CustomerOrdersWSImpl());
    endpoint.publish("/customerordersservice");

    SOAPBinding binding = (SOAPBinding) endpoint.getBinding();
    ArrayList<Handler> handlers = new ArrayList<>();
    handlers.add(new SiteHandler());
    binding.setHandlerChain(handlers);

    return endpoint;
  }
}
