import java.util.Objects;

public class Pair {
    int r;
    int c;
    Pair(int r, int c){
        this.r = r;
        this.c = c;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public String toString(){
        return "" + r + " " + c +" ";
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair pair)) return false;
        return r == pair.r && c == pair.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }
}
