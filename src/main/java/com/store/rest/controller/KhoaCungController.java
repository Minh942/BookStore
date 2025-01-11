package com.store.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KhoaCungController {

    @GetMapping("/khoa-cung-tho-cong")
    public String khoaCungThoCong() {
        return ""; // Tên của template (khoa-cung-tho-cong.html)
    }
}
