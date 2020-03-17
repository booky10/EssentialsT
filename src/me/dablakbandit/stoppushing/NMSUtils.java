// 
// Decompiled by Procyon v0.5.36
// 

package me.dablakbandit.stoppushing;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

public class NMSUtils
{
    private static String version;
    private static Class<?> c;

    static {
        NMSUtils.version = getVersion();
        NMSUtils.c = getOBCClass("block.CraftBlock");
        getMethod(Objects.requireNonNull(NMSUtils.c), "getNMSBlock");
    }
    
    public static String getVersion() {
        if (NMSUtils.version != null) {
            return NMSUtils.version;
        }
        final String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf(46) + 1) + ".";
    }
    
    public static Class<?> getNMSClassWithException(final String className) throws Exception {
        return Class.forName("net.minecraft.server." + getVersion() + className);
    }
    
    public static Class<?> getNMSClass(final String className) {
        try {
            return getNMSClassWithException(className);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Class<?> getNMSClassSilent(final String className) {
        try {
            return getNMSClassWithException(className);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static Class<?> getNMSClassSilent(final String className, final String embedded) {
        try {
            return getNMSClassWithException(className);
        }
        catch (Exception e) {
            return getInnerClassSilent(getNMSClassSilent(embedded), className);
        }
    }
    
    public static Class<?> getOBCClassWithException(final String className) throws Exception {
        return Class.forName("org.bukkit.craftbukkit." + getVersion() + className);
    }
    
    public static Class<?> getOBCClass(final String className) {
        try {
            return getOBCClassWithException(className);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Class<?> getOBCClassSilent(final String className) {
        try {
            return getOBCClassWithException(className);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static Object getHandle(final Object obj) {
        try {
            return Objects.requireNonNull(getMethod(obj.getClass(), "getHandle")).invoke(obj);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getFieldWithException(final Class<?> clazz, final String name) throws Exception {
        Field[] declaredFields;
        for (int length = (declaredFields = clazz.getDeclaredFields()).length, i = 0; i < length; ++i) {
            final Field field = declaredFields[i];
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                final Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                int modifiers = modifiersField.getInt(field);
                modifiers &= 0xFFFFFFEF;
                modifiersField.setInt(field, modifiers);
                return field;
            }
        }
        Field[] fields;
        for (int length2 = (fields = clazz.getFields()).length, j = 0; j < length2; ++j) {
            final Field field = fields[j];
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                final Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                int modifiers = modifiersField.getInt(field);
                modifiers &= 0xFFFFFFEF;
                modifiersField.setInt(field, modifiers);
                return field;
            }
        }
        throw new Exception("Field Not Found");
    }
    
    public static Field getField(final Class<?> clazz, final String name) {
        try {
            return getFieldWithException(clazz, name);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Field getFieldSilent(final Class<?> clazz, final String name) {
        try {
            return getFieldWithException(clazz, name);
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static Field getFirstFieldOfTypeWithException(final Class<?> clazz, final Class<?> type) throws Exception {
        Field[] declaredFields;
        for (int length = (declaredFields = clazz.getDeclaredFields()).length, i = 0; i < length; ++i) {
            final Field field = declaredFields[i];
            if (field.getType().equals(type)) {
                field.setAccessible(true);
                final Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                int modifiers = modifiersField.getInt(field);
                modifiers &= 0xFFFFFFEF;
                modifiersField.setInt(field, modifiers);
                return field;
            }
        }
        throw new Exception("Field Not Found");
    }

    public static Method getMethod(final Class<?> clazz, final String name, final Class<?>... args) {
        Method[] declaredMethods;
        for (int length = (declaredMethods = clazz.getDeclaredMethods()).length, i = 0; i < length; ++i) {
            final Method m = declaredMethods[i];
            if (m.getName().equals(name) && ((args.length == 0 && m.getParameterTypes().length == 0) || ClassListEqual(args, m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
        }
        Method[] methods;
        for (int length2 = (methods = clazz.getMethods()).length, j = 0; j < length2; ++j) {
            final Method m = methods[j];
            if (m.getName().equals(name) && ((args.length == 0 && m.getParameterTypes().length == 0) || ClassListEqual(args, m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }
    
    public static Method getMethodSilent(final Class<?> clazz, final String name, final Class<?>... args) {
        try {
            return getMethod(clazz, name, args);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static boolean ClassListEqual(final Class<?>[] l1, final Class<?>[] l2) {
        if (l1.length != l2.length) {
            return false;
        }
        for (int i = 0; i < l1.length; ++i) {
            if (l1[i] != l2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static Class<?> getInnerClassWithException(final Class<?> c, final String className) {
        Class<?>[] declaredClasses;
        for (int length = (declaredClasses = c.getDeclaredClasses()).length, i = 0; i < length; ++i) {
            final Class<?> cl = declaredClasses[i];
            if (cl.getSimpleName().equals(className)) {
                return cl;
            }
        }
        return null;
    }

    public static Class<?> getInnerClassSilent(final Class<?> c, final String className) {
        try {
            return getInnerClassWithException(c, className);
        }
        catch (Exception ex) {
            return null;
        }
    }

}
