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

package com.anothercaffeinatedday.wsdl;

import com.anothercaffeinatedday.CreateOrdersRequest;
import com.anothercaffeinatedday.CreateOrdersResponse;
import com.anothercaffeinatedday.CustomerOrdersPortType;
import com.anothercaffeinatedday.GetOrdersRequest;
import com.anothercaffeinatedday.GetOrdersResponse;
import com.anothercaffeinatedday.Order;
import com.anothercaffeinatedday.Product;
import org.apache.cxf.feature.Features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Features(features = "org.apache.cxf.ext.logging.LoggingFeature")
public class CustomerOrdersWSImpl implements CustomerOrdersPortType {

    Map<Integer, List<Order>> customerOrders = new HashMap<>();
    int currentId;

    public CustomerOrdersWSImpl() {
        init();
    }

    public void init() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setId(1);

        Product product = new Product();
        product.setId("1");
        product.setDescription("iPhone 12");
        product.setQuantity(3);

        order.getProducts().add(product);

        customerOrders.put(++currentId, orders);

        orders.add(order);
    }

    @Override
    public GetOrdersResponse getOrders(GetOrdersRequest request) {
        Integer customerId = request.getCustomerId();
        List<Order> orders = customerOrders.get(customerId);

        GetOrdersResponse response = new GetOrdersResponse();
        response.getOrders().addAll(orders);
        return response;
    }

    @Override
    public CreateOrdersResponse createOrders(CreateOrdersRequest request) {
        int customerId = request.getCustomerId();
        Order order = request.getOrder();
        order.setId(++currentId);

        List<Order> orders  = customerOrders.get(customerId);
        orders.add(order);

        CreateOrdersResponse createOrdersResponse = new CreateOrdersResponse();
        createOrdersResponse.setResponse(true);

        return createOrdersResponse;
    }
}
