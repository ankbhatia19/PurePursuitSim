public class TwoWheelRobotSimulator extends RobotSimulator
{
    // Variables to keep track of left and right wheel position
    // Mimics the function of encoders.
    private float leftWheelPosition;
    private float rightWheelPosition;

    private float lastPos; // Previous position of the robot, used for odometry.
    private float angle; // In radians

    // Some robot constants
    public float robotWidth = 20;
    public float robotLength = 30;
    public float maxVelocity = 2.5f;

    /**
     * Constructor for a two wheeled robot.
     *
     * @param position Starting position of the robot.
     */
    public TwoWheelRobotSimulator(Point position) {
        super(position);
        leftWheelPosition = 0;
        rightWheelPosition = 0;
        angle = 0;
        lastPos = (leftWheelPosition + rightWheelPosition) / 2;
    }

    /**
     * Returns the coordinates of the robot. Also serves as an odometry function.
     *
     * @return A Point object containing position of the robot.
     */
    public Point getRobotPosition(){
        float currentPos = (leftWheelPosition + rightWheelPosition) / 2;
        float dPos = currentPos - lastPos;
        position.x += Math.cos(angle) * dPos;
        position.y += Math.sin(angle) * dPos;
        lastPos = currentPos;
        return position;
    }

    /**
     * Keeps track of the robot's angle.
     *
     * @return Current angle of the robot.
     * TODO Fix the angle so that it is not limited to just -pi / 2 to +pi / 2
     */
    public float getRobotAngle(){
        angle = (float) (Math.atan((rightWheelPosition - leftWheelPosition) / robotWidth));
        return angle;
    }

    /**
     * Function used for controlling the robot speed. Also adds points to the list of points the robot has traveled to.
     *
     * @param leftSpeed The speed left wheel should go at.
     * @param rightSpeed The speed right wheel should go at.
     */
    public void setSpeeds(float leftSpeed, float rightSpeed){
        traveled.add(new Point(position.x, position.y));
        leftWheelPosition += leftSpeed;
        rightWheelPosition += rightSpeed;
    }
}
