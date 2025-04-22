package eeit.OldProject.allen.Controller;

import eeit.OldProject.allen.Entity.News;
import eeit.OldProject.allen.Entity.NewsCategory;
import eeit.OldProject.allen.Repository.NewsRepository;
import eeit.OldProject.allen.Repository.NewsCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsCategoryRepository newsCategoryRepository;

    private NewsCategory testCategory;

    @BeforeEach
    public void setUp() {
        // 清空資料表
        newsRepository.deleteAll();
        newsCategoryRepository.deleteAll();

        // 建立一筆分類
        testCategory = new NewsCategory();
        testCategory.setCategoryName("測試分類");
        testCategory = newsCategoryRepository.save(testCategory);

        // 建立兩筆新聞
        News news1 = new News();
        news1.setTitle("測試新聞 A");
        news1.setContent("內容 A");
        news1.setCreateBy("admin");
        news1.setCreateAt(LocalDateTime.now());
        news1.setStatus(1);
        news1.setCategory(testCategory);
        newsRepository.save(news1);

        News news2 = new News();
        news2.setTitle("測試新聞 B");
        news2.setContent("內容 B");
        news2.setCreateBy("editor");
        news2.setCreateAt(LocalDateTime.now());
        news2.setStatus(0);
        news2.setCategory(testCategory);
        newsRepository.save(news2);
    }

    @Test
    public void testGetAllNews() throws Exception {
        mockMvc.perform(get("/api/news")) // ✅ 注意這裡是 /api/news
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", containsString("測試新聞")))
                .andExpect(jsonPath("$[1].createBy", is("editor")));
    }
}