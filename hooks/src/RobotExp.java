import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Administrator on 2016/7/19.
 */
public class RobotExp {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            //定义五秒延迟
            robot.delay(200);

            robot.mouseMove(173,1058);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);

            robot.delay(200);

            robot.mouseMove(228,975);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);

            robot.keyPress(KeyEvent.VK_H);
            robot.keyPress(KeyEvent.VK_E);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_L);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyPress(KeyEvent.VK_O);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
