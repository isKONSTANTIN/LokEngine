#version 330 core

in vec2 uvposition;
layout(location = 0) out vec4 color;
uniform sampler2D frame;
in float exposure;
in float gamma;
in vec3 colorAddition;
in vec3 colorMultiplication;

void main() {
    vec4 frameTexture = texture(frame, uvposition);

    vec3 frameColor = vec3(1.0) - exp(-vec3(frameTexture.rgb) * exposure);
    frameColor = pow(frameColor, vec3(1.0 / gamma));

    color = vec4(frameColor * colorMultiplication + colorAddition, frameTexture.a);
}