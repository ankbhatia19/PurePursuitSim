import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class runner extends PApplet {

    // The robot simulator object
    private TwoWheelRobotSimulator robot;

    // A flag boolean indicating whether or not the robot has been placed on the field.
    private boolean robotInitialized = false;

    // The lookahead point, which is updated every loop.
    private Point lookaheadPoint;

    // Some drawing variables
    private int pointSize = 4;

    // decorations!
    private PImage backgroundImage;

    public static void main(String[] args) {
        PApplet.main(runner.class.getName());
    }

    @Override
    public void settings() {
        size(1134, 567);
    }

    @Override
    public void setup() {
        reset();
        lookaheadPoint = null;
        backgroundImage = loadImage("../images/DeepSpace.jpg");

    }

    public void reset() {
        robot = null;
        lookaheadPoint = null;
    }

    public void update(){
        if (!robotInitialized && PurePursuitHandler.getPath().size() > 0){
            robot = new TwoWheelRobotSimulator(new Point(PurePursuitHandler.getPath().get(0).x, PurePursuitHandler.getPath().get(0).y));
            robotInitialized = true;
        }
        if (robotInitialized)
            lookaheadPoint = PurePursuitHandler.getLookaheadPoint(robot.getRobotPosition());
    }

    public void draw() {
        update();
        background(backgroundImage);
        background(255, 255, 255);
        keyHandler();

        drawTraveledPath();
        drawPath();
        drawRobot();
        drawLookaheadInfo();
    }

    public void drawRobot() {
        if (robotInitialized) {

            fill(0, 0, 255);
            noStroke();
            Point pos = robot.getRobotPosition();

            pushMatrix();
            rectMode(CENTER);
            translate(pos.x, pos.y);
            rotate(robot.getRobotAngle());
            rect(0,0, robot.robotLength, robot.robotWidth);
            fill(0, 0, 0);

            /*ellipse(robot.robotLength / 3, -robot.robotWidth / 2, pointSize, pointSize);
            ellipse(robot.robotLength / 3, robot.robotWidth / 2, pointSize, pointSize);
            ellipse(-robot.robotLength / 3, -robot.robotWidth / 2, pointSize, pointSize);
            ellipse(-robot.robotLength / 3, robot.robotWidth / 2, pointSize, pointSize);*/
            popMatrix();
            ellipse(robot.getLeftWheelPosition().x, robot.getLeftWheelPosition().y, pointSize, pointSize);
            ellipse(robot.getRightWheelPosition().x, robot.getRightWheelPosition().y, pointSize, pointSize);

        }
    }

    public void drawPath() {
        fill(0, 0, 0);
        stroke(0, 0, 0);
        ArrayList<Point> path = PurePursuitHandler.getPath();

        for (int i = 0; i < path.size(); i++) {
            ellipse(path.get(i).x, path.get(i).y, pointSize, pointSize);
            if (i > 1)
                line(path.get(i).x, path.get(i).y, path.get(i - 1).x, path.get(i - 1).y);
        }
    }

    public void drawLookaheadInfo() {
            if (lookaheadPoint != null && robotInitialized) {
                double distance = 2 * Math.sqrt(Math.pow(lookaheadPoint.y - robot.getRobotPosition().y, 2) + Math.pow(lookaheadPoint.x - robot.getRobotPosition().x, 2));
                noFill();
                stroke(0, 0, 0);
                ellipse(robot.getRobotPosition().x, robot.getRobotPosition().y, (float)distance, (float)distance);

                fill(0, 255, 0);
                noStroke();
                ellipse(lookaheadPoint.x, lookaheadPoint.y, pointSize, pointSize);

                noFill();
                stroke(0, 255, 0);
                line(robot.getRobotPosition().x, robot.getRobotPosition().y, lookaheadPoint.x, lookaheadPoint.y);

                stroke(0, 0, 0);
                text(robot.getRobotPosition().x+ ", " + robot.getRobotPosition().y + ", " + Math.toDegrees(robot.getRobotAngle()), 100, 100);
            }

    }

    public void drawTraveledPath() {
        if (robotInitialized) {
            for (int i = 0; i < robot.getTraveled().size() - 1; i += 4) {
                Point p1 = robot.getTraveled().get(i);
                Point p2 = robot.getTraveled().get(i + 1);

                stroke(255, 0, 0);
                line(p1.x, p1.y, p2.x, p2.y);

            }
        }
    }

    public void keyHandler() {
        if (mousePressed) {
            switch (mouseButton) {
                case (LEFT):
                    PurePursuitHandler.addPoint(new Point(mouseX, mouseY));
                    break;
                case (RIGHT):
                    break;
            }
        }
    }

    public void keyPressed() {
        if (robotInitialized) {
            switch (key) {
                case ('f'):
                    double curvature = PurePursuitHandler.calculateCurvature(robot.getRobotAngle(), robot.getRobotPosition(), lookaheadPoint);
                    double[] velocities = PurePursuitHandler.getTargetVelocities(curvature, robot.maxVelocity, robot.robotWidth);

                    robot.setSpeeds((float) velocities[0], (float) velocities[1]);
                    break;
                case ('+'):
                    PurePursuitHandler.addToLookaheadDistance(2.5f);
                    break;
                case ('-'):
                    PurePursuitHandler.addToLookaheadDistance(-2.5f);
                    break;
            }
        }
    }
}
