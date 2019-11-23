#version 330 core

in vec2 uvposition;
layout(location = 0) out vec4 color;
uniform sampler2D frame;
uniform sampler2D frame2;
in float exposure;
in float gamma;

void main() {
    vec4 hdr = texture(frame, uvposition);
    vec4 bloom = texture(frame2, uvposition);

    vec3 hdrColor = vec3(hdr.rgb);
    vec3 bloomColor = vec3(bloom.rgb);

    vec3 result = hdrColor * exposure;//vec3(1.0) - exp(-hdrColor * exposure);

    result = pow(result, vec3(1.0 / gamma));
    result += bloomColor;
    color = vec4(result, max(hdr.a,bloom.a));
}