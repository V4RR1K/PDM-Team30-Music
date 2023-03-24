package com.pib2000;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        pibCLI cli = new pibCLI();
        cli.userLoginMenu();
        cli.run();
    }
}
