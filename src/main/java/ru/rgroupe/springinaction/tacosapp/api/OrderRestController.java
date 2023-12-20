package ru.rgroupe.springinaction.tacosapp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.rgroupe.springinaction.tacosapp.entities.TacosOrder;
import ru.rgroupe.springinaction.tacosapp.repositories.OrderRepository;

@RestController
@RequestMapping(path="/api/orders",
        produces="application/json")
@CrossOrigin(origins="http://tacocloud:8080")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderRepository orderRepository;

    @GetMapping(produces = "application/json")
    public Iterable<TacosOrder> allOrders() {
        return orderRepository.findAll();
    }

    @PostMapping(consumes ="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacosOrder postOrder(@RequestBody TacosOrder tacosOrder) {
        return orderRepository.save(tacosOrder);
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public TacosOrder putOrder(@PathVariable("orderId") Long orderId,
                               @RequestBody TacosOrder tacosOrder) {
        tacosOrder.setId(orderId);
        return orderRepository.save(tacosOrder);
    }

    @PatchMapping(path="/{orderId}", consumes ="application/json")
    public TacosOrder patchOrder(@PathVariable("orderId") Long orderId,
                                 @RequestBody TacosOrder patchOrder) {

        TacosOrder order = orderRepository.findById(orderId).get();

        if (patchOrder.getDeliveryName() != null) {
            order.setDeliveryName(patchOrder.getDeliveryName());
        }

        order.setDeliveryStreet(defaultIfNull(patchOrder.getDeliveryStreet(), order.getDeliveryStreet()));
        order.setDeliveryCity(defaultIfNull(patchOrder.getDeliveryCity(), order.getDeliveryCity()));
        order.setDeliveryState(defaultIfNull(patchOrder.getDeliveryState(), order.getDeliveryState()));
        order.setDeliveryZip(defaultIfNull(patchOrder.getDeliveryZip(), order.getDeliveryZip()));

        order.setCcNumber(defaultIfNull(patchOrder.getCcNumber(), order.getCcNumber()));
        order.setCcExpiration(defaultIfNull(patchOrder.getCcExpiration(), order.getCcExpiration()));
        order.setCcCVV(defaultIfNull(patchOrder.getCcCVV(), order.getCcCVV()));

        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {
        }
    }

    private <T> T defaultIfNull(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
