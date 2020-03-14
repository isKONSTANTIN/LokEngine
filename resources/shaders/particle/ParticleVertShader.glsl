#version 330 core

layout(location = 0) in vec2 vertexPosition;
layout(location = 1) in vec2 UVPosition;
layout(location = 2) in float ObjectSize;
layout(location = 3) in vec2 ObjectPos;

out vec2 uvposition;

uniform mat4 Projection;
uniform mat4 View;
uniform mat4 ObjectModelMatrix;

void main() {
    uvposition = UVPosition;
    gl_Position = Projection * View * ObjectModelMatrix * vec4(vertexPosition.x * ObjectSize + ObjectPos.x, vertexPosition.y * ObjectSize + ObjectPos.y, 1, 1);
}