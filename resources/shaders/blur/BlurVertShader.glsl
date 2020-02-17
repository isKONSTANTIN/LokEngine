#version 330 core

layout(location = 0) in vec2 vertexPosition;
layout(location = 1) in vec2 UVPosition;

uniform mat4 Projection;
uniform vec2 screenSize;
uniform vec2 direction;
out vec2 uvposition;
out vec2 screensize;
out vec2 dir;
void main() {
    dir = direction;
    uvposition = UVPosition;
    screensize = screenSize;
    gl_Position = Projection * vec4(vertexPosition.x, vertexPosition.y, 0, 1);
}