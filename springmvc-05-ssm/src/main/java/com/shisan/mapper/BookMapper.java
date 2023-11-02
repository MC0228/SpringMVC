package com.shisan.mapper;

import com.shisan.pojo.Books;

import java.util.List;

/**
 * @Author:shisan
 * @Date:2023/11/2 7:30
 */
public interface BookMapper {

    Integer add(Books books);

    Integer delete(Integer bookID);

    Integer update(Books books);

    Books selectById(Integer bookId);

    List<Books> findAll();
}
