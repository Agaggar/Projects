%% Simulation 2 - Layne Clemen
%% MAE 234, Spring Term 2013 - Dr. Margolis
% Bicycle

%% Clean up
clc; close all; clear all;

%% Parameters
global m b a Cf Jy U Ku_MR Cr_MR mu Ku_R Cr_R sf sr Nf Nr ti d0

% Parameters common to real and reference vehicle
m = 3000/2.2; % kg, Total mass
L = 2.84; % m, Wheelbase 
rab = 0.85; % Ratio of a to b
b = L/(1+rab); % m, Length from cg to rear axle
a = L-b; % m, length from cg to front axle
Jy = 0.4*m*a*b; % kg-m^2, Yaw moment of inertia 
Cf = 50000; % N/rad, Front cornering coefficient in the linear range
U = 60*0.447; %Forward velocity - m/s
g = 9.81; % m/s^2, Gravity
d0 = 6;

% Reference vehicle parameters
Ku_MR = 1.1; % Model reference understeer coefficient
Cr_MR = Cf*rab*Ku_MR; % N/rad, rear cornering coefficient

% Real vehicle parameters
mu = 0.85; % Coefficeint of kinetic friction
Ku_R = 1.2; % Real vehicle understeer coefficient
Cr_R = Cf*rab*Ku_R; % N/rad, Real vehicle rear cornering coefficient
sf = 0; % Front longitudinal slip ratio
sr = 0; % Rear longitudinal slip ratio
Nf = b*m*g/(a+b); % N, Front normal force
Nr = a*m*g/(a+b); % N, Rear normal Force

% Senso characteristics
%from data sheet
accel_noise = 0.000230/500;
accel_ts = 1/1000;
accel_res = (8*9.81)/2^16;

gyro_noise = 0.000230/1000000;
gyro_ts = 1/1000;
gyro_res = (500*pi/180)/2^16;

mag_noise = 2;
mag_ts = 1/100;
mag_res = (9800)/2^16;

%% Time vector
tf = 25;
tspan = 0:0.01:5;
ti = 0.1;

%% Initial Conditions - Reference Car
% [V0, theta0, omega0, x0, y0]

V0 = 0; % m/s, inital lateral velocity
theta0 = 0; % rad, initial yaw angle 
omega0 = 0; % rad/s, initial yaw rate
x0 = 0; % m, initial x coordinate
y0 = 0; % m, initial y coordinate

initial = [V0, theta0, omega0, x0, y0];

%% Simulation
res = sim('bicycle_car_model.slx')
s = res.s;
ds = res.ds;

% %% Plots
% traj = ['trajec.' num2str(U/0.447) '.' num2str(d0) '.step.jpg']
% RF = ['RearForces.' num2str(U/0.447) '.' num2str(d0) '.step.jpg']
% a_lat = ['a_lat.' num2str(U/0.447) '.' num2str(d0) '.step.jpg']
% steer = ['SteerAngle.' num2str(U/0.447) '.' num2str(d0) '.step.jpg']
% yaw = ['YawRate.' num2str(U/0.447) '.' num2str(d0) '.step.jpg']
% 
figure('Name','Trajectory','NumberTitle','off','Color','white')
plot(s(:,4),s(:,5),'-k','LineWidth',2), grid on
title('Trajectory of Real and Refence Vehicles')
legend('Reference Vehicle','Real Vehicle')
ylabel('Y-position (m)')
xlabel('x-position (m)')
% saveas(gcf,traj)
% 
% figure('Name','Rear Forces','NumberTitle','off','Color','white')
% plot(t,ext(:,2),'-k',t,ext(:,7),'--k','LineWidth',2), grid on
% title('Rear Tire Force of Real and Refence Vehicles')
% legend('Reference Vehicle','Real Vehicle')
% ylabel('F_{R} (N)')
% xlabel('Time (sec)')
% saveas(gcf,RF)
% 
% 
% figure('Name','Steer Angle Inputs','NumberTitle','off','Color','white')
% plot(t,ext(:,5)*180/pi,'-k',t,ext(:,10)*180/pi,'--k','LineWidth',2), grid on
% title('Steer angles of Real and Refence Vehicles')
% legend('\delta','\delta_c')
% ylabel('\delta (degrees)')
% xlabel('Time (sec)')
% saveas(gcf,steer)
% 
% figure('Name','Yaw Rate','NumberTitle','off','Color','white')
% plot(t,s(:,3),'-k',t,s(:,8),'--k','LineWidth',2), grid on
% title('Yaw Rates of Real and Refence Vehicles')
% legend('Reference Vehicle','Real Vehicle')
% ylabel('\omega (rad/sec)')
% xlabel('Time (sec)')
% saveas(gcf,yaw)
% 
% figure('Name','Lateral Acceleration','NumberTitle','off','Color','white')
% plot(t,(ds(:,1)-U*s(:,3)),'-k',t,(ds(:,6)-U*s(:,8)),'--k','LineWidth',2), grid on
% title('Lateral Acceleration of Real and Refence Vehicles')
% legend('Reference Vehicle','Real Vehicle')
% ylabel('a_{lat} (m/sec^2)')
% xlabel('Time (sec)')
% saveas(gcf,a_lat)


