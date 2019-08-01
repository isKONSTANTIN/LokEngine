#version 330 core

layout(location = 0) in vec2 vertexPosition;
layout(location = 1) in vec2 UVPosition;
uniform mat4 Projection;
uniform vec2 screenSize;
out vec2 uvposition;
out vec2 screensize;
void main() {
	uvposition = UVPosition;
	screensize = screenSize;
	gl_Position = Projection * vec4(vertexPosition.x, vertexPosition.y, 0, 1);
}