package com.franquicias.controller;

import com.franquicias.model.Franquicia;
import com.franquicias.model.Producto;
import com.franquicias.model.Sucursal;
import com.franquicias.service.FranquiciaService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franquicias")
public class FranquiciasController {

    private final FranquiciaService franquiciaService;


    public FranquiciasController(FranquiciaService franquiciaService) {
        this.franquiciaService = franquiciaService;
    }

    @PostMapping
    public Mono<Franquicia> agregarFranquicia(@RequestBody Franquicia franquicia) {
        return franquiciaService.agregarFranquicia(franquicia);
    }

    @PostMapping("/{nombreFranquicia}/sucursales")
    public Mono<Franquicia> agregarSucursal(@PathVariable String nombreFranquicia, @RequestBody Sucursal sucursal) {
        return franquiciaService.agregarSucursal(nombreFranquicia, sucursal);
    }

    @PostMapping("/{nombreFranquicia}/sucursales/{nombreSucursal}/productos")
    public Mono<Franquicia> agregarProducto(@PathVariable String nombreFranquicia, @PathVariable String nombreSucursal, @RequestBody Producto producto) {
        return franquiciaService.agregarProducto(nombreFranquicia, nombreSucursal, producto);
    }

    @DeleteMapping("/{nombreFranquicia}/sucursales/{nombreSucursal}/productos/{nombreProducto}")
    public Mono<Franquicia> eliminarProducto(@PathVariable String nombreFranquicia, @PathVariable String nombreSucursal, @PathVariable String nombreProducto) {
        return franquiciaService.eliminarProducto(nombreFranquicia, nombreSucursal, nombreProducto);
    }

    @PatchMapping("/{nombreFranquicia}/sucursales/{nombreSucursal}/productos/{nombreProducto}/stock")
    public Mono<Franquicia> modificarStock(@PathVariable String nombreFranquicia, @PathVariable String nombreSucursal, @PathVariable String nombreProducto, @RequestParam int nuevoStock) {
        return franquiciaService.modificarStock(nombreFranquicia, nombreSucursal, nombreProducto, nuevoStock);
    }

    @GetMapping("/{nombreFranquicia}/productos-con-mayor-stock")
    public Flux<Producto> obtenerProductoConMayorStockPorSucursal(@PathVariable String nombreFranquicia) {
        return franquiciaService.obtenerProductoConMayorStockPorSucursal(nombreFranquicia);
    }

    @PatchMapping("/{nombreFranquicia}/nombre")
    public Mono<Franquicia> actualizarNombreFranquicia(@PathVariable String nombreFranquicia, @RequestParam String nuevoNombre) {
        return franquiciaService.actualizarNombreFranquicia(nombreFranquicia, nuevoNombre);
    }

    @PatchMapping("/{nombreFranquicia}/sucursales/{idSucursal}/nombre")
    public Mono<Franquicia> actualizarNombreSucursal(@PathVariable String nombreFranquicia, @PathVariable String idSucursal, @RequestParam String nuevoNombre) {
        return franquiciaService.actualizarNombreSucursal(nombreFranquicia, idSucursal, nuevoNombre);
    }

    @PatchMapping("/{nombreFranquicia}/sucursales/{nombreSucursal}/productos/{nombreProducto}/nombre")
    public Mono<Franquicia> actualizarNombreProducto(@PathVariable String nombreFranquicia, @PathVariable String nombreSucursal, @PathVariable String nombreProducto, @RequestParam String nuevoNombre) {
        return franquiciaService.actualizarNombreProducto(nombreFranquicia, nombreSucursal, nombreProducto, nuevoNombre);
    }
}
