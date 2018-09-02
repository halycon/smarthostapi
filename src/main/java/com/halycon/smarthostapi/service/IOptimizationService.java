package com.halycon.smarthostapi.service;

@FunctionalInterface
public interface IOptimizationService<P,R> {
    R optimize(P p) throws Exception;
}
