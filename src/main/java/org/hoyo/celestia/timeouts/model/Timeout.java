package org.hoyo.celestia.timeouts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
@AllArgsConstructor
public class Timeout {
    @Id
    String uid;
    Instant timestamp;
}

