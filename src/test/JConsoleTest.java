package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JConsoleTest {

	//内存页演示
	static class OOMObject{
		public byte[] placeholder=new byte[64*1024];
	}
	
	public static void fillHeap(int num) throws Exception{
		List<OOMObject> list=new ArrayList<>();
		for (int i = 0; i < num; i++) {
			Thread.sleep(50);
			list.add(new OOMObject());
		}
		System.gc();
	}
	
//	public static void main(String[] args) throws Exception{
//		fillHeap(1000);
//	}
	
	
	/**
	 * 线程页演示
	 */
	//线程死循环演示
	public static void createBusyThread(){
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					;
				}
			}
		},"testBusyThread");
		thread.start();
	}
	
	public static void createLockThread(final Object lock){
		Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		},"testLockThread");
		thread.start();
	}
	
//	public static void main(String[] args) throws Exception{
//		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//		br.readLine();
//		System.out.println(111);
//		createBusyThread();
//		br.readLine();
//		Object object=new Object();
//		createLockThread(object);
//	}
	
	/**
	 * 死锁等待演示
	 */
	static class SynAddRunable implements Runnable{
		int a,b;
		public SynAddRunable(int a,int b){
			this.a=a;
			this.b=b;
		}
		
		@Override
		public void run() {
			synchronized (Integer.valueOf(a)) {
				synchronized (Integer.valueOf(b)) {
					System.out.println(a+b);
				}
			}
		}
	}
	
//	public static void main(String[] args) {
//		for (int i = 0; i < 100; i++) {
//			new Thread(new SynAddRunable(1, 2)).start();
//			new Thread(new SynAddRunable(2, 1)).start();
//		}
//	}
	
	public static void main(String[] args) throws Exception{
		Integer a=1;
		Integer b=2;
		Integer c=3;
		Integer d=3;
		Integer e=127;
		Integer f=127;
		Long g=3L;
		System.out.println(e==f);
		System.out.println(c==d);
		System.out.println(c==(a+b));
		System.out.println(c.equals(a+b));
		System.out.println(g==(a+b));
		System.out.println(g.equals(a+b));
	}
}
