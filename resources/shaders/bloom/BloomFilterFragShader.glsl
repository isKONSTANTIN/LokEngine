#version 330 core

in vec2 uvposition;
layout(location = 0) out vec4 color;
uniform sampler2D frame;
in float brightnessLimit;

void main() {
    vec4 frameColor = texture(frame, uvposition);
    float brightness = dot(frameColor.rgb, vec3(0.2126, 0.7152, 0.0722));

    if (brightness <= brightnessLimit)
    frameColor = vec4(0.0, 0.0, 0.0, 0.0);

    color = frameColor;
}