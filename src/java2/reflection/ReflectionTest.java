package java2.reflection;

import java.lang.reflect.Field;

public class ReflectionTest {

	 String s11 = "11";
	String s12 = "12";
	String s13 = "13";
	private	String s14= "14";
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {

		Field[] fields = ReflectionTest.class.getFields();
		Field[] fields2 = ReflectionTest.class.getDeclaredFields();
		System.out.println("Members");
		ReflectionTest reflectionTest = new ReflectionTest();
		reflectionTest.s11="123123";
		for (Field field : fields) {
			System.out.println("Member:" + field.getName());
		}
		for (Field field : fields2) {
			System.out.println("Member:" + field.getName() + ":"+ field.get(reflectionTest));
		}
	}

}
