package com.myl.electronicsignatureservice;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModularityTest {
    @Test
    public void applicationModules() {
        ApplicationModules modules = ApplicationModules.of(ElectronicSignatureServiceApplication.class);
        modules.forEach(System.out::println);
        modules.verify();

    }

    @Test
    void createDocumentation(){
        ApplicationModules modules = ApplicationModules.of(ElectronicSignatureServiceApplication.class);
        new Documenter(modules).writeDocumentation();
    }
}
