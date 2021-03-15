clc; clear all; close all;
CoordData = importdata('coordinates.txt');

time = CoordData.data(:,1);
delta = time(1);
time = time-delta;

xPos = CoordData.data(:,2);
yPos = -1*CoordData.data(:,3);
uVel = CoordData.data(:,4);
vVel = CoordData.data(:,5);

figure(1)
subplot(2,1,1)
plot(time,xPos,'b','Linewidth',2)
hold on
grid on
ylabel('meters?')
xlabel('Time [s]')
title('xPos vs Time')
subplot(2,1,2)
plot(time,uVel,'r','Linewidth',2)
hold on
grid on
ylabel('meters/s?')
xlabel('Time [s]')
title('uVel vs Time')

figure(2)
plot(xPos,yPos,'k','Linewidth',2)
hold on
grid on
axis equal

title('Inertia Nav Map')
xlabel('xPos')
ylabel('yPos')

