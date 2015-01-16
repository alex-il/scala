package java2.reflection;

import java.lang.reflect.Field;

public class RunReflection {

	public static void main(String[] args) throws Exception {
		Field[] fields = ReflectionTest.class.getFields();
		Field[] fields2 = ReflectionTest.class.getDeclaredFields();
		System.out.println("Start Enumerate Class Members...");
		ReflectionTest reflectionTest = new ReflectionTest();
		for (Field field : fields) {
			System.out.println("Member:" + field.getName());
		}
		for (Field field : fields2) {
			System.out.println("Member_2:" + field.getName()+ ":"+ field.get(reflectionTest));
		}

	}

}
