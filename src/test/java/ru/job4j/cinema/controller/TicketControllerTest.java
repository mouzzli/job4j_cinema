package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TicketControllerTest {
    private TicketService ticketService;
    private FilmSessionService filmSessionService;
    private TicketController ticketController;

    @BeforeEach
    public void initTicketController() {
        ticketService = Mockito.mock(TicketService.class);
        filmSessionService = Mockito.mock(FilmSessionService.class);
        ticketController = new TicketController(ticketService, filmSessionService);
    }

    @Test
    public void bookTicketWhenEmptyTicket() {
        Model model = Mockito.mock(Model.class);
        Ticket ticket = Mockito.mock(Ticket.class);
        Mockito.when(ticketService.save(ticket)).thenReturn(Optional.empty());
        String page = ticketController.bookTicket(model, ticket);
        Mockito.verify(model).addAttribute("message", "Не удалось приобрести билет на заданное место. Вероятно оно уже занято. Перейдите на страницу бронирования билетов и попробуйте снова.");
        assertThat(page).isEqualTo("errors/404");
    }

    @Test
    public void bookTicket() {
        FilmSessionDto filmSessionDto = Mockito.mock(FilmSessionDto.class);
        Model model = Mockito.mock(Model.class);
        Ticket ticket = Mockito.mock(Ticket.class);
        Mockito.when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));
        Mockito.when(filmSessionService.findById(ticket.getSessionId())).thenReturn(filmSessionDto);
        String page = ticketController.bookTicket(model, ticket);
        Mockito.verify(model).addAttribute("ticket", ticket);
        Mockito.verify(model).addAttribute("filmSession", filmSessionDto);
        assertThat(page).isEqualTo("ticket");
    }

}