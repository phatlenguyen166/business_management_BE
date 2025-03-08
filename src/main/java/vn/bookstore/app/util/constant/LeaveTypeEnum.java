package vn.bookstore.app.util.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LeaveTypeEnum {
    SICK_LEAVE(1),
    PAID_LEAVE(2),
    MATERNITY_LEAVE(3);

    private final int value;
    LeaveTypeEnum(int value) {
        this.value = value;
    }
//    @JsonValue
//    public String getName() {
//        return this.name();
//    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static LeaveTypeEnum fromValue(Object value) {
        if (value == null) {
            return null; // Hoặc giá trị mặc định
        }

        // Xử lý khi giá trị là số
        if (value instanceof Integer) {
            int intValue = (Integer) value;
            for (LeaveTypeEnum type : LeaveTypeEnum.values()) {
                if (type.getValue() == intValue) {
                    return type;
                }
            }
            // Giá trị số không hợp lệ
            return null; // Hoặc throw new IllegalArgumentException("Invalid leave type number: " + intValue);
        }

        // Xử lý khi giá trị là chuỗi
        if (value instanceof String) {
            String stringValue = (String) value;
            for (LeaveTypeEnum type : LeaveTypeEnum.values()) {
                if (type.name().equalsIgnoreCase(stringValue)) {
                    return type;
                }
            }
            // Giá trị chuỗi không hợp lệ
            return null; // Hoặc throw new IllegalArgumentException("Invalid leave type string: " + stringValue);
        }

        // Trường hợp không xác định
        return null; // Hoặc throw exception
    }
}