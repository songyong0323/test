package effective.chapter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class StopThread {

	private static volatile boolean stopRequested;
	
	
	public static void main(String[] args) throws Exception{
		Thread backgroundThread=new Thread(new Runnable() {
			@Override
			public void run() {
				int i=0;
				while(!stopRequested){
					System.out.println(i++);
				}
			}
		});
		backgroundThread.start();
		TimeUnit.SECONDS.sleep(1);
		stopRequested=true;
	}
	
	private static final AtomicLong nextNum=new AtomicLong();
	public static long getNextNum(){
		return nextNum.getAndIncrement();
	}
	
}
