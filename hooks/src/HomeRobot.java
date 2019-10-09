import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HomeRobot {
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
            int comeFlag = 0;
            //防止公告
            mouseMove(robot, 944, 127);
            mousePress(robot);
            robot.delay(100);
            //火车操作总共3*36*4次
            //火车操作会伴随着收金币。所以上面不需要了。
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
                if (pixel.equals(blue) || pixel.equals(purple) || pixel.equals(orange)) {
                    comeFlag = 1;//火车来过了。
                }
                if (!pixel.equals(orange)) {
                    continue;
                }
                for (int k = 0; k < 4; k++) {
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
                    }
//                    robot.delay(2000);
                }
            }
            //5s收一次
            robot.delay(5000);
            //如果火车来了，并且循环结束，那就退出登录然后重新登录
            if(comeFlag == 1) {
                mouseMove(robot, 678, 83);
                robot.delay(100);
                mousePress(robot);
                mouseMove(robot, 805, 784);
                robot.delay(100);
                mousePress(robot);
                robot.delay(5000);
                mouseMove(robot, Integer.parseInt(p.getProperty("LoginX")), Integer.parseInt(p.getProperty("LoginY")));
                robot.delay(100);
                mousePress(robot);
            }
        }
    }

    public static void main(String[] args) {
        HomeRobot a = new HomeRobot();
        try {
            a.collectCoins();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
