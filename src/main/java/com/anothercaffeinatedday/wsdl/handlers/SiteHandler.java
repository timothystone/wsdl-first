/*
 * Copyright (c) 2021 Timothy Stone.
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

package com.anothercaffeinatedday.wsdl.handlers;

import java.util.Iterator;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle the SOAP Message from the requesting site.
 *
 * <p>A client may pass site information to the service. Handle the sites requests uniquely.
 */
public class SiteHandler implements SOAPHandler<SOAPMessageContext> {

  public static final Logger LOGGER = LoggerFactory.getLogger(SiteHandler.class);

  @Override
  public Set<QName> getHeaders() {
    LOGGER.info("In getHeaders");
    return null;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext soapMessageContext) {
    LOGGER.info("In handleMessage");
    Boolean isResponse = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    if (!isResponse) {
      SOAPMessage message = soapMessageContext.getMessage();
      try {
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        SOAPHeader header = envelope.getHeader();
        Iterator<Node> childElements = header.getChildElements();
        while (childElements.hasNext()) {
          Node node = childElements.next();
          String name = node.getLocalName();
          if ("SiteName".equals(name)) {
            LOGGER.info("Site Name is {}", node.getValue());
          }

        }
      } catch (SOAPException e) {
        LOGGER.error(e.getMessage());
      }
    } else {
      LOGGER.info("Response on the way.");
    }
    return true;
  }

  @Override
  public boolean handleFault(SOAPMessageContext soapMessageContext) {
    LOGGER.info("In handleFault");
    return false;
  }

  @Override
  public void close(MessageContext messageContext) {
    LOGGER.info("In close");
  }
}
