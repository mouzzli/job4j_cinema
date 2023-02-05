package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest {
    private IndexController indexController;

    @BeforeEach
    public void initIndexController() {
        indexController = new IndexController();
    }

    @Test
    public void index() {
        String page = indexController.index();
        assertThat(page).isEqualTo("index");
    }

}