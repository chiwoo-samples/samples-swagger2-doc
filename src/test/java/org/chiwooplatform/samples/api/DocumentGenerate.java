package org.chiwooplatform.samples.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.chiwooplatform.samples.SwaggerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SwaggerApplication.class)
public class DocumentGenerate {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    private static final String SWAGGER2_API_URI = "/v2/api-docs";

    @Test
    public void generateSwaggerJSON() throws Exception {
        this.mockMvc.perform(get(SWAGGER2_API_URI).accept(MediaType.APPLICATION_JSON)).andDo(result -> {
            File outputDir = new File("src/docs/swagger");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            try (FileOutputStream fos = new FileOutputStream("src/docs/swagger/swagger.json")) {
                fos.write(result.getResponse().getContentAsByteArray());
                fos.flush();
            }
        }).andExpect(status().isOk());
    }

    @Test
    public void writeSingleFile() throws Exception {
        this.mockMvc.perform(get(SWAGGER2_API_URI).accept(MediaType.APPLICATION_JSON)).andDo(result -> {
            MockHttpServletResponse response = result.getResponse();
            response.setCharacterEncoding("UTF-8");
            String swaggerJson = response.getContentAsString();
            Path outputFile = Paths.get("src/docs/asciidoc/single");
            Swagger2MarkupConverter.from(swaggerJson)
                    .withConfig(new Swagger2MarkupConfigBuilder().withMarkupLanguage(MarkupLanguage.ASCIIDOC).build()).build()
                    .toFile(outputFile);
        }).andExpect(status().isOk());
    }

    @Test
    public void writeMultipleFiles() throws Exception {
        this.mockMvc.perform(get(SWAGGER2_API_URI).accept(MediaType.APPLICATION_JSON)).andDo(result -> {
            MockHttpServletResponse response = result.getResponse();
            response.setCharacterEncoding("UTF-8");
            String swaggerJson = response.getContentAsString();
            Path outputFolder = Paths.get("src/docs/asciidoc");

            Swagger2MarkupConverter.from(swaggerJson)
                    .withConfig(new Swagger2MarkupConfigBuilder().withMarkupLanguage(MarkupLanguage.ASCIIDOC).build()).build()
                    .toFolder(outputFolder);
        }).andExpect(status().isOk());
    }

    @Test
    public void writeMarkdownSingleFile() throws Exception {
        this.mockMvc.perform(get(SWAGGER2_API_URI).accept(MediaType.APPLICATION_JSON)).andDo(result -> {
            MockHttpServletResponse response = result.getResponse();
            response.setCharacterEncoding("UTF-8");
            String swaggerJson = response.getContentAsString();
            Path outputFile = Paths.get("src/docs/markdown/single");

            Swagger2MarkupConverter.from(swaggerJson)
                    .withConfig(new Swagger2MarkupConfigBuilder().withMarkupLanguage(MarkupLanguage.MARKDOWN).build()).build()
                    .toFile(outputFile);
        });
    }

    @Test
    public void writeMarkdownMultipleFiles() throws Exception {
        this.mockMvc.perform(get(SWAGGER2_API_URI).accept(MediaType.APPLICATION_JSON)).andDo(result -> {
            MockHttpServletResponse response = result.getResponse();
            response.setCharacterEncoding("UTF-8");
            String swaggerJson = response.getContentAsString();
            Path outputFolder = Paths.get("src/docs/markdown");

            Swagger2MarkupConverter.from(swaggerJson)
                    .withConfig(new Swagger2MarkupConfigBuilder().withMarkupLanguage(MarkupLanguage.MARKDOWN).build()).build()
                    .toFolder(outputFolder);
        });
    }

    @Test
    public void writeConfluenceSingleFile() throws Exception {
        this.mockMvc.perform(get(SWAGGER2_API_URI).accept(MediaType.APPLICATION_JSON)).andDo(result -> {
            MockHttpServletResponse response = result.getResponse();
            response.setCharacterEncoding("UTF-8");
            String swaggerJson = response.getContentAsString();
            Path outputFile = Paths.get("src/docs/confluence/single");

            Swagger2MarkupConverter.from(swaggerJson)
                    .withConfig(new Swagger2MarkupConfigBuilder().withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP).build()).build()
                    .toFile(outputFile);
        });
    }

    @Test
    public void writeConfluenceMultipleFiles() throws Exception {
        this.mockMvc.perform(get(SWAGGER2_API_URI).accept(MediaType.APPLICATION_JSON)).andDo(result -> {
            MockHttpServletResponse response = result.getResponse();
            response.setCharacterEncoding("UTF-8");
            String swaggerJson = response.getContentAsString();
            Path outputFolder = Paths.get("src/docs/confluence");

            Swagger2MarkupConverter.from(swaggerJson)
                    .withConfig(new Swagger2MarkupConfigBuilder().withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP).build()).build()
                    .toFolder(outputFolder);
        });
    }
}
