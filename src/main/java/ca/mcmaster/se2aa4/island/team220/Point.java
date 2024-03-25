package ca.mcmaster.se2aa4.island.team220;

public class Point {
    private Integer x;
    private Integer y;

    /**
     * Create a Point.
     * @param x X-coordinate
     * @param y Y-coordinate
     */
    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() { return x; }

    public Integer getY() { return y; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Point)) { return false; }

        Point point = (Point) o;

        if (!x.equals(point.getX())) { return false; }
        return y.equals(point.getY());
    }

    /**
     * Translates a point forward relative to a given direction.
     * @param direction Direction to translate the point
     * @return Translated Point
     */
    public Point translateForward(Direction direction) {
        int newX = this.x;
        int newY = this.y;

        switch (direction) {
            case NORTH:
                newY -= 1;
                break;
            case SOUTH:
                newY += 1;
                break;
            case EAST:
                newX -= 1;
                break;
            case WEST:
                newX += 1;
                break;
            default:
                break;
        }
        return new Point(newX, newY);
    }

    /**
     * Translates a point up and to the left given a direction.
     * @param direction Direction to translate point
     * @return Translated Point
     */
    public Point translateForwardLeft(Direction direction) {
        int newX = this.x;
        int newY = this.y;

        switch (direction) {
            case NORTH:
                newY -= 1;
                newX += 1;
                break;
            case SOUTH:
                newY += 1;
                newX -= 1;
                break;
            case EAST:
                newY -= 1;
                newX -= 1;
                break;
            case WEST:
                newY += 1;
                newX += 1;
                break;
            default:
                break;
        }
        return new Point(newX, newY);
    }

    /**
     * Translates a point up and to the right given a direction.
     * @param direction Direction to translate point
     * @return Translated Point
     */
    public Point translateForwardRight(Direction direction) {
        int newX = this.x;
        int newY = this.y;

        switch (direction) {
            case NORTH:
                newY -= 1;
                newX -= 1;
                break;
            case SOUTH:
                newY += 1;
                newX += 1;
                break;
            case EAST:
                newY += 1;
                newX -= 1;
                break;
            case WEST:
                newY -= 1;
                newX += 1;
                break;
            default:
                break;
        }

        return new Point(newX, newY);
    }

    /**
     * Calculates the distance between two points.
     * @param point2 The second point
     * @return Distance between the two points
     */
    public Double calcDistance(Point point2) {
        return Math.sqrt(Math.pow(this.getX() - point2.getX(), 2) + Math.pow(this.getY() - point2.getY(), 2));
    }

    @Override
    public String toString() {
        return "Point [x =" + x + ", y =" + y + "]";
    }
}