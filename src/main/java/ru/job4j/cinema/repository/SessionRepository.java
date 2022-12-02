package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище сеансов
 *
 * @see ru.job4j.cinema.model.Session
 */
public interface SessionRepository {

    /**
     * Выполняет сохранение сеанса. При успешном сохранении возвращает Optional
     * с объектом сеанса, у которого проинициализировано поле id. Иначе возвращает Optional.empty()
     * Сохранение может не произойти если, у сохраняемого сеанса id совпадает с id сеанса находящимся в репозитории
     *
     * @param session - модель фильма
     * @return Optional.of(Session) при успешном сохранении, иначе Optional.empty()
     */
    Optional<Session> save(Session session);

    /**
     * Возвращает список сеансов
     *
     * @return список сеансов List<Session>
     */
    List<Session> findAll();

    /**
     * Удаляет сеанс из хранилища сеансов по id, при успешном удалении
     * возвращает true, иначе false.
     * Вернёт false если сеанс с данным id не содержится в репозитории
     *
     * @param id - id сеанса
     * @return true при успешном удалении, иначе false
     */
    boolean deleteById(int id);

    /**
     * Выполняет поиск сеанса с заданным id находящимся в данном репозитории.
     * При успешном поиске возвращает Optional с объектом пользователя.
     * Иначе возвращает Optional.empty()
     * Поиск может закончиться неудачно если сеанс с заданным id не содержится в данном репозитории
     *
     * @param id - id сеанса
     * @return Optional.of(Session) если сеанс найден, иначе Optional.empty()
     */
    Optional<Session> finById(int id);

}
