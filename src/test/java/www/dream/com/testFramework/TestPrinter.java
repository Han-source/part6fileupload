package www.dream.com.testFramework;

import org.junit.Test;

import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.framework.printer.TablePrinter;

public class TestPrinter {
	@Test
	public void test() {
		System.out.println(TablePrinter.printHeader(PostVO.class));
	}
}
