package org.inspien.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SalesStatus {
    private List<Order> orders;
    private List<Item> items;
}
