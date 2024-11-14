package com.franquicias.service;

import com.franquicias.model.Franquicia;
import com.franquicias.model.Producto;
import com.franquicias.model.Sucursal;
import com.franquicias.repository.FranquiciaRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
public class FranquiciaServiceImpl implements FranquiciaService{

    private final FranquiciaRepository franquiciaRepository;

    public FranquiciaServiceImpl(FranquiciaRepository franquiciaRepository) {
        this.franquiciaRepository = franquiciaRepository;
    }

    @Override
    public Mono<Franquicia> agregarFranquicia(Franquicia franquicia) {
        return franquiciaRepository.save(franquicia);
    }

    @Override
    public Mono<Franquicia> agregarSucursal(String nombreFranquicia, Sucursal sucursal) {

        return franquiciaRepository.findByNombre(nombreFranquicia)
                .flatMap(franquicia -> {
                    franquicia.getSucursales().add(sucursal);
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Mono<Franquicia> agregarProducto(String nombreFranquicia, String nombreSucursal, Producto producto) {
        return franquiciaRepository.findByNombre(nombreFranquicia)
                .flatMap(franquicia -> {
                    franquicia.getSucursales().stream()
                            .filter(sucursal -> sucursal.getNombre().equals(nombreSucursal))
                            .findFirst()
                            .ifPresent(sucursal -> sucursal.getProductos().add(producto));
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Mono<Franquicia> eliminarProducto(String nombreFranquicia, String nombreSucursal, String nombreProducto) {
        return franquiciaRepository.findByNombre(nombreFranquicia)
                .flatMap(franquicia -> {
                    franquicia.getSucursales().stream()
                            .filter(sucursal -> sucursal.getNombre().equals(nombreSucursal))
                            .findFirst()
                            .ifPresent(sucursal -> sucursal.getProductos().removeIf(producto -> producto.getNombre().equals(nombreProducto)));
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Mono<Franquicia> modificarStock(String nombreFranquicia, String nombreSucursal, String nombreProducto, int nuevoStock) {
        return franquiciaRepository.findByNombre(nombreFranquicia)
                .flatMap(franquicia -> {
                    franquicia.getSucursales().stream()
                            .filter(sucursal -> sucursal.getNombre().equals(nombreSucursal))
                            .flatMap(sucursal -> sucursal.getProductos().stream())
                            .filter(producto -> producto.getNombre().equals(nombreProducto))
                            .findFirst()
                            .ifPresent(producto -> producto.setStock(nuevoStock));
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Flux<Producto> obtenerProductoConMayorStockPorSucursal(String nombreFranquicia) {
        return franquiciaRepository.findByNombre(nombreFranquicia)
                .flatMapMany(franquicia -> Flux.fromIterable(franquicia.getSucursales()))
                .mapNotNull(sucursal -> sucursal.getProductos().stream().max(Comparator.comparingInt(Producto::getStock)).orElse(null))
                .filter(producto -> producto != null);
    }


    @Override
    public Mono<Franquicia> actualizarNombreFranquicia(String nombreFranquicia, String nuevoNombre) {
        return franquiciaRepository.findByNombre(nombreFranquicia)
                .flatMap(franquicia -> {
                    franquicia.setNombre(nuevoNombre);
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Mono<Franquicia> actualizarNombreSucursal(String nombreFranquicia, String idSucursal, String nuevoNombre) {
        return franquiciaRepository.findByNombre(nombreFranquicia)
                .flatMap(franquicia -> {
                    franquicia.getSucursales().stream()
                            .filter(sucursal -> sucursal.getId().equals(idSucursal))
                            .findFirst()
                            .ifPresent(sucursal -> sucursal.setNombre(nuevoNombre));
                    return franquiciaRepository.save(franquicia);
                });
    }

    @Override
    public Mono<Franquicia> actualizarNombreProducto(String nombreFranquicia, String idSucursal, String nombreProductoActual, String nuevoNombreProducto) {
        return franquiciaRepository.findByNombre(nombreFranquicia)
                .flatMap(franquicia -> {
                    franquicia.getSucursales().stream()
                            .filter(sucursal -> sucursal.getId().equals(idSucursal))
                            .flatMap(sucursal -> sucursal.getProductos().stream())
                            .filter(producto -> producto.getNombre().equals(nombreProductoActual))
                            .findFirst()
                            .ifPresent(producto -> producto.setNombre(nuevoNombreProducto));
                    return franquiciaRepository.save(franquicia);
                });
    }
}
