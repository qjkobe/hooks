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
//            for (int i = 0; i < 9; i++) {
//                int x = Integer.parseInt(p.getProperty("x" + (i + 1)));
//                int y = Integer.parseInt(p.getProperty("y" + (i + 1)));
//                mouseMove(robot, x, y);
//                mousePress(robot);
//                robot.delay(500);
//            }
//            robot.delay(3000);
            //防止公告
            mouseMove(robot, 944, 127);
            mousePress(robot);
            robot.delay(100);
            //火车操作总共3*36*4次
            //火车操作会伴随着收金币。所以上面不需要了。
            for (int i = 0; i < 3; i++) {
                int trainX = Integer.parseInt(p.getProperty("trainX" + (i + 1)));
                int trainY = Integer.parseInt(p.getProperty("trainY" + (i + 1)));
                for (int k = 0; k < 4; k++) {
                    for (int j = 0; j < 9; j++) {
                        mouseMove(robot, trainX, trainY);
                        holdMouse(robot);
                        //有时候按住，位置会歪掉。所以这里也需要delay
                        robot.delay(100);
                        int x = Integer.parseInt(p.getProperty("x" + (j + 1)));
                        int y = Integer.parseInt(p.getProperty("y" + (j + 1)));
                        //这里做一个缓慢移动操作。先要判断相对位置。首先纵坐标一定小
                        if (trainX > x) {
                            for (int toX = trainX; toX > x; toX -= 15) {
                                mouseMove(robot, toX, trainY);
                                robot.delay(1);
                            }
                            for (int toY = trainY; toY > y - 30; toY -= 15) {
                                mouseMove(robot, x, toY);
                                robot.delay(1);
                            }
                        } else if (trainX < x) {
                            for (int toX = trainX; toX < x; toX += 15) {
                                mouseMove(robot, toX, trainY);
                                robot.delay(1);
                            }
                            for (int toY = trainY; toY > y - 30; toY -= 15) {
                                mouseMove(robot, x, toY);
                                robot.delay(1);
                            }
                        }
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
