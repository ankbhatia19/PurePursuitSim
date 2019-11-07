public class Point {
    public float x;
    public float y;
    private boolean passed;

    public Point(float x, float y){
        this.x = x;
        this.y = y;
        passed = false;
    }

    public void setPassed(){
        passed = true;
    }

    public boolean isPassed(){
        return passed;
    }

    public boolean equals(Point other){
        if (x == other.x && y == other.y)
            return true;
        return false;
    }
}
