package Job;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;

import Log.CLog;

public class CJobThread extends Thread {
	
	static public interface ExceptionCallback {
		
		void run(Exception e);
	}
	
	private final static Logger logger = CLog.getLogger();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CJobThread(Callable callable, String arg1, int retry, long wait, long timeout, ExceptionCallback exceptionCallback) {
		super(new Runnable() {
			
			@Override
			public void run() {
				int retryTimes = retry;
				FutureTask<Object> task = new FutureTask<Object>(callable);
				while (retryTimes-- > 0) {
					Thread thread = null;
					try {
						thread = new Thread(task, arg1 + "-1");
						thread.start();
						task.get(timeout, TimeUnit.SECONDS);
						thread = null;
						break;
					}
					catch (Exception e) {
						if (retryTimes == 0) {
							logger.error(e.getMessage(), e);
							if (exceptionCallback != null) exceptionCallback.run(e);
						}
						else {
							try {
								Thread.sleep(wait);
							}
							catch (InterruptedException e1) {
							}
						}
					}
					catch (Throwable e) {
						logger.error(e.getMessage(), e);
						System.exit(0);
					}
					thread = null;
				}
				task = null;
			}
		}, arg1);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CJobThread(Callable arg0, int retry, long wait, long timeout, ExceptionCallback exceptionCallback) {
		super(new Runnable() {
			
			@Override
			public void run() {
				int retryTimes = retry;
				FutureTask<Object> task = new FutureTask<Object>(arg0);
				while (retryTimes-- > 0) {
					Thread thread = null;
					try {
						thread = new Thread(task);
						thread.start();
						task.get(timeout, TimeUnit.SECONDS);
						thread = null;
						break;
					}
					catch (Exception e) {
						if (retryTimes == 0) {
							logger.error(e.getMessage(), e);
							if (exceptionCallback != null) exceptionCallback.run(e);
						}
						else {
							try {
								Thread.sleep(wait);
							}
							catch (InterruptedException e1) {
							}
						}
					}
					catch (Throwable e) {
						logger.error(e.getMessage(), e);
						System.exit(0);
					}
					thread = null;
				}
				task = null;
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CJobThread(Callable arg0, String arg1, long timeout, ExceptionCallback exceptionCallback) {
		super(new Runnable() {
			
			@Override
			public void run() {
				FutureTask<Object> task = new FutureTask<Object>(arg0);
				Thread thread = new Thread(task, arg1 + "-1");
				try {
					thread.start();
					task.get(timeout, TimeUnit.SECONDS);
				}
				catch (Exception e) {
					logger.error(e.getMessage(), e);
					if (exceptionCallback != null) exceptionCallback.run(e);
				}
				catch (Throwable e) {
					logger.error(e.getMessage(), e);
					System.exit(0);
				}
				task = null;
				thread = null;
			}
		}, arg1);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CJobThread(Callable arg0, long timeout, ExceptionCallback exceptionCallback) {
		super(new Runnable() {
			
			@Override
			public void run() {
				FutureTask<Object> task = new FutureTask<Object>(arg0);
				Thread thread = new Thread(task);
				try {
					thread.start();
					task.get(timeout, TimeUnit.SECONDS);
				}
				catch (Exception e) {
					logger.error(e.getMessage(), e);
					if (exceptionCallback != null) exceptionCallback.run(e);
				}
				catch (Throwable e) {
					logger.error(e.getMessage(), e);
					System.exit(0);
				}
				task = null;
				thread = null;
			}
		});
	}
}
