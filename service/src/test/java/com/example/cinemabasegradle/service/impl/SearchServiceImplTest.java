package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.config.EmbeddedTestServiceConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"testService", "testJdbc"})
@SpringBootTest(classes = EmbeddedTestServiceConfig.class)
class SearchServiceImplTest {

//    @Autowired
//    private RestTemplate restTemplate;
//    private SearchService searchService;
//    private SearchDto searchDto;
//
//    {
//        searchDto = new SearchDto();
//        searchDto.setQuery("Matrix");
//    }
//
//    @BeforeEach
//    void setUp() {
//        searchService = new SearchServiceImpl(restTemplate);
//        ReflectionTestUtils.setField(searchService, "apiKey", "0b31fa0abdf8b9e72d279c0646c5bd08");
//        ReflectionTestUtils.setField(searchService, "scheme", "https");
//        ReflectionTestUtils.setField(searchService, "host", "api.themoviedb.org");
//        ReflectionTestUtils.setField(searchService, "searchMovieByName", "/3/search/movie");
//        ReflectionTestUtils.setField(searchService, "searchMoviePopular", "/3/movie/popular");
//        ReflectionTestUtils.setField(searchService, "searchMovieLatest", "/3/movie/latest");
//        ReflectionTestUtils.setField(searchService, "searchMovieById", "/3/movie/");
//    }
//
//    @Test
//    void searchMoviesById() {
//        assertNotNull(searchService.searchMoviesById(603L));
//    }
//
//    @Test
//    void searchMoviesByName() {
//        List<MovieDto> apiDtoList = searchService.searchMoviesByName(searchDto);
//        assertTrue(apiDtoList.stream().count() > 0);
//    }
//
//    @Test
//    void searchMoviesPopular() {
//        List<MovieDto> apiDtoList = searchService.searchMoviesPopular();
//        assertTrue(apiDtoList.stream().count() > 0);
//    }
//
//    @Test
//    void searchMovieLatest() {
//        MovieDto movieDto = searchService.searchMovieLatest();
//        assertNotNull(movieDto.getTitle());
//    }
}