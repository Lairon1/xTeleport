package com.lairon.xteleport.data;

import com.lairon.xteleport.model.location.SpawnLocation;
import com.lairon.xteleport.model.location.WarpLocation;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpawnDataProvider {

    Optional<SpawnLocation> findByName(@NonNull String name);
    List<SpawnLocation> findAll();
    void save(@NonNull SpawnLocation spawn);
    void delete(@NonNull SpawnLocation spawn);

}
