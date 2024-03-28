package com.severusnguyen.ohaha.repository;

import com.severusnguyen.ohaha.entity.OrderItem;
import com.severusnguyen.ohaha.entity.keys.KeyOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, KeyOrderItem> {

}
