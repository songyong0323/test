/**
 * 
 */
package effective.chapter2;

/**
 * @author songyong
 * @name : Complex
 * @todo : []
 * 
 * 创建时间 ： 2017年5月10日上午9:37:56
 */
public class Complex {
	//我在回退版本上改第二版
	private static int dd;
	
	public Complex() {
		dd=3;
	}
	
	public static void main(String[] args) {
		System.out.println(new Complex().dd);
	}
	
	public void test(){
		System.out.println(dd);
	}
	
	
}
