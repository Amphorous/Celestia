package org.hoyo.celestia.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public enum UpdateStatus {
    /**
        hi
     */
    BAD_UID(HttpStatus.BAD_REQUEST, "Bad UID.", false),
    ENKA_TIMEOUT(HttpStatus.OK, "User is not enka call yet.", false),
    ENKA_USER_NOT_FOUND(HttpStatus.IM_USED, "User found in DB but not in Enka.", false),
    PRIVATE_BUILDS(HttpStatus.OK, "User has private Builds List", false),
    UPDATED(HttpStatus.OK, "User updated successfully.", true),
    CREATED(HttpStatus.OK, "User created successfully.", true),
    UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "User not found.", false);

    private final HttpStatus status;
    private final String message;
    private final boolean success;

    UpdateStatus(HttpStatus status, String message, boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }

    public ResponseEntity<String> toResponseEntity() {
        return ResponseEntity.status(status).body(message);
    }

    public ResponseEntity<Boolean> isSuccess() {
        return ResponseEntity.status(status).body(success);
    }

}

