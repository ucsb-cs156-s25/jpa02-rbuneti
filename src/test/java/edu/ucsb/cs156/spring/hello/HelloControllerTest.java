package edu.ucsb.cs156.spring.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getHello_has_expectedHeader() throws Exception {
        MvcResult response = mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String actualContent = response.getResponse().getContentAsString();
        String expectedContent =  """
            <h1>Greetings from Spring Boot!</h1>
            <p>This is a simple example of a Spring Boot application.</p>
            <p><a href="/info">Developer Info</a></p>
            """;
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void getInfo_has_developer_info_header() throws Exception {
         MvcResult response = mvc.perform(MockMvcRequestBuilders.get("/info").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String actualContent = response.getResponse().getContentAsString();
        String expectedContent = "<h1>Developer Info</h1>";

        assertTrue(actualContent.contains(expectedContent),String.format("Expected content to contain: [%s]\nActual content: [%s]", expectedContent, actualContent));
    }


    @Test
    public void get_team_returns_team_object() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper(); // maps between Java objects and JSON objects
         MvcResult response = mvc.perform(MockMvcRequestBuilders.get("/team").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String actualContent = response.getResponse().getContentAsString();
        Team teamReturned = objectMapper.readValue(actualContent, Team.class);
        Team expectedTeam = Developer.getTeam();
        assertEquals(expectedTeam, teamReturned);
    }


    // TODO: Add additional tests as needed to get to 100% jacoco line coverage, and
    // 100% mutation coverage (all mutants timed out or killed)
    @Test
    public void getInfo_displays_correct_developer_info() throws Exception {
    MvcResult response = mvc.perform(MockMvcRequestBuilders.get("/info").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();
    String actualContent = response.getResponse().getContentAsString();

    String expectedContent = String.format("""
            <h1>Developer Info</h1>
            <ul>
              <li>Name: %s</li>
              <li>Github ID: <a href="https://github.com/%s">%s</a></li>
              <li>Team: <a href="/team">%s</a></li>
            </ul>
            """, Developer.getName(), Developer.getGithubId(), Developer.getGithubId(), Developer.getTeam().getName());

    assertEquals(expectedContent, actualContent);
}

}
