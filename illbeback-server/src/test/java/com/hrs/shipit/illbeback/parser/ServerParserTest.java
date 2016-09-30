package com.hrs.shipit.illbeback.parser;

import com.hrs.shipit.illbeback.ssl_workaround.SSLWorkAround;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerParserTest {

    @Autowired ServerParser parser;

    @Test
    public void parseJobList() throws Exception {
        SSLWorkAround.disableSslVerification();

        assertThat(parser.parseServerStatus("https://ci.jenkins-ci.org").getJobs(), IsCollectionWithSize.hasSize(9));

        assertThat(parser.parseServerStatus("https://ci.jenkins-ci.org")
            .getJobs(), hasItem(hasProperty("url", equalTo("https://ci.jenkins.io/job/Core/"))));
    }

}