package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class InvokeAllTest {
	
	//固定大小的线程池，同时只能接受5个任务
	static ExecutorService mExecutor=Executors.newFixedThreadPool(5);
	
	/**
	 * 计算价格的任务
	 * @author admin
	 *
	 */
	private class QuoteTask implements Callable<BigDecimal>{
		
		public final double price;
		public final int num;
		
		public QuoteTask(double price,int num) {
			this.price=price;
			this.num=num;
		}
		
		@Override
		public BigDecimal call() throws Exception {
			Random r=new Random();
			long time=(r.nextInt(10)+1)*1000;
			Thread.sleep(time);
			BigDecimal d=BigDecimal.valueOf(price*num).setScale(2);
			System.out.println("耗时："+time/1000+"s，单价是："+price+"，人数是："+num
					+",总额是："+d);
			return d;
		}
	}
	
	/**
	 * 在预定时间内请求获得旅游报价信息 
	 */
	public void getRankedTravelQuotes() throws Exception{
		List<QuoteTask> tasks=new ArrayList<>();
		//模拟10个计算旅游报价的任务
		for (int i = 1; i <= 20; i++) {
			tasks.add(new QuoteTask(200, i));
		}
		/**
		 * 使用invokeAll方法批量提交限时任务，预期15s所有任务都执行完成，没有执行完的任务会自动取消
		 */
		List<Future<BigDecimal>> futures=mExecutor.invokeAll(tasks,15,TimeUnit.SECONDS);
		System.out.println("过了15s了");
		//报价合计集合
		List<BigDecimal> totalPriceList=new ArrayList<>();
		
		Iterator<QuoteTask> taskIter=tasks.iterator();
		
		for (Future<BigDecimal> future:futures) {
			QuoteTask task=taskIter.next();
			try {
				totalPriceList.add(future.get());
			} catch (ExecutionException e) {
				// 返回计算失败的原因
				totalPriceList.add(BigDecimal.valueOf(-1));
				System.out.println("任务执行异常,单价是"+task.price+"，人数是："+task.num);  
			}catch(CancellationException e){
				totalPriceList.add(BigDecimal.ZERO);  
                System.out.println("任务超时，取消计算,单价是"+task.price+"，人数是："+task.num);  
			}
		}
		for (BigDecimal bigDecimal:totalPriceList) {
			System.out.println(bigDecimal);
		}
		mExecutor.shutdown();
	}
	
	public static void testInvokeAllThread() throws InterruptedException{
		ExecutorService exec=Executors.newFixedThreadPool(10);
		
		List<Callable<Integer>> tasks=new ArrayList<>();
		Callable<Integer> task=null;
		for (int i = 0; i < 20; i++) {
			task=new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					int ran = new Random().nextInt(1000);  
	                Thread.sleep(ran);  
	                System.out.println(Thread.currentThread().getName()  
	                        + " 休息了 " + ran);  
	                return ran;  
				}
			};
			tasks.add(task);
		}
		
		long s = System.currentTimeMillis();  
		List<Future<Integer>> results=exec.invokeAll(tasks);
		System.out.println("执行任务消耗了 ：" + (System.currentTimeMillis() - s)  
	            + "毫秒");  
		
		for (int i = 0; i < results.size(); i++) {
			try {
				System.out.println(results.get(i).get());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		exec.shutdown();
	}
	
	public static void main(String[] args) {
		try {
			InvokeAllTest it=new InvokeAllTest();
			it.getRankedTravelQuotes();
//			testInvokeAllThread();
		} catch (Exception  e) {
			// TODO: handle exception
		}
	}
}
