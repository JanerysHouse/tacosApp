package ru.rgroupe.springinaction.tacosapp.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.rgroupe.springinaction.tacosapp.entities.TacosOrder;
import ru.rgroupe.springinaction.tacosapp.repositories.OrderRepository;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes("tacosOrder")
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String inActionTacos(@Valid TacosOrder tacosOrder, Errors errors, SessionStatus status) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepository.save(tacosOrder);
        status.setComplete();

        return "redirect:/";
    }
}
