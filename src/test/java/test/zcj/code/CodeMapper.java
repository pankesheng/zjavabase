package test.zcj.code;

import java.lang.reflect.Field;

public class CodeMapper {

	public static void main(String[] args) {
//		printCode("t_dmt_ratinglist", DmtRatingList.class);
	}
	
	// ,jdbcType=LONGVARCHAR
	public static void printCode(String tableName, Class<?> className) {
		
		Field[] f = className.getDeclaredFields();
		
		System.out.println("");
		System.out.println("	<insert id=\"insert\">");
		System.out.println("		INSERT INTO "+tableName);
		System.out.print("			(id,");
			for (Field ff : f) {
				if (!"serialVersionUID".equals(ff.getName())) {
					System.out.print(ff.getName()+",");
				}
			}
		System.out.println("ctime,utime)");
		System.out.println("		VALUES");
		System.out.print("			(#{object.id},");
			for (Field ff : f) {
				if (!"serialVersionUID".equals(ff.getName())) {					
					System.out.print("#{object."+ff.getName()+"},");
				}
			}
		System.out.println("getdate(),getdate())");
		System.out.println("	</insert>");
		System.out.println("");
		System.out.println("	<update id=\"update\">");
		System.out.println("		UPDATE "+tableName);
		System.out.println("		SET");
			for (Field ff : f) {
				if (!"serialVersionUID".equals(ff.getName())) {					
					System.out.println("			"+ff.getName()+" = #{object."+ff.getName()+"},");
				}
			}
		System.out.println("			utime = getdate()");
		System.out.println("		WHERE");
		System.out.println("			id = #{object.id}");
		System.out.println("	</update>");
		System.out.println("");
		System.out.println("	<delete id=\"delete\">");
		System.out.println("		DELETE FROM "+tableName+" WHERE id = #{id}");
		System.out.println("	</delete>");
		System.out.println("");
		System.out.println("	<delete id=\"deleteByIds\">");
		System.out.println("		DELETE FROM "+tableName+" WHERE id in ");
		System.out.println("		<foreach item=\"id\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">");
		System.out.println("			#{id}");
		System.out.println("		</foreach>");
		System.out.println("	</delete>");
		System.out.println("");
		System.out.println("	<delete id=\"cleanTable\">");
		System.out.println("		DELETE FROM "+tableName);
		System.out.println("	</delete>");
		System.out.println("");
	}
	
}
