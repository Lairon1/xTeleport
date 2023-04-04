package com.lairon.xteleport.handler.namedlocation;

import com.lairon.xteleport.model.Player;
import com.lairon.xteleport.model.location.Location;
import com.lairon.xteleport.model.location.NamedLocation;
import lombok.NonNull;

public interface AbstractNamedLocationHandler<T> {

    void teleportHandle(@NonNull Player player, @NonNull T location);

    void setHandle(@NonNull Player player, @NonNull NamedLocation location);

    void deleteHandle(@NonNull Player player, @NonNull T location);

    void moveHandle(@NonNull Player player, @NonNull T location, @NonNull Location newLocation);
    void showAllHandle(@NonNull Player player);

}
