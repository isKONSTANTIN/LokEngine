#version 330 core

in vec2 uvposition;
in vec4 objectcolor;
layout(location = 0) out vec4 color;
uniform sampler2D frame;

void main() {
	color = texture(frame, uvposition);
	color = vec4(color.r * objectcolor.r, color.g * objectcolor.g, color.b * objectcolor.b, color.a * objectcolor.a);
}