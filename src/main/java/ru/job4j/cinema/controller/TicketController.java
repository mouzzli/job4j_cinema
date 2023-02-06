package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;

@Controller
public class TicketController {
    private final TicketService ticketService;
    private final FilmSessionService filmSessionService;

    public TicketController(TicketService ticketService, FilmSessionService filmSessionService) {
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
    }

    @PostMapping("/bookTicket")
    public String bookTicket(Model model, @ModelAttribute Ticket ticket) {
        try {
            var optionalTicket = ticketService.save(ticket);
            model.addAttribute("ticket", optionalTicket);
            model.addAttribute("filmSession", filmSessionService.findById(optionalTicket.getSessionId()));
            return "ticket";
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }
}
