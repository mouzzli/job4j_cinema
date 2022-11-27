package ru.job4j.cinema.filter;

import java.util.Set;

public abstract class AbstractFilter {

    /**
     *  Проверяет содержание текущей ссылка на наличие адреса из сета, если совпадения есть то
     *  возвращает true, иначе false
     * @param uri - ссылка
     * @param values - Сет адресов
     * @return true если uri содержит значение из Set, иначе false
     */
    protected boolean filter(String uri, Set<String> values) {
        return values.stream().anyMatch(uri::endsWith);
    }
}
