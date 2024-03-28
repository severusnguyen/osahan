package com.severusnguyen.ohaha.service;

import com.severusnguyen.ohaha.entity.*;
import com.severusnguyen.ohaha.entity.keys.KeyOrderItem;
import com.severusnguyen.ohaha.payload.request.OrderRequest;
import com.severusnguyen.ohaha.repository.OrderItemRepository;
import com.severusnguyen.ohaha.repository.OrderRepository;
import com.severusnguyen.ohaha.service.imp.OrderServiceImp;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional //giúp callback lại nếu insert bị lỗi
public class OrderService implements OrderServiceImp {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public boolean insertOrder(OrderRequest orderRequest) {
        try {
            //Nhiều khóa ngoại nên cần tạo các entity để gán khóa chính
            Users users = new Users();
            users.setId(orderRequest.getUserId());

            Restaurant restaurant = new Restaurant();
            restaurant.setId(orderRequest.getRestId());

            Orders orders = new Orders();
            orders.setUsers(users);
            orders.setRestaurant(restaurant);

            orderRepository.save(orders);

            List<OrderItem> items = new ArrayList<>();

            for (int idFood : orderRequest.getFoodIds()) {
                Food food = new Food();
                food.setId(idFood);

                OrderItem orderItem = new OrderItem();
                KeyOrderItem keyOrderItem = new KeyOrderItem(orders.getId(), idFood); // là @EmbeddedId nên phải thông qua KeyOrderItem
                orderItem.setKeyOrderItem(keyOrderItem);

                items.add(orderItem);
            }

            orderItemRepository.saveAll(items);

            return true;
        }catch (Exception e){
            System.out.println("Error insert order: " + e.getMessage());
            return false;
        }

    }
}
