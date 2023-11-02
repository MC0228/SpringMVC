package com.shisan.service;

import com.shisan.mapper.BookMapper;
import com.shisan.pojo.Books;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:shisan
 * @Date:2023/11/2 7:40
 */
public class BookService {
    @Resource
    private BookMapper bookMapper;

    public Integer add(Books books) {
        return bookMapper.add(books);
    }


    public Integer delete(Integer bookID) {
        return bookMapper.delete(bookID);
    }

    public Books selectById(Integer bookID) {
        return bookMapper.selectById(bookID);
    }

    public Integer update(Books books) {
        return bookMapper.update(books);
    }

    public List<Books> findAll() {
        return bookMapper.findAll();
    }

    public void setBookMapper(BookMapper bookMapper) {

    }
}
