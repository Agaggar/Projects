clc; clear all
s = tf('s');
hertz = 0.02; % sampling frequency
tau = 0.2;

P = 1/(tau*s+1);
c2d(P,hertz)