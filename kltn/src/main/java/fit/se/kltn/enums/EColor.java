package fit.se.kltn.enums;

import lombok.Data;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum EColor {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7);
    @JsonValue
    private final int value;

    EColor(int value) {
        this.value = value;
    }
}


