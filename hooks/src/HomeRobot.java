import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class HomeRobot extends JFrame {

    private final Object lock = new Object();

    private boolean pause = false;

    private int zeroRefresh = 0;//0点刷新

    public String mode = "collectTrains"; //collectCoins

    /**
     * 调用该方法实现线程的暂停
     */
    void pauseThread() {
        pause = true;
    }


    /**
     * 调用该方法实现恢复线程的运行
     */
    void resumeThread() {
        pause = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    /**
     * 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
     */
    void onPause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void mouseMove(Robot robot, int x, int y) {
        robot.mouseMove(x, y);
    }

    public static void mousePress(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    void holdMouse(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_MASK);
    }

    void releaseMouse(Robot robot) {
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    void relogin(Robot robot, Properties p) {
        mouseMove(robot, 678, 83);
        robot.delay(100);
        mousePress(robot);
        mouseMove(robot, 805, 784);
        robot.delay(100);
        mousePress(robot);
        robot.delay(3000);
        mouseMove(robot, Integer.parseInt(p.getProperty("LoginX")), Integer.parseInt(p.getProperty("LoginY")));
        robot.delay(100);
        mousePress(robot);
    }

    void switchNetwork(Robot robot, Properties p) {
        mouseMove(robot, Integer.parseInt(p.getProperty("PanelX")), Integer.parseInt(p.getProperty("PanelY")));
        robot.delay(100);
        mousePress(robot);
        robot.delay(4000);

        mouseMove(robot, Integer.parseInt(p.getProperty("NetWorkX")), Integer.parseInt(p.getProperty("NetWorkY")));
        robot.delay(100);
        mousePress(robot);
        //时间太短不算掉线
        robot.delay(20 * 1000);
        mousePress(robot);

        robot.delay(5000);
        //点击屏幕中间收金币触发重连
        mouseMove(robot, Integer.parseInt(p.getProperty("x5")), Integer.parseInt(p.getProperty("y5")));
        robot.delay(100);
        mousePress(robot);
        //等待5s重连
        robot.delay(5*1000);
    }

    void collectCoinsMode(Robot robot, Properties p) {
        for (int i = 0; i < 9; i++) {
            int coinX = Integer.parseInt(p.getProperty("x" + (i + 1)));
            int coinY = Integer.parseInt(p.getProperty("y" + (i + 1)));
            mouseMove(robot, coinX, coinY);
            robot.delay(100);
            mousePress(robot);
            robot.delay(100);
            while (pause) {
                onPause();
            }
        }
        robot.delay(5000);
    }

    void switchMode() {
        if (mode.equals("collectCoins")) {
            mode = "collectTrains";
        } else {
            mode = "collectCoins";
        }
    }


    void collectCoins() throws Exception {
        InputStream inputStream = RobotExp.class.getClassLoader().getResourceAsStream("HomeConfig.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Robot robot = new Robot();
        robot.delay(3000);
        while (true) {
            if (mode.equals("collectCoins")) {
                collectCoinsMode(robot, p);
                continue;
            }
            while (pause) {
                onPause();
            }
            robot.delay(1000);
            int comeFlag = 0;
            //防止公告
            mouseMove(robot, 944, 127);
            mousePress(robot);
            robot.delay(100);
            //火车操作总共3*36*4次
            //现在改为只收取橙色的。所以只收取右上角四个。循环要修改的就是最内层循环。j只能为2 3 5 6
            for (int i = 0; i < 3; i++) {
                int trainX = Integer.parseInt(p.getProperty("trainX" + (i + 1)));
                int trainY = Integer.parseInt(p.getProperty("trainY" + (i + 1)));
                //先判断颜色，不是橙色跳过。
                int trainColorX = Integer.parseInt(p.getProperty("trainColorX" + (i + 1)));
                int trainColorY = Integer.parseInt(p.getProperty("trainColorY" + (i + 1)));
                Color pixel = robot.getPixelColor(trainColorX, trainColorY);
                Color blue = new Color(Integer.parseInt(p.getProperty("Blue1")), Integer.parseInt(p.getProperty("Blue2")), Integer.parseInt(p.getProperty("Blue3")));
                Color purple = new Color(Integer.parseInt(p.getProperty("Purple1")), Integer.parseInt(p.getProperty("Purple2")), Integer.parseInt(p.getProperty("Purple3")));
                Color orange = new Color(Integer.parseInt(p.getProperty("Orange1")), Integer.parseInt(p.getProperty("Orange2")), Integer.parseInt(p.getProperty("Orange3")));
                if (pixel.equals(blue) || pixel.equals(purple)) {
                    //注意如果只有橙色，那么不需要重新登陆，直接拉完橙色
                    comeFlag = 1;//火车来过了。
                }
                if (!pixel.equals(orange)) {
                    continue;
                }
                //橙色其实最多只来3个..也可能只有2个
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 9; j++) {
                        int temp = j + 1;
                        if (temp != 2 && temp != 3 && temp != 5 && temp != 6 && temp != 9) {
                            continue;
                        }
                        mouseMove(robot, trainX, trainY);
                        holdMouse(robot);
                        //有时候按住，位置会歪掉。所以这里也需要delay
                        robot.delay(100);
                        int x = Integer.parseInt(p.getProperty("x" + (j + 1)));
                        int y = Integer.parseInt(p.getProperty("y" + (j + 1)));
                        //这里做一个缓慢移动操作。先要判断相对位置。首先纵坐标一定小
                        //后面发现其实不需要缓慢移动，之前只是因为点击的时候没有delay而已。
                        mouseMove(robot, x, y - 60);

                        //松鼠标前也必须等待一会
                        robot.delay(100);
                        releaseMouse(robot);
                        //如果没成功，必须等那个东西回来。这里需要一会
                        robot.delay(300);

                        while (pause) {
                            onPause();
                        }
                    }
//                    robot.delay(2000);
                }
            }
            //5s收一次
            robot.delay(3000);
            //如果火车来了，并且循环结束，那就网络切换
            if (comeFlag == 1) {
                switchNetwork(robot, p);
            }
            //如果已经12点了。那就网络切换。这个只会执行一次
            if(zeroRefresh == 0) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH");
                String hour = sdf.format(date);
                if (Integer.parseInt(hour) == 0) {
                    switchNetwork(robot, p);
                    zeroRefresh = 1;
                }
            }
        }
    }

    public void stopKeyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {//同时按下ctrl+c
        }
    }

    public static void main(String[] args) {
        System.out.println("Ctrl+C:暂停");
        System.out.println("Ctrl+Z:继续");
        System.out.println("Ctrl+I:关闭");
        System.out.println("Ctrl+M:收集金币模式。再按可以切换回来");
        HomeRobot a = new HomeRobot();
        Runnable runner = () -> {
            try {
                a.collectCoins();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Thread collectThread = new Thread(runner);
        collectThread.start();
        new Thread(() -> {
            HotKey hotKey = new HotKey(a);
            hotKey.initHotkey();
            System.out.println("监听 Start");
        }, "t2").start();

    }
}
