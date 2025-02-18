package com.example.Bookstore.Service;

import com.example.Bookstore.Exception.UserNotFoundException;
import com.example.Bookstore.Model.*;
import com.example.Bookstore.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService{

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    CartRepository cartRepository;

    @Transactional // This annotation is used to perform a transaction on the method
    @Override
    public String createOrder(Long userId) { // createOrder method with userId as parameter
        Users user=usersRepo.findById(userId).orElse(null);
        if (user==null)
            throw new UserNotFoundException("User not found!");
        Cart cart=cartRepository.findByUserId(userId).orElse(null);
        if(cart!=null)
        {
            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem cartItem : cart.getCartItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setBook(cartItem.getBook());
                cartItem.getBook().setStock(cartItem.getBook().getStock()-cartItem.getQuantity());
                bookRepository.save(cartItem.getBook());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getBook().getPrice());
                orderItems.add(orderItem);
            }
            System.out.println(orderItems);

            double totalAmount=0;
            for (OrderItem item : orderItems) {
                totalAmount +=item.getPrice()*item.getQuantity();
            }

            Order order = new Order();
            order.setUsers(user);
            order.setOrderItems(orderItems);
            order.setTotalAmount(totalAmount);
            order.setOrderStatus(OrderStatus.PENDING);
            order.setCreated_at(LocalDateTime.now());

            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
                orderItemRepository.save(orderItem);
            }

            List<CartItem> cartItems = cart.getCartItems();
            System.out.println(cartItems);
            //for (CartItem cartItem : cartItems) {
                cartItemRepository.deleteAll(cartItems);
            //    cartItemRepository.deleteById(cartItem.getId());
            //}
            cart.setCartItems(new ArrayList<>());
            cartRepository.save(cart);
            orderRepository.save(order);
            return "Order placed sucessfully!";
        }
        else
        {
            return "Order failed!";
        }
    }

}

