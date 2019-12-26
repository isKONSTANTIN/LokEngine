#version 330 core

layout(location = 0) in vec2 vertexPosition;
layout(location = 1) in vec2 UVPosition;
uniform mat4 Projection;
uniform float Exposure;
uniform float Gamma;
uniform vec3 ColorAddition;
uniform vec3 ColorMultiplication;

out vec2 uvposition;
out float exposure;
out float gamma;
out vec3 colorAddition;
out vec3 colorMultiplication;

void main() {
	uvposition = UVPosition;
	exposure = Exposure;
	gamma = Gamma;
	colorAddition = ColorAddition;
	colorMultiplication = ColorMultiplication;

	gl_Position = Projection * vec4(vertexPosition.x, vertexPosition.y, 0, 1);
}