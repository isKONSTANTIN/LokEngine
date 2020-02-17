#version 330 core

layout(location = 0) in vec2 vertexPosition;
layout(location = 1) in vec2 UVPosition;
uniform mat4 Projection;
uniform float BrightnessLimit;
out vec2 uvposition;
out float brightnessLimit;
void main() {
    uvposition = UVPosition;
    brightnessLimit = BrightnessLimit;
    gl_Position = Projection * vec4(vertexPosition.x, vertexPosition.y, 0, 1);
}