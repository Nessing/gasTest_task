package ru.nessing.client;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.nessing.client.enums.Commands;
import ru.nessing.client.methods.ZipHelper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Client {
    private static final String URL = "http://localhost:8080";
    public static void main(String[] args) throws IOException, IllegalAccessException {
        // INFO
        System.out.println("Write command");
        System.out.println("/load [path to folder]");
        System.out.println("example: /load folder");
        System.out.println("/result [id of file]");
        System.out.println("example: /result 1");
        System.out.println("[/exit] for exit from the program");
        System.out.println("↓↓↓↓↓↓↓↓");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            if (command.startsWith(Commands.LOAD.toString())) {
                load(command);
            } else if (command.startsWith(Commands.RESULT.toString())) {
                result(command);
            } else if (command.startsWith(Commands.EXIT.toString())) return;
            else {
                System.out.println("not correct command");
            }
        }

    }

    private static void load(String command) throws IOException, IllegalAccessException {
        command = command.replaceFirst("/load ", "");
        File filePath = zip(command);
        if (filePath == null) {
            System.out.println("this path doesn't exist");
            return;
        }
        FileSystemResource file = new FileSystemResource(filePath);

        String serverUrl = URL;
        RestTemplate restTemplateGetId = new RestTemplate();
        ResponseEntity<String> getId = restTemplateGetId
                .getForEntity(serverUrl, String.class, "");

        String id = getId.getBody();
        System.out.println(getId.getBody());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("file", file);
        body.add("id", id);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        RestTemplate restTemplateStartFile = new RestTemplate();
        ResponseEntity<String> startFile = restTemplateStartFile
                .postForEntity(serverUrl, requestEntity, String.class);
        System.out.println(startFile.getBody());
    }

    private static void result(String command) {
        command = command.replaceFirst("/result ", "");
        String serverUrl = URL + "/result";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("id", command);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, request, String.class);

        System.out.println(response.getBody());
    }

    private static File zip(String path) throws IOException, IllegalAccessException {
        ZipHelper zipHelper = new ZipHelper();
        return zipHelper.zip(new File(path));
    }
}
