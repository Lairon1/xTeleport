package com.lairon.xteleport.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Vector {

    private double x, y, z;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        if (Double.compare(vector.x, x) != 0) return false;
        if (Double.compare(vector.y, y) != 0) return false;
        return Double.compare(vector.z, z) == 0;
    }

}
