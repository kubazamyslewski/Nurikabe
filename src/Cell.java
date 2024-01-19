public class Cell {
    int r;
    int c;
    int size;
    Cell(int r, int c, int size){
        this.r = r;
        this.c = c;
        this.size = size;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }
    public int getSize(){
        return size;
    }

    public String toString(){
        return "(" + r + " " + c +" "+ size + ") ";
    }


}