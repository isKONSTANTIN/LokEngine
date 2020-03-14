#version 330 core

layout(location = 0) in vec2 vertexPosition;
layout(location = 1) in vec2 UVPosition;

out vec2 uvposition;
out vec4 objectcolor;
uniform mat4 Projection;
uniform mat4 View;
uniform float ObjectSize;
uniform vec4 ObjectColor;
uniform mat4 ObjectModelMatrix;

void main() {
    uvposition = UVPosition;
    objectcolor = ObjectColor;
    gl_Position = Projection * View * ObjectModelMatrix * vec4(vertexPosition.x * ObjectSize, vertexPosition.y * ObjectSize, 1, 1);
}