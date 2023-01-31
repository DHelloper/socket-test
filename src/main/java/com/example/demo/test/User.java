package com.example.demo.test;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class User {
    String id;
    String name;
    String mode;
    String language;
    int mmr;
}
