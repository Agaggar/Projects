%Dugoff tire Calculations 
s = 0;
m = 3000/2.2; % kg, Total mass
L = 2.84; % m, Wheelbase 
rab = 0.85; % Ratio of a to b
b = L/(1+rab); % m, Length from cg to rear axle
a = L-b; % m, length from cg to front axle
Cf = 50000; % N/rad, Front cornering coefficient in the linear range
g = 9.81; % m/s^2, Gravity
d0 = 6;
mu = 0.85; % Coefficeint of kinetic friction
N = b*m*g/(a+b); % N, Front normal force
alpha = 0:1:20;
Fx = zeros(size(alpha));
Fy = zeros(size(alpha));

%Calculate the longitudinal force as a function of slip. For this lab 
%s=0. equation 6

Fx = Cf*s/(1-s);

%Calculate the lateral force as a function of slip angle and slip. Eq 6. 
Fy = Cf*tan(alpha)/(1-s);

%Calculate the denominator of lambda and make sure that it is not 0. Eq 7.
Q = sqrt((Fx/N)^2+(Fy/N)^2);
if Q == 0;
    Q = 0.0001;                       
end;

lambda=mu/2/Q;

%Check lambda and calculate Fx and Fy accordingly. Eq 8-9.
if lambda >= 1
    Fx=Fx; 
    Ff=Fy;
else
   Fx=Fx*2*lambda*(1-lambda/2);
   Fy=Fy*2*lambda*(1-lambda/2);
end;

plot(alpha,Fy)



