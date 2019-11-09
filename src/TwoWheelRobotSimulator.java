public class TwoWheelRobotSimulator extends RobotSimulator {
    // Variables to keep track of left and right wheel position
    // Mimics the function of encoders.
    private float leftWheelEncoder;
    private float rightWheelEncoder;
    private float leftPreviousEncoderVal;
    private float rightPreviousEncoderVal;

    private Point leftWheelPosition;
    private Point rightWheelPosition;

    private float angle; // In radians

    // Some robot constants
    public float robotWidth = 20;
    public float robotLength = 30;
    public float maxVelocity = 2.5f;
    private float r = robotWidth / 2;

    /**
     * Constructor for a two wheeled robot.
     *
     * @param position Starting position of the robot.
     */
    public TwoWheelRobotSimulator(Point position) {
        super(position);
        leftWheelEncoder = 0;
        rightWheelEncoder = 0;
        leftPreviousEncoderVal = 0;
        rightPreviousEncoderVal = 0;
        angle = (float) Math.PI / 2;
        leftWheelPosition = new Point((float) (position.x - r * Math.sin(angle)), (float) (position.y + r * Math.cos(angle)));
        rightWheelPosition = new Point((float) (position.x + r * Math.sin(angle)), (float) (position.y - r * Math.cos(angle)));
    }

    /**
     * A helper method used to find the angle of the robot given a few parameters.
     *
     * @param rx The right wheel x position
     * @param ry The right wheel y position
     * @param lx The left wheel x position
     * @param ly The left wheel y position
     * @return The current angle of the robot.
     */
    private float findAngle(float rx, float ry, float lx, float ly) {
        float m = (ry - ly) / (rx - lx); // Finds the slope of the axle of the robot
        float a = (float) Math.atan(m); // Since atan will only ever return -pi / 2 to pi / 2,
                                        // we will have to find the true angle ourselves.
        if (rx < lx)
            a += Math.PI;
        a += Math.PI / 2;
        if (a > 2 * Math.PI)
            a -= 2 * Math.PI;
        return a;
    }

    /**
     * Returns the coordinates of the robot. Also serves as an odometry function.
     *
     * @return A Point object containing position of the robot.
     */

    public Point getRobotPosition() {

        System.out.println(leftWheelEncoder + "," + rightWheelEncoder + "," + rightWheelPosition.x + ", " + rightWheelPosition.y + ", " + leftWheelPosition.x + ", " + leftWheelPosition.y + ", " + angle);

        float dl = leftWheelEncoder - leftPreviousEncoderVal;
        float dr = rightWheelEncoder - rightPreviousEncoderVal;

        float dPos = (dl + dr) / 2;
        Point prevlwpos = new Point(leftWheelPosition.x, leftWheelPosition.y);
        Point prevrwpos = new Point(rightWheelPosition.x, rightWheelPosition.y);
        position.x += Math.cos(angle) * dPos;
        position.y += Math.sin(angle) * dPos;

        leftWheelPosition.x = (float) (prevlwpos.x + dl * Math.cos(angle));
        leftWheelPosition.y = (float) (prevlwpos.y + dl * Math.sin(angle));
        rightWheelPosition.x = (float) (prevrwpos.x + dr * Math.cos(angle));
        rightWheelPosition.y = (float) (prevrwpos.y + dr * Math.sin(angle));
        angle = findAngle(rightWheelPosition.x, rightWheelPosition.y, leftWheelPosition.x, leftWheelPosition.y);
        leftPreviousEncoderVal = leftWheelEncoder;
        rightPreviousEncoderVal = rightWheelEncoder;

        return position;
    }

    /**
     * Returns the current robot angle, which we keep track of through odometry.
     *
     * @return Current angle of the robot.
     */
    public float getRobotAngle() {
        return angle;
    }

    /**
     * Function used for controlling the robot speed. Also adds points to the list of points the robot has traveled to.
     *
     * @param leftSpeed  The speed left wheel should go at.
     * @param rightSpeed The speed right wheel should go at.
     */
    public void setSpeeds(float leftSpeed, float rightSpeed) {
        traveled.add(new Point(position.x, position.y));

        leftWheelEncoder += r * Math.atan(leftSpeed / r);
        rightWheelEncoder += r * Math.atan(rightSpeed / r);
        /*leftWheelEncoder += leftSpeed;
        rightWheelEncoder += rightSpeed;*/
    }

    public Point getLeftWheelPosition() {
        return leftWheelPosition;
    }

    public Point getRightWheelPosition() {
        return rightWheelPosition;
    }
}
