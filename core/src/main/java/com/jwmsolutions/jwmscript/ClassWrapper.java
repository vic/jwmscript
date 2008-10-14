package com.jwmsolutions.jwmscript;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

public class ClassWrapper {

	public Class<Object> wrappedClass;

	public ClassWrapper(Class cls) {
		this.wrappedClass = cls;
	}

	public Class<Object> getWrappedClass() {
		return wrappedClass;
	}

	public void setWrappedClass(Class<Object> wrappedClass) {
		this.wrappedClass = wrappedClass;
	}

	public Class asSubclass(Class arg0) {
		return wrappedClass.asSubclass(arg0);
	}

	public Object cast(Object arg0) {
		return wrappedClass.cast(arg0);
	}

	public boolean desiredAssertionStatus() {
		return wrappedClass.desiredAssertionStatus();
	}

	public boolean equals(Object arg0) {
		return wrappedClass.equals(arg0);
	}

	public Annotation getAnnotation(Class arg0) {
		return wrappedClass.getAnnotation(arg0);
	}

	public Annotation[] getAnnotations() {
		return wrappedClass.getAnnotations();
	}

	public String getCanonicalName() {
		return wrappedClass.getCanonicalName();
	}

	public Class[] getClasses() {
		return wrappedClass.getClasses();
	}

	public ClassLoader getClassLoader() {
		return wrappedClass.getClassLoader();
	}

	public Class getComponentType() {
		return wrappedClass.getComponentType();
	}

	public Constructor<Object> getConstructor(Class... arg0)
			throws NoSuchMethodException, SecurityException {
		return wrappedClass.getConstructor(arg0);
	}

	public Constructor[] getConstructors() throws SecurityException {
		return wrappedClass.getConstructors();
	}

	public Annotation[] getDeclaredAnnotations() {
		return wrappedClass.getDeclaredAnnotations();
	}

	public Class[] getDeclaredClasses() throws SecurityException {
		return wrappedClass.getDeclaredClasses();
	}

	public Constructor<Object> getDeclaredConstructor(Class... arg0)
			throws NoSuchMethodException, SecurityException {
		return wrappedClass.getDeclaredConstructor(arg0);
	}

	public Constructor[] getDeclaredConstructors() throws SecurityException {
		return wrappedClass.getDeclaredConstructors();
	}

	public Field getDeclaredField(String arg0) throws NoSuchFieldException,
			SecurityException {
		return wrappedClass.getDeclaredField(arg0);
	}

	public Field[] getDeclaredFields() throws SecurityException {
		return wrappedClass.getDeclaredFields();
	}

	public Method getDeclaredMethod(String arg0, Class... arg1)
			throws NoSuchMethodException, SecurityException {
		return wrappedClass.getDeclaredMethod(arg0, arg1);
	}

	public Method[] getDeclaredMethods() throws SecurityException {
		return wrappedClass.getDeclaredMethods();
	}

	public Class getDeclaringClass() {
		return wrappedClass.getDeclaringClass();
	}

	public Class getEnclosingClass() {
		return wrappedClass.getEnclosingClass();
	}

	public Constructor getEnclosingConstructor() {
		return wrappedClass.getEnclosingConstructor();
	}

	public Method getEnclosingMethod() {
		return wrappedClass.getEnclosingMethod();
	}

	public Object[] getEnumConstants() {
		return wrappedClass.getEnumConstants();
	}

	public Field getField(String arg0) throws NoSuchFieldException,
			SecurityException {
		return wrappedClass.getField(arg0);
	}

	public Field[] getFields() throws SecurityException {
		return wrappedClass.getFields();
	}

	public Type[] getGenericInterfaces() {
		return wrappedClass.getGenericInterfaces();
	}

	public Type getGenericSuperclass() {
		return wrappedClass.getGenericSuperclass();
	}

	public Class[] getInterfaces() {
		return wrappedClass.getInterfaces();
	}

	public Method getMethod(String arg0, Class... arg1)
			throws NoSuchMethodException, SecurityException {
		return wrappedClass.getMethod(arg0, arg1);
	}

	public Method[] getMethods() throws SecurityException {
		return wrappedClass.getMethods();
	}

	public int getModifiers() {
		return wrappedClass.getModifiers();
	}

	public String getName() {
		return wrappedClass.getName();
	}

	public Package getPackage() {
		return wrappedClass.getPackage();
	}

	public ProtectionDomain getProtectionDomain() {
		return wrappedClass.getProtectionDomain();
	}

	public URL getResource(String arg0) {
		return wrappedClass.getResource(arg0);
	}

	public InputStream getResourceAsStream(String arg0) {
		return wrappedClass.getResourceAsStream(arg0);
	}

	public Object[] getSigners() {
		return wrappedClass.getSigners();
	}

	public String getSimpleName() {
		return wrappedClass.getSimpleName();
	}

	public Class getSuperclass() {
		return wrappedClass.getSuperclass();
	}

	public TypeVariable[] getTypeParameters() {
		return wrappedClass.getTypeParameters();
	}

	public int hashCode() {
		return wrappedClass.hashCode();
	}

	public boolean isAnnotation() {
		return wrappedClass.isAnnotation();
	}

	public boolean isAnnotationPresent(Class arg0) {
		return wrappedClass.isAnnotationPresent(arg0);
	}

	public boolean isAnonymousClass() {
		return wrappedClass.isAnonymousClass();
	}

	public boolean isArray() {
		return wrappedClass.isArray();
	}

	public boolean isAssignableFrom(Class arg0) {
		return wrappedClass.isAssignableFrom(arg0);
	}

