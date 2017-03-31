package org.arquillian.cube.git;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.arquillian.cube.HostIp;
import org.arquillian.cube.HostPort;
import org.arquillian.cube.docker.impl.requirement.RequiresDockerMachine;
import org.arquillian.cube.requirement.ArquillianConditionalRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;

@RunWith(ArquillianConditionalRunner.class)
@RequiresDockerMachine(name = "dev")
public class PingPongTest {

    @HostIp
    String ip;

    @HostPort(containerName = "pingpong", value = 8080)
    int port;

    @Test
    public void should_receive_ok_message() throws IOException {

        URL obj = new URL("http://" + ip + ":" + port);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Http URL");

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        assertThat(response.toString(), is("{  \"status\": \"OK\"}"));
    }
}
