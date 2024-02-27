package org.inspien.client;

import org.inspien.dto.order.SalesStatus;

public interface Client {
    String requestDataAndConnInfo(String data, String url);
    void insertData(SalesStatus salesStatus);
}
