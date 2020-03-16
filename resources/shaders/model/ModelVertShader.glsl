#version 330 core

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec2 UVPosition;
layout(location = 2) in vec3 NormalPosition;

uniform mat4 Projection;
uniform mat4 View;
uniform mat4 ObjectModelMatrix;

uniform vec3 AmbientColor;
uniform vec3 DiffuseColor;
uniform vec3 SpecularColor;
uniform vec3 CamPosition;

out vec2 uvposition;
out vec3 normalPosition;
out vec3 uAmbientColor;
out vec3 uDiffuseColor;
out vec3 uSpecularColor;
out vec3 camPosition;
out vec3 vPosition;

void main() {
    uvposition = UVPosition;
    normalPosition = NormalPosition;
    uAmbientColor = AmbientColor;
    uDiffuseColor = DiffuseColor;
    uSpecularColor = SpecularColor;
    camPosition = CamPosition;
    vPosition = vertexPosition;

    gl_Position = Projection * View * ObjectModelMatrix * vec4(vertexPosition.x, vertexPosition.y, vertexPosition.z, 1);
}