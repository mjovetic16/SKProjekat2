package com.example.skpr2.skprojekat2mainservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {




    public TestDataRunner() {

    }

    @Override
    public void run(String... args) throws Exception {

    }
}
