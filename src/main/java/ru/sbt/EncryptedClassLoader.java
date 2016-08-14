package ru.sbt;

import java.io.*;
import java.nio.file.Files;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            return decryptClass(name);
        } catch (Exception ex) {
            //ignore
        }
        return super.findClass(name);
    }

    public void encryptClass(Class<?> clazz) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(new File(dir,clazz.getSimpleName()))
        ) {
            objectOutputStream.writeObject(clazz);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byte[] encrypted = encrypt(bytes);
            fileOutputStream.write(encrypted);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Class<?> decryptClass(String name) {
        File fullDir = new File(dir,name);
        try {
            byte[] bytes = Files.readAllBytes(fullDir.toPath());
            byte[] decrypted = decrypt(bytes);
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decrypted);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)){
                Object clazz = objectInputStream.readObject();
                return (Class<?>)clazz;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(" ласс не был найден");
    }

    /**
     * XOR encryption
     * @return encrypted bytes
     */
    protected final byte[] encrypt(byte[] bytes) {
        byte[] keyBytes = key.getBytes();
        byte[] encryptedBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            encryptedBytes[i] = (byte) (bytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        return encryptedBytes;
    }

    protected final byte[] decrypt(byte[] bytes) {
        byte[] decryptedByres = new byte[bytes.length];
        byte[] keyBytes = key.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            decryptedByres[i] = (byte) (bytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        return decryptedByres;
    }
}
