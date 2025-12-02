package com.project.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.ecommerce.domain.CartItem;
import com.project.ecommerce.domain.Order;
import com.project.ecommerce.domain.OrderItem;
import com.project.ecommerce.domain.User;
import com.project.ecommerce.domain.response.order.ResGetOderDTO;
import com.project.ecommerce.repository.CartItemRepository;
import com.project.ecommerce.repository.OrderItemRepository;
import com.project.ecommerce.repository.OrderRepository;
import com.project.ecommerce.ultil.SecurityUtil;
import com.project.ecommerce.ultil.constant.StatusEnum;
import com.project.ecommerce.ultil.constant.PaymentStatusEnum;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;


    public Order createOder(String shippingAdress, List<Long> cartItemId){
        String email = this.securityUtil.getCurrentUserLogin().isPresent() ? this.securityUtil.getCurrentUserLogin().get() : null;
        User user = this.userService.findByUsername(email);

        List<CartItem> cartItems= cartItemRepository.findByIdIn(cartItemId);

        System.out.println(cartItems);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("cart empty");
        }
        Order order= new Order();
        order.setUser(user);
        order.setStatus(StatusEnum.PENDDING);
        order.setShippingAddress(shippingAdress);
        order.setPaymentStatus(PaymentStatusEnum.UNPAID);


        List<OrderItem> orderItems = new ArrayList<>();
        Double total=0.0;

        for(CartItem i: cartItems){
            OrderItem orderItem=new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(i.getProduct());
            orderItem.setQuantity(i.getQuantity());
            orderItem.setPrice(i.getUnitPrice());
            orderItem.setSubTotal(i.getUnitPrice()*i.getQuantity());
            total+=orderItem.getSubTotal();

            orderItems.add(orderItem);
        }
        order.setTotalPrice(total);
        order.setItems(orderItems);

        this.cartItemRepository.deleteAll(cartItems);

        return this.orderRepository.save(order);
    }

    public Order getOrder(Long id){
    Optional<Order> orderOptional= this.orderRepository.findById(id);
    return orderOptional.isPresent() ? orderOptional.get() : null;
    }

    public ResGetOderDTO convertToResGetOderDTO(Order order){
        ResGetOderDTO res=new ResGetOderDTO();
        ResGetOderDTO.UserInner userInner=new ResGetOderDTO.UserInner();

        res.setId(order.getId());

        userInner.setId(order.getUser().getId());
        userInner.setName(order.getUser().getName());
        userInner.setEmail(order.getUser().getEmail());
        res.setUser(userInner);

        res.setStatus(order.getStatus());
        res.setTotalPrice(order.getTotalPrice());
        res.setPaymentStatus(order.getPaymentStatus());
        res.setShippingAddress(order.getShippingAddress());
        return res;
    }
}
