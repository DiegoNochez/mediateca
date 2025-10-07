package com.diego.mediateca.app;

import com.diego.mediateca.repo.MaterialRepository;
import com.diego.mediateca.services.MaterialService;
import java.time.LocalDate;

public class DemoAgregar {
    public static void main(String[] args) {
        var repo = new MaterialRepository();
        var service = new MaterialService(repo);

        var l1 = service.agregarLibro(
                "Patrones de Diseño", "GoF", 395, "Addison-Wesley", "978-0201633610", 1994, 3);
        var r1 = service.agregarRevista(
                "National Geographic", "NatGeo", "Mensual", LocalDate.of(2024, 10, 1), 10);

        System.out.println(l1);
        System.out.println(r1);
        System.out.println("Total almacenado: " + repo.findAll().size());
    }
}
