%% Simulation 2 - Controller Design File

%% Clean Up
clc; close all; clear all;

%% Initialize Parameters
% Parameters common to real and reference vehicle
m = 3000/2.2; % kg, Total mass
L = 2.84; % m, Wheelbase 
rab = 0.85; % Ratio of a to b
b = L/(1+rab); % m, Length from cg to rear axle
a = L-b; % m, length from cg to front axle
Jy = 0.4*m*a*b; % kg-m^2, Yaw moment of inertia 
Cf = 50000; % N/rad, Front cornering coefficient in the linear range
U = 30*0.447; %Forward velocity - m/s

% Reference vehicle parameters
Ku_MR = 1.1; % Model reference understeer coefficient
Cr_MR = Cf*rab*Ku_MR; % N/rad, rear cornering coefficient

% Real vehicle parameters
mu = 0.85; % Coefficeint of kinetic friction
Ku_R = 1.2; % Real vehicle understeer coefficient
Cr_R = Cf*rab*Ku_R; %N/rad, Real vehicle rear cornering coefficient

P = tf([a*Cf/Jy (a+b)*Cr_MR*Cf/(m*Jy*U)],...
    [1 (Cf+Cr_MR)/(m*U)+(a^2*Cf+b^2*Cr_MR)/(Jy*U)...
    (a+b)^2*Cf*Cr_MR/(m*Jy*U^2)+(b*Cr_MR-a*Cf)/Jy]);

pidtool(P,'pi')