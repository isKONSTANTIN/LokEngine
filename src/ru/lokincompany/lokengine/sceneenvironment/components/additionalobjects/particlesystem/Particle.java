package ru.lokincompany.lokengine.sceneenvironment.components.additionalobjects.particlesystem;

import org.lwjgl.util.vector.Vector2f;

public class Particle {
    public float positionX;
    public float positionY;
    public float speedX;
    public float speedY;
    public float lifeTime;
    public float size;

    public Particle(Vector2f position, float size, float lifeTime) {
        this.positionX = position.x;
        this.positionY = position.y;
        this.size = size;
        this.lifeTime = lifeTime;
    }
}
