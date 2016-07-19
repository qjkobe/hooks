import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/19.
 */
public class RobotExp {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            //定义延迟
            robot.delay(200);

            //测试
//            SimpleDateFormat test=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //HH是24小时制时间。
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //输入你想要判断的时间
            Date date1 = simpleDateFormat.parse("2016-7-19 15:32:50");
//            Date datenow = new Date();
//            System.out.println(datenow.after(date1));
//            System.out.println(simpleDateFormat.format(datenow));
//            System.out.println(simpleDateFormat.format(date1));
            while (true) {
                //循环获取当前时间
                Date datenow = new Date();
                if (true) {

                    robot.mouseMove(173, 1058);
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);

//                    robot.delay(1000);

//                    robot.mouseMove(228, 975);
//                    robot.mousePress(InputEvent.BUTTON1_MASK);
//                    robot.mouseRelease(InputEvent.BUTTON1_MASK);

                    robot.keyPress(KeyEvent.VK_H);
                    robot.keyPress(KeyEvent.VK_E);
                    robot.keyPress(KeyEvent.VK_L);
                    robot.keyRelease(KeyEvent.VK_L);
                    robot.keyPress(KeyEvent.VK_L);
                    robot.keyPress(KeyEvent.VK_O);

                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    break;
                }
            }

        } catch (AWTException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
