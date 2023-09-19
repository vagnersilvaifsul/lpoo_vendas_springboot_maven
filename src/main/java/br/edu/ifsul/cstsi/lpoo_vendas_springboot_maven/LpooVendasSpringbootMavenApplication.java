package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LpooVendasSpringbootMavenApplication {
    public static void main(String[] args) {
        SpringApplication.run(LpooVendasSpringbootMavenApplication.class, args);
        HomeController.main(null);
    }
}
