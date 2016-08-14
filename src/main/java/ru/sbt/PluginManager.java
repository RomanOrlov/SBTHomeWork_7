package ru.sbt;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //todo Надо сделать так, чтобы интерфейс загружался системным класслоадером, а сам класс новым (URL класслоадером)
        URL url = new URL(pluginRootDirectory + pluginName+"/");
        URLClassLoader urlClassLoader = new PluginClassLoader(new URL[]{url});
        Class<?> aClass = urlClassLoader.loadClass(pluginClassName);
        return (Plugin) aClass.newInstance();
    }
}

