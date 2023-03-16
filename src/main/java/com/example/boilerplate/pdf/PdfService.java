package com.example.boilerplate.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class PdfService {

    @Autowired
    public MessageSource messageSource;

    public void generate() throws IOException, DocumentException, TemplateException {
        String html = parseFreemarkerTemplate();
        generatePdfFromHtml(html);
    }

    public void generatePdfFromHtml(String html) throws IOException, DocumentException {
        OutputStream outputStream = Files.newOutputStream(new File("src/main/resources/generated.pdf").toPath());

        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("/fonts/noto/NotoSans-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.getFontResolver().addFont("/fonts/noto/arial-unicode-ms.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.setDocumentFromString(html, "");
        renderer.getSharedContext().setReplacedElementFactory(new MediaReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory()));
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }

    private String parseFreemarkerTemplate() throws IOException, TemplateException {
        // Create a FreeMarker configuration
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassForTemplateLoading(PdfService.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");


        // Load the FreeMarker template
        Template template = cfg.getTemplate("index.ftl");

        Map<String, Object> model = new HashMap<>();
        model.put("to", "Hello, नमस्कार , 안녕, こんにちは, привет");

        // Merge the data model with the FreeMarker template and generate the HTML output
        String htmlOutput = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        return htmlOutput;
    }

    private String parseThymeleafTemplate() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        String message = messageSource.getMessage("greeting", new Object[]{}, new Locale("ne", "NP"))
                + " / " + messageSource.getMessage("greeting", new Object[]{}, new Locale("fr", "FR"));

        Context context = new Context();
        context.setVariable("to", message);

        return templateEngine.process("index", context);
    }
}
