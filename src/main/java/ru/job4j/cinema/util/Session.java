package ru.job4j.cinema.util;

import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

public class Session {

    private Session() {
    }

    /**
     *  Добавляет в модель данные текущей сессии пользователя
     * @param model - модель данных
     * @param session - сессия пользователя
     */
    public static void setSession(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}
