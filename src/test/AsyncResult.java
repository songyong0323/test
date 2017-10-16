/**
 * 
 */
package test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author songyong
 * @name : AsyncResult
 * @todo : []
 * 
 * 创建时间 ： 2017年4月27日下午4:27:30
 */
public class AsyncResult{
	
	private byte[] result_;
	private AtomicBoolean done_=new AtomicBoolean(false);
	private Lock lock_=new ReentrantLock();
	private Condition condition_;
	private long startTime_;
	
	public AsyncResult(){
		condition_=lock_.newCondition();
		startTime_=System.currentTimeMillis();
	}
	
	//检查需要的数据是否已经返回，如果没有返回，阻塞
	public byte[] get(){
		lock_.lock();
		try {
			if(!done_.get()){
				condition_.await();
			}
		} catch (Exception e) {
		}finally {
			lock_.unlock();
		}
		return result_;
	}
	
	//检查需要的数据是否返回
	public boolean isDone(){
		return done_.get();
	}
	
	public byte[] get(long timeout,TimeUnit tu) throws Exception{
		lock_.lock();
		try {
			boolean bVal=true;
			try {
				if(!done_.get()){
					long overall_timeout=timeout-(System.currentTimeMillis()-startTime_);
					if(overall_timeout>0){//设置等待超时的时间
						bVal=condition_.await(overall_timeout, TimeUnit.MILLISECONDS);
					}else {
						bVal=false;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} finally {
			lock_.unlock();
		}
		return result_;
	}
	
	//该函数供另一个线程设置要返回的数据，并唤醒在阻塞的线程
	public void result(String msg){
		try {
			lock_.lock();
			if(!done_.get()){
				result_=msg.getBytes();
				done_.set(true);
				condition_.signal();//唤醒阻塞的线程
			}
		} finally {
			lock_.unlock();
		}
	}
	
	public static void main(String[] args) throws Exception{
		int i=1;
		while (true) {
			int j=i++;
			Thread.sleep(1000);
			System.out.println(j);
		}
	}
}
