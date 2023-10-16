package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Order;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Optional<Order> findOne(int id){
        return orderRepository.findById(id);
    }

    @Transactional
    public void save(Order order){
        order.setOrderDate(new Date());
        orderRepository.save(order);
    }

    @Transactional
    public void update(int id, Order updatedOrder){
        updatedOrder.setId_order(id);
        orderRepository.save(updatedOrder);
    }

    @Transactional
    public void delete(int id){
        orderRepository.deleteById(id);
    }

    @Transactional
    public void setAmount(Integer idBookOrder, int amount){
        orderRepository.setAmount(idBookOrder, amount);
    }
    public int getIdBookOrder(Integer idBook, Integer idOrder){
        return orderRepository.getIdBookOrder(idBook, idOrder);
    }

    public Integer getBookAmount(Integer idBookOrder){
        return orderRepository.getBookAmount(idBookOrder);
    }

    public List<Order> findAllByUser(User user){
        return orderRepository.findAllByUser(user);
    }
}
