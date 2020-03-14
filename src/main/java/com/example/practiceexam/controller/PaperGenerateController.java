package com.example.practiceexam.controller;

import com.example.practiceexam.service.PaperGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author ShiQing_Chen  2020/3/14  12:22
 **/
@Controller
public class PaperGenerateController {
    private final PaperGenerateService paperGenerateService;

    @Autowired
    public PaperGenerateController(PaperGenerateService paperGenerateService) {
        this.paperGenerateService = paperGenerateService;
    }


}
