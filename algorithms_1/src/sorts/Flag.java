package sorts;

public class Flag {

    private String color;

    public String getColor() {
        return color;
    }

    public Flag (String s)
    {
        if (!(s == "r" || s == "w" || s == "b")) throw new IllegalArgumentException("only accept r, w or b.");
        color = s;
    }
}
