#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

varying vec4 varying_Color;
void main (void)
{
 gl_FragColor = varying_Color;
 gl_FragColor = vec4(0.5, 0.0, 1.0, 1.0);
} 