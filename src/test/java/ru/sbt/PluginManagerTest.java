package ru.sbt;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PluginManagerTest {
    String[] plugins = new String[]{"HibernatePlugin", "JooqPlugin", "VaadinPlugin"};
    PluginManager pluginManager;

    @Before
    public void doBefore() {
        String resourcePath = "file://C:/Users/Roman/IdeaProjects/SBTHomeWork_7/src/test/resources/";
        pluginManager = new PluginManager(resourcePath);
    }

    @Test
    public void testLoad() throws Exception {
        List<Plugin> loadedPlugins = new ArrayList<>();
        try {
            for (String pluginName : plugins) {
                loadedPlugins.add(pluginManager.load(pluginName, "ru.sbt." + pluginName));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("�� ����� �������� ������� ��������� ������");
        }
        try {
            loadedPlugins.forEach(plugin -> assertEquals("����� �� ��������� � ���������",plugin.getClass().getSimpleName() ,plugin.doUsefull()));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("�� ����� ������� ������� ����� ���������� Plugin ��������� ������");
        }
        try {
            loadedPlugins.forEach(plugin -> {
                ClassLoader classLoader = plugin.getClass().getClassLoader();
                System.out.println(classLoader);
                assertNotEquals("��� �������� ��������� �������������", ClassLoader.getSystemClassLoader(), classLoader);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("�� ����� ������� ������� ����� ���������� Plugin ��������� ������");
        }
    }
}