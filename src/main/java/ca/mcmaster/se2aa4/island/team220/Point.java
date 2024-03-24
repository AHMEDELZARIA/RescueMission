package ca.mcmaster.se2aa4.island.team220;

public class Point {

    private Integer x;
    private Integer y;

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

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
        if (this == o)
            return true;
        if (!(o instanceof Point))
            return false;

        Point point = (Point) o;

        if (!x.equals(point.getX()))
            return false;
        return y.equals(point.getY());
    }

    public Point translateForward(Direction direction) {
        int newX = this.x;
        int newY = this.y;

//        switch (startDirection) {
//            case NORTH, EAST:
//                switch (direction) {
//                    case NORTH -> newY += 1;
//                    case SOUTH -> newY -= 1;
//                    case EAST -> newX += 1;
//                    case WEST -> newX -= 1;
//                }
//            case WEST:
//                switch (direction) {
//                    case NORTH -> newY += 1;
//                    case SOUTH -> newY -= 1;
//                    case EAST -> newX -= 1;
//                    case WEST -> newX += 1;
//                }
//            case SOUTH:
//                switch (direction) {
//                    case NORTH -> newY -= 1;
//                    case SOUTH -> newY += 1;
//                    case EAST -> newX -= 1;
//                    case WEST -> newX += 1;
//                }
//        }
        switch (direction) {
            case NORTH:
                newY += 1;
                break;
            case SOUTH:
                newY -= 1;
                break;
            case EAST:
                newX += 1;
                break;
            case WEST:
                newX -= 1;
                break;
            default:
                break;
        }

        return new Point(newX, newY);
    }

    public Point translateForwardLeft(Direction direction) {
        int newX = this.x;
        int newY = this.y;

        switch (direction) {
            case NORTH:
                newY += 1;
                newX -= 1;
                break;
            case SOUTH:
                newY -= 1;
                newX += 1;
                break;
            case EAST:
                newY += 1;
                newX += 1;
                break;
            case WEST:
                newY -= 1;
                newX -= 1;
                break;
            default:
                break;
        }

        return new Point(newX, newY);
    }

    public Point translateForwardRight(Direction direction) {
        int newX = this.x;
        int newY = this.y;

        switch (direction) {
            case NORTH:
                newY += 1;
                newX += 1;
                break;
            case SOUTH:
                newY -= 1;
                newX -= 1;
                break;
            case EAST:
                newY -= 1;
                newX += 1;
                break;
            case WEST:
                newY += 1;
                newX -= 1;
                break;
            default:
                break;
        }

        return new Point(newX, newY);
    }

    @Override
    public String toString() {
        return "Point [x =" + x + ", y =" + y + "]";
    }

    public Double calcDistance(Point point2) {
        return Math.sqrt(Math.pow(this.getX() - point2.getX(), 2) + Math.pow(this.getY() - point2.getY(), 2));
    }
}
