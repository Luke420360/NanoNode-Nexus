package com.example.nanonodenexus.data;

public record EnemyData (
        int hp,
        int reward,
        double moveSpeed,
        double interval,
        String imageName
) { }