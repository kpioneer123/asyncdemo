package com.kpioneer.asyncdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author xionghu
 * @create 2019/12/17
 * @Desc
 **/
@RestController
public class TestController {

    @Autowired
    private MyService myService;

    @RequestMapping("/test")
    public String getInedx() throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("开始访问");
        long l1 = System.currentTimeMillis();
        /**
         * 内部维护11个线程的线程池
         */
        ExecutorService exec = Executors.newFixedThreadPool(11);
        /**
         * 容量为10的堵塞队列
         */
        final BlockingQueue<Future<String>> queue = new LinkedBlockingDeque<Future<String>>(
                10);
        //实例化CompletionService
        final CompletionService<String> completionService = new ExecutorCompletionService<String>(
                exec, queue);

        /**
         * 模拟瞬间产生10个任务，且每一个任务运行时间不一致
         */
        for (int i = 0; i < 10; i++){
            int finalI = i;
            completionService.submit(new Callable<String>()
            {
                @Override
                public String call() throws Exception
                {

                    return myService.test(finalI +"");
                }
            });
        }

        /**
         * 马上输出结果
         */
        for (int i = 0; i < 10; i++){
            try
            {
                //谁最先运行完毕，直接返回
                Future<String> f = completionService.take();
//                if(f.get().equals("3")){
//                    return "ff";
//
//                }
                System.out.println(f.get());
            } catch (InterruptedException e){
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }

        exec.shutdown();

        long l2 = System.currentTimeMillis();//跳出while循环时说明此时三个异步调用的方法都执行完成了，此时得到当前时间


        System.out.println("结束访问,用时"+(l2-l1));


        return "finished";
    }

}
