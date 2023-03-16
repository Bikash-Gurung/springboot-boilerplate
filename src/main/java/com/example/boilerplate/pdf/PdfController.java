package com.example.boilerplate.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping
    public void generatePdf() {
        try {
            pdfService.generate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
