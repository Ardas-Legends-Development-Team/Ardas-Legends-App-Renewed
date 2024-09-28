package com.ardaslegends.presentation.api.response.unit;


import com.ardaslegends.domain.Unit;

public record UnitResponse(
        String unitName,
        Integer count,
        Integer amountAlive
) {
    public UnitResponse(Unit unit) {
        this(
                unit.getUnitType().getUnitName(),
                unit.getCount(),
                unit.getAmountAlive()
        );
    }
}
