public class NumberIsland {
    private int r;
    private int c;
    private int number;
    private int area;

    NumberIsland(int r, int c, int number, int area) {
        this.r = r;
        this.c = c;
        this.number = number;
        this.area = area;
    }

    public int getNumber() {
        return number;
    }

    public int getArea() {
        return area;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }


    @Override
    public String toString() {
        return "" + r + " " + c + " " + number + " " + area +" ";
    }
}