package ru.sbt;

public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) {
        //todo Надо сделать так, чтобы интерфейс загружался системным класслоадером, а сам класс новым (URL класслоадером)
        return null;
    }
}
