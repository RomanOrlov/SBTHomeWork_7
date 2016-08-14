package ru.sbt;

/**
 * Класс специально находится здесь, чтобы проверить на то, что они будут загружены URLClassLoader
 */
public class JooqPlugin implements Plugin {
    public String doUsefull() {
        return  "JooqPlugin";
    }
}
