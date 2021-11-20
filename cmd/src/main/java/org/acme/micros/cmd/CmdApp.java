package org.acme.micros.cmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CmdApp {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(CmdApp.class, args)));
    }
}
