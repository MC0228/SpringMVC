package com.shisan.controller;

import com.shisan.pojo.Books;
import com.shisan.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:shisan
 * @Date:2023/11/2 8:59
 */
@Controller
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/findAll")
    public String findAll(Model model) {
        List<Books> books = bookService.findAll();
        model.addAttribute("books", books); // 使用 "books" 作为属性名称，而不是 "findAll"
        return "findAll"; // 视图名称可以是 "findAll"，前提是你的视图解析器配置正确
    }

    @PutMapping("/add")
    public String add(@ModelAttribute("book") Books book) {
        bookService.add(book);
        return "redirect:/book/findAll"; // 重定向到显示所有图书的页面
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("book") Books book) {
        bookService.update(book);
        return "redirect:/book/findAll"; // 重定向到显示所有图书的页面
    }

    @GetMapping("/update/{bookID}")
    public String edit(@PathVariable("bookID") Integer bookID, Model model) {
        Books book = bookService.selectById(bookID);
        model.addAttribute("book", book);
        return "update"; // 前往编辑页面
    }

    @DeleteMapping("/delete/{bookID}")
    public String delete(@PathVariable("bookID") Integer bookID) {
        bookService.delete(bookID);
        return "redirect:/book/findAll"; // 重定向到显示所有图书的页面
    }
}

