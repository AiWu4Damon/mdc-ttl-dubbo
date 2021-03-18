import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TtlAndItlTest {

    public static void main(String[] args) {
//        final TransmittableThreadLocal<String> transmittableThreadLocal = new TransmittableThreadLocal<String>();
//        transmittableThreadLocal.set("transmittableThreadLocal");
        final InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<String>();
        inheritableThreadLocal.set("inheritableThreadLocal");
//        Runnable r = () -> System.out.println(transmittableThreadLocal.get());
        Runnable r2 = () -> System.out.println(inheritableThreadLocal.get());

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(r2);
        executorService.submit(r2);

        executorService.submit(r2);
        executorService.submit(r2);

        executorService.shutdown();
    }
}
