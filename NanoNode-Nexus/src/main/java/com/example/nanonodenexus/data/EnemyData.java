package com.example.nanonodenexus.data;

public record EnemyData (
        int hp,
        int reward,
        double movespeed,
        double interval,
        String imageName
) { }