#version 330 core

in vec2 uvposition;
layout(location = 0) out vec4 color;
uniform sampler2D frame;
uniform sampler2D frame2;

void main() {
    vec4 hdr = texture(frame, uvposition);
    vec4 bloom = texture(frame2, uvposition);

    hdr += bloom;

    color = vec4(hdr.rgb, max(hdr.a, bloom.a));
}