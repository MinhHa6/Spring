package pkg_default_method;

public interface Shape {
    void draw();
    default  void setColor( String color)
    {
        System.out.println("Ve hinh voi mau :"+color);
    }
}
