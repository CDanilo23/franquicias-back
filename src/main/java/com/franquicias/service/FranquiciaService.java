package com.franquicias.service;

import com.franquicias.model.Franquicia;
import com.franquicias.model.Producto;
import com.franquicias.model.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaService {
    Mono<Franquicia> agregarFranquicia(Franquicia franquicia);
    Mono<Franquicia> agregarSucursal(String nombreFranquicia, Sucursal sucursal);
    Mono<Franquicia> agregarProducto(String nombreFranquicia, String nombreSucursal, Producto producto);
    Mono<Franquicia> eliminarProducto(String nombreFranquicia, String nombreSucursal, String nombreProducto);
    Mono<Franquicia> modificarStock(String nombreFranquicia, String nombreSucursal, String nombreProducto, int nuevoStock);
    Flux<Producto> obtenerProductoConMayorStockPorSucursal(String nombreFranquicia);
    Mono<Franquicia> actualizarNombreFranquicia(String idFranquicia, String nuevoNombre);
    Mono<Franquicia> actualizarNombreSucursal(String idFranquicia, String idSucursal, String nuevoNombre);
    Mono<Franquicia> actualizarNombreProducto(String idFranquicia, String idSucursal, String nombreProductoActual, String nuevoNombreProducto);
}
