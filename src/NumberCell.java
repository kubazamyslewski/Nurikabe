public class NumberCell {
    int r;
    int c;
    int number;
    NumberCell(int r, int c, int number){
        this.r = r;
        this.c = c;
        this.number = number;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public int getNumber() {
        return number;
    }
    public String toString(){
        String s = "r:"+ r +" c:" + c +" number: "+ number;
        return s;
    }

}

