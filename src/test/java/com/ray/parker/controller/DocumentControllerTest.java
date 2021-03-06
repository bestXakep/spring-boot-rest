package com.ray.parker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.parker.model.DataWrapper;
import com.ray.parker.model.Document;
import com.ray.parker.services.DocumentService;
import com.ray.parker.utils.HttpService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @Autowired
    private DocumentController documentController;

    private MockMvc mockMvc;
    @MockBean
    private DocumentService documentService;
    @MockBean
    private Model model;
    @MockBean
    private Document document;
    @MockBean
    private BindingResult bindingResult;
    @MockBean
    private HttpService httpService;
    private String stringData;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(documentController).build();
        stringData = "{\"data\": [{\"hash\": \"md5:232dba893a22ac722249ad92f8bccf22\"}]}";
    }

    @Test
    public void redirectToList() {
        assertEquals("redirect:/document/list", documentController.redirectToList());
    }

    @Test
    public void listDocuments() {
        assertEquals("document/list", documentController.listDocuments(model));
    }

    @Test
    public void getDocument() {
        assertEquals("document/show", documentController.getDocument("", model));
    }

    @Test
    public void edit() {
        assertEquals("document/documentform", documentController.edit("", model));
    }

    @Test
    public void newDocument() {
        assertEquals("document/documentform", documentController.newDocument(model));
    }

    @Test
    public void saveOrUpdateDocumentSuccess() {
        when(documentService.saveOrUpdate(document)).thenReturn(document);
        assertEquals("redirect:/document/show/" + document.getId(),
                documentController.saveOrUpdateDocument(document, bindingResult));
    }

    @Test
    public void saveOrUpdateDocumentFail() {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("document/documentform",
                documentController.saveOrUpdateDocument(document, bindingResult));
    }

    @Test
    public void delete() {
        assertEquals("redirect:/document/list", documentController.delete(""));
    }

    @Test
    public void saveDocumentsFromEndpointReturnPage() throws IOException {
        when(httpService.getResponseData(any(String.class))).thenReturn(stringData);
        assertEquals("document/list", documentController.saveDocumentsFromEndpoint(model));

    }

    @Test(expected = IOException.class)
    public void saveDocumentsFromEndpointThrowsIOException() throws IOException {
        when(httpService.getResponseData(any(String.class))).thenThrow(IOException.class);
        documentController.saveDocumentsFromEndpoint(model);
    }

    @Test
    public void saveDocumentsFromEndpointSuccessExecutesSaveAllAndListAllMethod() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DataWrapper data = objectMapper.readValue(stringData, DataWrapper.class);
        List<Document> documents = data.getDocuments();
        when(httpService.getResponseData(any(String.class))).thenReturn(stringData);
        documentController.saveDocumentsFromEndpoint(model);
        verify(documentService, times(1)).saveAll(documents);
        verify(documentService, times(1)).listAll();
    }
}