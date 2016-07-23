import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Administrator on 2016/7/19.
 */
public class RobotExp {

    public static void mouseMove(Robot robot, int x, int y) {
        robot.mouseMove(x,y);
    }

    public static void mousePress(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public static int getKey(int a) {
        switch (a) {
            case 1:
                return KeyEvent.VK_1;
            case 2:
                return KeyEvent.VK_2;
            case 3:
                return KeyEvent.VK_3;
            case 4:
                return KeyEvent.VK_4;
            case 5:
                return KeyEvent.VK_5;
            case 6:
                return KeyEvent.VK_6;
            case 7:
                return KeyEvent.VK_7;
            case 8:
                return KeyEvent.VK_8;
            case 9:
                return KeyEvent.VK_9;
        }
        return 0;
    }

    public static void doTask() throws Exception {
        InputStream inputStream = RobotExp.class.getClassLoader().getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        int x1, x2, x3, x4, y1, y2, y3, y4,money;
        String time;
        x1 = Integer.parseInt(p.getProperty("x1"));
        x2 = Integer.parseInt(p.getProperty("x2"));
        x3 = Integer.parseInt(p.getProperty("x3"));
        x4 = Integer.parseInt(p.getProperty("x4"));
        y1 = Integer.parseInt(p.getProperty("y1"));
        y2 = Integer.parseInt(p.getProperty("y2"));
        y3 = Integer.parseInt(p.getProperty("y3"));
        y4 = Integer.parseInt(p.getProperty("y4"));

        time = p.getProperty("time");
        money = Integer.parseInt(p.getProperty("money"));
        System.out.println(money);

        money /= 100;

        int constkey = getKey(money);

        //HH是24小时制时间。
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = simpleDateFormat.parse(time);

        Robot robot = new Robot();
        robot.delay(5000);
        while (true) {
            Date datenow = new Date();

            if (datenow.after(date1)) {
                mouseMove(robot,x1, y1);
                mousePress(robot);

                //输入money
                robot.keyPress(constkey);
                robot.keyRelease(constkey);
                robot.keyPress(KeyEvent.VK_0);
                robot.keyRelease(KeyEvent.VK_0);
                robot.keyPress(KeyEvent.VK_0);
                robot.keyRelease(KeyEvent.VK_0);

                //点击加价
                mouseMove(robot, x2, y2);
                mousePress(robot);

                //点击确认
                mouseMove(robot,x3,y3);
                mousePress(robot);

                //移动到验证码确认
                mouseMove(robot, x4, y4);

                break;
            }
        }

    }

    public static void main(String[] args) throws Exception {
        doTask();
//        try {
//            Robot robot = new Robot();
//            //定义延迟
////            robot.delay(2000);
//
//            //测试
////            SimpleDateFormat test=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//            //HH是24小时制时间。
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            //输入你想要判断的时间
//            Date date1 = simpleDateFormat.parse("2016-7-23 11:29:55");
////            Date datenow = new Date();
////            System.out.println(datenow.after(date1));
////            System.out.println(simpleDateFormat.format(datenow));
////            System.out.println(simpleDateFormat.format(date1));
//            while (true) {
//                //循环获取当前时间
//                Date datenow = new Date();
////                if(true){
//                if (datenow.after(date1)) {
//
//                    //移动到框内
//                    robot.mouseMove(1190, 379);
//                    robot.mousePress(InputEvent.BUTTON1_MASK);
//                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//                    //+400
//                    robot.keyPress(KeyEvent.VK_4);
//                    robot.keyRelease(KeyEvent.VK_4);
//                    robot.keyPress(KeyEvent.VK_0);
//                    robot.keyRelease(KeyEvent.VK_0);
//                    robot.keyPress(KeyEvent.VK_0);
//                    robot.keyRelease(KeyEvent.VK_0);
//
//                    //移动到加价
//                    robot.mouseMove(1307,380);
//                    robot.mousePress(InputEvent.BUTTON1_MASK);
//                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//                    //移动到出价
//                    robot.mouseMove(1313,487);
//                    robot.mousePress(InputEvent.BUTTON1_MASK);
//                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//                    //移动到验证码确定处不点击
//                    robot.mouseMove(1070,570);
////                    robot.delay(1000);
//
////                    robot.mouseMove(228, 975);
////                    robot.mousePress(InputEvent.BUTTON1_MASK);
////                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
////                    robot.keyPress(KeyEvent.VK_H);
////                    robot.keyPress(KeyEvent.VK_E);
////                    robot.keyPress(KeyEvent.VK_L);
////                    robot.keyRelease(KeyEvent.VK_L);
////                    robot.keyPress(KeyEvent.VK_L);
////                    robot.keyPress(KeyEvent.VK_O);
////
////                    robot.keyPress(KeyEvent.VK_CONTROL);
////                    robot.keyPress(KeyEvent.VK_ENTER);
////                    robot.keyRelease(KeyEvent.VK_CONTROL);
////                    robot.keyRelease(KeyEvent.VK_ENTER);
//                    break;
//                }
//            }
//
//        } catch (AWTException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }
}
