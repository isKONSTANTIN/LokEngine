#version 330 core

in vec2 uvposition;
in vec2 screensize;
layout(location = 0) out vec4 color;
uniform sampler2D frame;

void main() {
	color = texture(frame, uvposition);
}