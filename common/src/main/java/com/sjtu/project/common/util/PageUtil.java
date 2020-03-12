package com.sjtu.project.common.util;

import com.sjtu.project.common.response.PageResult;
import org.springframework.data.domain.Page;

public class PageUtil {
    public static <T> PageResult<T> convert2PageResult(Page<T> page) {
        PageResult<T> res = new PageResult<>();
        res.setContent(page.getContent());
        res.setNumber(page.getNumber());
        res.setTotalPages(page.getTotalPages());
        return res;
    }
}
