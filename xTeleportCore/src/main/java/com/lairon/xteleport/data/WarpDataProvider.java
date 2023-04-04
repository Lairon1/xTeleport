package com.lairon.xteleport.data;

import com.lairon.xteleport.model.location.WarpLocation;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarpDataProvider {

    Optional<WarpLocation> findByUUID(@NonNull UUID uuid);
    Optional<WarpLocation> findByName(@NonNull String name);
    List<WarpLocation> findAll();
    void save(@NonNull WarpLocation warp);
    void delete(@NonNull WarpLocation warp);

}
