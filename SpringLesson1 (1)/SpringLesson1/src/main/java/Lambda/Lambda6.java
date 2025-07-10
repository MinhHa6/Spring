package Lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class Book {
    int id;
    String name;
    float price;

    public Book(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
public class Lambda6 {
    public static void main(String[] args) {
        List<Book> books= new ArrayList<>();
        books.add(new Book(1,"Lap trinh Java",9.95f));
        books.add(new Book(2,"Spring boot",19.95f));
        books.add(new Book(3,"Php laravel",12.95f));
        books.add(new Book(4,"Netcore",29.95f));
        // loc sach co gia lon hon 15k
        Stream<Book>filter=books.stream().filter(b->b.price>15);
        filter.forEach(System.out::println);
    }
}
