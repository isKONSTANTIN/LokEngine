#version 330 core

layout(location = 0) in vec2 vertexPosition;
layout(location = 1) in vec2 UVPosition;

out vec2 uvposition;
uniform mat4 Projection;
uniform mat4 View;
uniform float ObjectSize;
uniform mat4 ObjectModelMatrix;

void main() {
	uvposition = UVPosition;
	gl_Position = Projection * View * ObjectModelMatrix * vec4(vertexPosition.x * ObjectSize, vertexPosition.y * ObjectSize, 1, 1);
}