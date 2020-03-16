#version 330 core

in vec2 uvposition;
in vec3 normalPosition;
in vec3 uAmbientColor;
in vec3 uDiffuseColor;
in vec3 uSpecularColor;
in vec3 camPosition;
in vec3 vPosition;

layout(location = 0) out vec4 color;
uniform sampler2D texture;
uniform int Textured;

void main() {
    vec3 uLightPosition = vec3(30, 100, 50);
    float ambientStrength = 0.5;
    float diffuseStrength = 0.5;
    float specularStrength = 1;
    float shininess = 4.0;
    vec3 ambientColor = ambientStrength * uAmbientColor;
    vec3 normal = normalize(normalPosition);
    vec3 lightDirection = normalize(uLightPosition - vPosition);
    vec3 diffuseColor = diffuseStrength * max(0.0, dot(normal, lightDirection)) * uDiffuseColor;
    vec3 viewDirection = normalize(camPosition - vPosition);
    vec3 reflectDirection = reflect(-lightDirection, normal);
    vec3 specularColor = specularStrength * pow(max(dot(viewDirection, reflectDirection), 0.0), shininess) * uSpecularColor;

    if (Textured == 1){
        color = texture2D(texture, uvposition) + vec4(ambientColor + diffuseColor + specularColor, 0);
    }else{
        color = vec4(ambientColor + diffuseColor + specularColor, 1);
    }

}