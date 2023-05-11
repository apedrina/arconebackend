package io.github.apedrina.web.mock;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockUtilTest {

    @Value("${test.url}")
    public String url;

    @Autowired
    public RestTemplate restTemplate;

    @LocalServerPort
    public String port;

    @AfterClass
    public static void tearDown() {
        try {
            MockServerManager.tearDown();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @BeforeClass
    public static void setUp() {
        try {
            MockServerManager.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Before
    public void setUpEach() {
        url = url + ":" + port;
    }

    @Test
    public void init() {
        log.info("MockUtilTest init...");

        Assert.assertTrue(true);

    }

}
