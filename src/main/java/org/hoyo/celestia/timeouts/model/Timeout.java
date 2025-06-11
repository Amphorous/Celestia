package org.hoyo.celestia.timeouts.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
public class Timeout {
    @Id
    String id;
    String uid;
    Instant timestamp;
}

