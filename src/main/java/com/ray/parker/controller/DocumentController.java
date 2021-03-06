package com.ray.parker.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.parker.model.DataWrapper;
import com.ray.parker.model.Document;
import com.ray.parker.services.DocumentService;
import com.ray.parker.utils.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


/**
 * Created by Ray Parker on 25/01/18.
 */
@Controller
public class DocumentController implements WebMvcConfigurer {
    private final static Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);
    private DocumentService documentService;
    private HttpService httpService;
    private MessageSource messageSource;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/document/list").setViewName("list");
    }

    @Autowired
    public void setDocumentService(DocumentService documentService, HttpService httpService, MessageSource messageSource) {
        this.documentService = documentService;
        this.httpService = httpService;
        this.messageSource = messageSource;
    }

    @GetMapping("/")
    public String redirectToList() {
        return "redirect:/document/list";
    }

    @GetMapping({"/document/list", "/document"})
    public String listDocuments(Model model) {
        model.addAttribute("documents", documentService.listAll());
        return "document/list";
    }

    @GetMapping("/document/show/{id}")
    public String getDocument(@PathVariable String id, Model model) {
        model.addAttribute("document", documentService.getById(id));
        return "document/show";
    }

    @GetMapping("document/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Document document = documentService.getById(id);
        model.addAttribute("document", document);
        return "document/documentform";
    }

    @GetMapping("/document/new")
    public String newDocument(Model model) {
        model.addAttribute("document", new Document()); // -> th:object="${document} in html page
        return "document/documentform";
    }

    @PostMapping("/document")
    public String saveOrUpdateDocument(@Valid Document document, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "document/documentform";
        }
        Document savedDocument = documentService.saveOrUpdate(document);
        return "redirect:/document/show/" + savedDocument.getId();
    }

    @RequestMapping("/document/delete/{id}")
    public String delete(@PathVariable String id) {
        documentService.delete(id);
        return "redirect:/document/list";
    }

    @RequestMapping("/document/fromEndPoint")
    public String saveDocumentsFromEndpoint(Model model) throws IOException {
        String stringData = httpService.getResponseData("https://lb-api-sandbox.prozorro.gov.ua/api/2.4/" +
                "contracts/4805f381d48948b1b34d6ea2daa029a3/documents");
        ObjectMapper objectMapper = new ObjectMapper();
        DataWrapper data = objectMapper.readValue(stringData, DataWrapper.class);
        List<Document> documents = data.getDocuments();
        documentService.saveAll(documents);
        LOGGER.info("Data from endpoint was successfully saved.");
        documents = documentService.listAll();
        model.addAttribute("documents", documents);
        return "document/list";
    }
}
