package Devmaster_Leson8.Controller;

import Devmaster_Leson8.Service.AuthorService;
import Devmaster_Leson8.Service.BookService;
import Devmaster_Leson8.entity.Author;
import Devmaster_Leson8.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book") // ✅ giữ đúng theo chuẩn
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    private static final String UPLOAD_DIR = "src/main/resources/static/";
    private static final String UPLOAD_PathFile = "images/products/";

    // ✅ Hiển thị toàn bộ sách
    @GetMapping
    public String listBook(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/book-list";
    }

    // ✅ Hiển thị form tạo sách mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthor());
        return "books/book-form";
    }

    // ✅ Xử lý lưu sách mới
    @PostMapping("/new")
    public String saveBook(@ModelAttribute Book book,
                           @RequestParam List<Long> authorsIds,
                           @RequestParam("imageBook") MultipartFile imageFile,
                           Model model) {
        if (!imageFile.isEmpty()) {
            try {
                // Tạo thư mục nếu chưa có
                Path uploadPath = Paths.get(UPLOAD_DIR + UPLOAD_PathFile);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Lấy tên file gốc và đuôi mở rộng
                String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

                // Tạo tên file mới từ mã sách
                String newFileName = book.getCode() + fileExtension;
                Path filePath = uploadPath.resolve(newFileName);

                // Lưu file lên thư mục
                Files.copy(imageFile.getInputStream(), filePath);

                // Gán đường dẫn ảnh cho Book
                book.setImgUrl("/" + UPLOAD_PathFile + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Lỗi khi lưu ảnh!");
                return "books/book-form";
            }
        }

        // Gán danh sách tác giả
        List<Author> authors = new ArrayList<>(authorService.findAllById(authorsIds));
        book.setAuthors(authors);
        bookService.saveBook(book);

        return "redirect:/book"; // ✅ Đã sửa: đúng đường dẫn @RequestMapping
    }

    // ✅ Hiển thị form chỉnh sửa sách
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthor());
        return "books/book-form";
    }

    // ✅ Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) { // ✅ đã thêm @PathVariable
        bookService.deleteBook(id);
        return "redirect:/book"; // ✅ giữ đúng với RequestMapping
    }
}
