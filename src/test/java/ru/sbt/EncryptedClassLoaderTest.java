package ru.sbt;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.*;

public class EncryptedClassLoaderTest {

    EncryptedClassLoader encryptedClassLoader;

    @Before
    public void doBefore() {
        String key = "sdgndognadon02n4v039nviodn";
        String dir = "C:\\Users\\Roman\\IdeaProjects\\SBTHomeWork_7\\src\\test\\resources\\EncryptedTest\\";
        File file = new File(dir);
        assertTrue(file.exists());
        encryptedClassLoader = new EncryptedClassLoader(key,file,ClassLoader.getSystemClassLoader());
    }

    @Test
    public void testFindClass() throws Exception {
        encryptedClassLoader.encryptClass(Date.class);
        Class clazz = encryptedClassLoader.decryptClass("Date");
        encryptedClassLoader.findClass("Date");
        System.out.println(clazz.newInstance());
        assertEquals("Не тот класс!","java.util.Date",clazz.getName());
    }
}