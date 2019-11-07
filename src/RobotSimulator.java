import java.util.ArrayList;
import java.util.List;

public class RobotSimulator {
    // follower coordinates
    protected Point position;

    // follower speed
    protected float speed = 2.5f;

    // follower path that it has traveled
    protected ArrayList<Point> traveled;

    public RobotSimulator(Point position) {
        this.position = position;
        traveled = new ArrayList<>();
    }

    /**
     * Moves the follower towards a point by the follower's speed.
     *
     * @param point The point towards which the robot must move.
     */
    public void moveRobotTowardsPoint(Point point) {
        // add the last robot point to the list of traveled points
        traveled.add(new Point(position.x, position.y));
        System.out.println("Position Added: " + position.x + ", " + position.y);

        // move the point to origin (the follower's coordinates)
        float offsetX = point.x - position.x;
        float offsetY = point.y - position.y;

        // normalize the vector
        float distanceToPoint = (float) Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        float normalizedX = offsetX / distanceToPoint;
        float normalizedY = offsetY / distanceToPoint;

        // move towards the point at a certain speed
        position.x += normalizedX * speed;
        position.y += normalizedY * speed;

    }

    /**
     * Returns the coordinates of the follower.
     *
     * @return A point object containing the position of the simulated robot.
     */
    public Point getRobotPosition() {
        return position;
    }

    /**
     * Returns a list containing all the points the robot has visited.
     *
     * @return An ArrayList object containing all previously visited Points of the RobotSimulator
     */
    public ArrayList<Point> getTraveled(){
        return traveled;
    }
}