	public boolean isEnum() {
		return wrappedClass.isEnum();
	}

	public boolean isInstance(Object arg0) {
		return wrappedClass.isInstance(arg0);
	}

	public boolean isInterface() {
		return wrappedClass.isInterface();
	}

	public boolean isLocalClass() {
		return wrappedClass.isLocalClass();
	}

	public boolean isMemberClass() {
		return wrappedClass.isMemberClass();
	}

	public boolean isPrimitive() {
		return wrappedClass.isPrimitive();
	}

	public boolean isSynthetic() {
		return wrappedClass.isSynthetic();
	}

	public Object newInstance() throws InstantiationException,
			IllegalAccessException {
		return wrappedClass.newInstance();
	}

	public String toString() {
		return wrappedClass.toString();
	}

	public Set<String> getUniqueMethodNames() {
		Method[] methods = wrappedClass.getMethods();
		Set<String> names = new HashSet<String>();
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			names.add(m.getName());
		}
		return names;
	}

	public Set<String> getFieldNames() {
		Field[] fields = wrappedClass.getFields();
		Set<String> names = new HashSet<String>();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			names.add(f.getName());
		}
		return names;
	}

	public Object getStaticField(String name) throws IllegalArgumentException,
			SecurityException, IllegalAccessException, NoSuchFieldException {
		return wrappedClass.getDeclaredField(name).get(null);
	}

	public Object getInstanceField(Object obj, String name)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, NoSuchFieldException {
		return wrappedClass.getDeclaredField(name).get(obj);
	}

	public Object callStaticMethod(String name, Object... arguments)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		return callInstanceMethod(null, name, arguments);
	}

	public Object callInstanceMethod(Object target, String name,
			Object... arguments) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Method[] methods = wrappedClass.getMethods();
		Method applicableMethod = null;
		Object[] coercedArguments = new Object[arguments.length];

		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];

			if (m.getName().equals(name)) {
				Class[] paramTypes = m.getParameterTypes();
				if (paramTypes.length == arguments.length) {
					int j = 0;
					while (j < paramTypes.length) {
						if (!argumentMatchType(arguments, paramTypes,
								coercedArguments, j)) {
							// System.err.println("[class '" + m_class.getName()
							// + "' [method '" + name + "'] Failed matching '" +
							// arguments[j].getClass() + "' with '" +
							// paramTypes[j] + "'");
							break;
						}
						j++;
					}
					if (j == paramTypes.length) {
						if (applicableMethod == null) {
							applicableMethod = m;
						} else {
							throw new NoSuchMethodError(
									"More than one method named "
											+ name
											+ " is applicable on provided arguments");
						}
					}
				}
			}
		}
		if (applicableMethod != null) {
			return applicableMethod.invoke(target, coercedArguments);
		}
		throw new NoSuchMethodError("No method named " + name
				+ " is applicable on provided arguments");
	}

	public Object newInstance(Object... arguments)
			throws IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		Constructor[] constructors = getWrappedClass().getConstructors();
		Constructor applicableConstructor = null;
		Object[] coercedArguments = new Object[arguments.length];

		for (int i = 0; i < constructors.length; i++) {
			Constructor c = constructors[i];
			Class[] paramTypes = c.getParameterTypes();

			if (paramTypes.length == arguments.length) {
				int j = 0;
				while (j < paramTypes.length) {
					if (!argumentMatchType(arguments, paramTypes,
							coercedArguments, j)) {
						break;
					}
					j++;
				}

				if (j == paramTypes.length) {
					if (applicableConstructor == null) {
						applicableConstructor = c;
					} else {
						throw new NoSuchMethodError(
								"More than one constructor is applicable on provided arguments");
					}
				}
			}
		}

		if (applicableConstructor != null) {
			return applicableConstructor.newInstance(coercedArguments);
		}

		throw new NoSuchMethodError(
				"No constructor is applicable on provided arguments");
	}

	final static private Class[] s_primitiveTypes = new Class[] { Boolean.TYPE,
			Byte.TYPE, Character.TYPE, Double.TYPE, Float.TYPE, Integer.TYPE,
			Long.TYPE, Short.TYPE };
	
	final static private Class[] s_primitiveClasses = new Class[] {
			Boolean.class, Byte.class, Character.class, Double.class,
			Float.class, Integer.class, Long.class, Short.class };

	private boolean argumentMatchType(Object[] arguments, Class[] types,
			Object[] coercedArguments, int index) {
		Object argument = arguments[index];
		Class type = types[index];

		coercedArguments[index] = argument;

		if (argument == null) {
			coercedArguments[index] = null;
			return true;
		} else if (type.isAssignableFrom(argument.getClass())) {
			coercedArguments[index] = argument;
			return true;
		} else if (type.equals(String.class)) {
			coercedArguments[index] = argument.toString();
			return true;
		} else {
			if (argument instanceof Double) {
				double d = ((Double) argument).doubleValue();
				if ((d - ((int) d)) == 0.0) {
					coercedArguments[index] = new Integer((int) d);
				}
			} else if (argument instanceof Float) {
				float f = ((Float) argument).floatValue();
				if ((f - ((int) f)) == 0) {
					coercedArguments[index] = new Integer((int) f);
				}
			}

			if (type.isPrimitive()) {
				for (int i = 0; i < s_primitiveClasses.length; i++) {
					if (type.equals(s_primitiveTypes[i])) {
						type = s_primitiveClasses[i];
						break;
					}
				}
			}

			return type.isAssignableFrom(coercedArguments[index].getClass());
		}
	}

}
