package test.zcj.util.json.gson;

import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zcj.util.json.gson.GsonExclusionStrategy;
import com.zcj.util.json.gson.GsonFieldNamingStrategy;

public class MyGsonTest {

	@SuppressWarnings("unused")
	private static final Gson GSON1 = new GsonBuilder()
			.serializeNulls() // 显示 null 的数据
			.setDateFormat("yyyy-MM-dd HH:mm:ss") // 格式化时间格式的数据
			.registerTypeAdapter(Date.class, new MyGsonDateTypeAdapter()) // 添加时间类型转换器，可对时间进行任意处理
			.excludeFieldsWithoutExposeAnnotation() // 排除没有@Expose注解的字段
			.setExclusionStrategies(new GsonExclusionStrategy(new String[]{"descr","mapurl"}, new Class<?>[]{List.class})) // 自定义排除指定属性和类型
			.setFieldNamingStrategy(new GsonFieldNamingStrategy(new String[]{"descr"}, new String[]{"theDescr"})) // 修改指定字段名称
			.setPrettyPrinting() // 返回优美的JSON格式
			.setVersion(1.0) // 忽略1.0版本以后的字段
			.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE) // 默认排除static、transient、volatile修饰的字段,如果需要显示，则去掉需要显示的那个
			.create();

	public static void main(String[] args) {
			List<Student> students = Student.initStudentList();
			String studentJson = "{'id':12,'name':'sddddss'}";

		String result = new GsonBuilder().setVersion(1.0).create().toJson(students);
			System.out.println(result);

		List<Student> s = new Gson().fromJson(result, new TypeToken<List<Student>>(){}.getType());
			System.out.println(s.get(0).getId());
		
		Student ss = new Gson().fromJson(studentJson, Student.class);
			System.out.println(ss.getName());
		
	}
}
