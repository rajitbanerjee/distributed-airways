package com.distributed.airways.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Flight")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight implements Serializable {
    @Id
    private String id;
    private String flightNumber;
}
