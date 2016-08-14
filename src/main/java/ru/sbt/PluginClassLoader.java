package ru.sbt;

import java.net.URL;
import java.net.URLClassLoader;

class PluginClassLoader extends URLClassLoader {
    public PluginClassLoader(URL[] urls) {
        super(urls);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            return findClass(name);
        } catch (Exception ex) {
            // ignore
        }
        return super.loadClass(name);
    }
}
