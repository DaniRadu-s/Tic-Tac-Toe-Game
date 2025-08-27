package start;

import app.model.*;
import app.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import rest.client.Client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class StartRestClient {
    private final static Client playersClient = new Client();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = LocalDateTime.now().format(formatter);

    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
