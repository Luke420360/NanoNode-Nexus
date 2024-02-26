package com.example.nanonodenexus.data;

public record EnemyBaseData(
        int hp,
        int reward,
        double moveSpeed,
        double interval,
        String imageName
) {}
