package com.turkishmenu.backend.factory;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FactoryProvider {

    private final Map<String, RegionFactory> factories;

    public FactoryProvider(List<RegionFactory> factoryList) {
        this.factories = factoryList.stream()
                .collect(Collectors.toMap(
                        RegionFactory::getRegionKey,
                        f -> f
                ));
    }

    public RegionFactory getFactory(String regionKey) {
        RegionFactory factory = factories.get(regionKey.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Bilinmeyen bölge: " + regionKey);
        }
        return factory;
    }

    public List<RegionFactory> getAllFactories() {
        return new ArrayList<>(factories.values());
    }
}
