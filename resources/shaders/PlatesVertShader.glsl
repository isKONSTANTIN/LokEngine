#version 330 core

layout(location = 0) in vec2 vertexPosition;
layout(location = 1) in vec2 UVPosition;
layout(location = 2) in vec2 ObjectPos;

out vec2 uvposition;

uniform mat4 Projection;
uniform mat4 View;
uniform mat4 ObjectModelMatrix;
uniform vec2 ChuckPosition;

void main() {
	uvposition = UVPosition;
	gl_Position = Projection * View * ObjectModelMatrix * vec4(vertexPosition.x + ObjectPos.x + ChuckPosition.x, vertexPosition.y + ObjectPos.y + ChuckPosition.y, 1, 1);
}