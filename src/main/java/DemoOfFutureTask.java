import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by wangchengcheng on 2018-12-03
 */

public class DemoOfFutureTask {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        List<FutureTask<String>> tasks = new ArrayList<>();
        List<Long> sleepTimes = new ArrayList<>();
        sleepTimes.add(7000L);
        sleepTimes.add(5000L);
        sleepTimes.add(3000L);
        sleepTimes.add(1000L);
        long start = System.currentTimeMillis();
        for (Long sleepTime : sleepTimes) {
            FutureTask<String> futureTask = new FutureTask<>(new Task(sleepTime));
            service.submit(futureTask);
            tasks.add(futureTask);
        }
        service.shutdown();

        for (FutureTask<String> task : tasks) {
            try {
                System.out.printf("%s", task.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("interval=" + (end - start));
    }

    static class Task implements Callable<String> {
        private long sleepTime;

        public Task(long sleepTime) {
            this.sleepTime = sleepTime;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(sleepTime);
            return String.format("sleepTime=%d%n", sleepTime);
        }

    }
}
