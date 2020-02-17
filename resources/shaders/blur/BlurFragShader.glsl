#version 330 core

in vec2 uvposition;
in vec2 screensize;
in vec2 dir;
layout(location = 0) out vec4 color;
uniform sampler2D frame;
uniform sampler2D postFrame;

vec4 blur(sampler2D texture, vec2 direction, int samples, float bokeh){

    vec4 sum = vec4(0.0);//результирующий цвет
    vec4 msum = vec4(0.0);//максимальное значение цвета выборок

    if (samples >= 1){
        float delta = 1.0/samples;//порция цвета в одной выборке
        float di = 1.0/(samples-1.0);//вычисляем инкремент
        for (float i=-0.5; i<0.501; i+=di) {
            vec2 uvpos = uvposition + direction * i;
            vec4 color = texture2D(texture, uvpos);//делаем выборку в заданном направлении
            sum += color * delta;//суммируем цвет
            msum = max(color, msum);//вычисляем максимальное значение цвета
        }

        return mix(sum, msum, bokeh);
    }

    return vec4(0, 0, 0, 1);
}

void main() {
    vec4 rawColor;
    vec4 postColor = texture(postFrame, uvposition);
    if (postColor.x > 0){
        rawColor = blur(frame, dir * postColor.x, int(postColor.y * 1000), postColor.z);
    } else {
        rawColor = texture(frame, uvposition);
    }

    color = rawColor;
}