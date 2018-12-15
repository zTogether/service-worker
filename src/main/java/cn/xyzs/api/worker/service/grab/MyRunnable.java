package cn.xyzs.api.worker.service.grab;

import java.util.Date;

public class MyRunnable implements Runnable {

    /**
     * 超过12小时
     * 分单阶段：
     */
    public void getSheet(){
        //1.查找达到12小时未截单的单据（）
        System.err.println(Thread.currentThread().getName()+"分单");
        //循环
        //判断是否有工人参与报名
        //是
        //2.查找开标级别最高的工人（开标级别=抢单级别+虚拟级别）
        //3.判断抢到的工人抢单级别是否大于5级
        //是
        //降低抢单等级（X）
        //4.修改单据已截单
        //否
        //提示是否新增补贴工资（后续操作）
    }
    @Override
    public void run() {
        this.getSheet();
    }
}
