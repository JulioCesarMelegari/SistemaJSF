package teste.junit;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Test;

import report.util.DateUtils;

public class testeData {

	@Test
	public void test() {
		System.out.println(DateUtils.getDateAtualReportName());
	}
	
	@Test
	public void testData() {
		try {
			assertEquals("28072022",DateUtils.getDateAtualReportName());
			assertEquals("'2022-07-28'",DateUtils.formatDateSql(Calendar.getInstance().getTime()));
			assertEquals("2022-07-28",DateUtils.formatDateSqlSimple(Calendar.getInstance().getTime()));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
