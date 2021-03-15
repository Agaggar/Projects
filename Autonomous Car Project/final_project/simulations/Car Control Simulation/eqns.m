%% Equations file for simulation 2 - Planar handling model.  
function [ds, ext] = eqns(t,s)

%% Parameters
global m b a Cf Jy U Ku_MR Cr_MR mu Ku_R Cr_R sf sr Nf Nr ti d0

%% Variable initialization - omega = theta_dot
% Reference car
V = s(1);
theta = s(2);
omega = s(3);
x = s(4);
y = s(5);

%Real car
V_R = s(6);
theta_R = s(7);
omega_R = s(8);
x_R = s(9);
y_R = s(10);
erri = s(11);

ds = zeros(11,1);
ext = zeros(10,1);

%% Inputs
% Delta
if t > ti
    delta = d0*pi/180;
else 
    delta = 0;
end
% 
% delta = d0*pi/180*sin(2*pi*t);

%% Gain schedule for Ki and Kp
% This was found using the MATLAB PID tuning tool. The final equations were
% found by tuning the gains for a settling time of magnitude 10^-2 and then
% assuming that the gains for change from the found Ts to a Ts of 0.02 were 
% proportional. These were then curve fit in Excel. 
Ki = 0.5*1903*U^(-0.916);
Kp = 0.5*(0.0556*U + 6.0513);

%% Calculation of side force - Reference Car
% alpha = wheel slip angle and F = Ca*alpha
alpha_f = delta - (V + a*omega)/U;
alpha_r = (b*omega-V)/U;
Ff = Cf*alpha_f;
Fr = Cr_MR*alpha_r;

%% Calculation of side force - Real Car
% alpha = wheel slip angle and F = Ca*alpha
err = omega-omega_R;
delta_c = Kp*err + Ki*erri;
alpha_f_R = delta_c - (V_R + a*omega_R)/U;
alpha_r_R = (b*omega_R-V_R)/U;

%For the front
F_xd_f=Cf*sf/(1-sf);
F_yd_f=Cf*tan(alpha_f_R)/(1-sf);
Q_f=sqrt( (F_xd_f/Nf)^2+(F_yd_f/Nf)^2 );
if Q_f==0;
    Q_f=.0001;                       %Makes sure there is no divide by zero
end;
lambda_f=mu/2/Q_f;
if lambda_f >=1;
    Fx_f=F_xd_f; Fy_f=F_yd_f;
else
   Fx_f=F_xd_f*2*lambda_f*(1-lambda_f/2);
   Fy_f=F_yd_f*2*lambda_f*(1-lambda_f/2);
end;
Ff_R=Fy_f;

%For the rear
F_xd_r=Cr_R*sr/(1-sr);
F_yd_r=Cr_R*tan(alpha_r_R)/(1-sr);
Q_r=sqrt((F_xd_r/Nr)^2+(F_yd_r/Nr)^2 );
if Q_r == 0;
    Q_r=.0001;                    %Makes sure there is no divide by zero
end;
lambda_r=mu/2/Q_r;
if lambda_r >=1;
    Fx_r=F_xd_r; Fy_r=F_yd_r;
else
   Fx_r=F_xd_r*2*lambda_r*(1-lambda_r/2);
   Fy_r=F_yd_r*2*lambda_r*(1-lambda_r/2);
end;
Fr_R=Fy_r;

%% Equations of Motion - Reference Car
V_dot = Ff/m + Fr/m - U*omega;
theta_dot = omega;
omega_dot = a*Ff/Jy - b*Fr/Jy;
x_dot = U*cos(theta) - V*sin(theta);
y_dot = U*sin(theta) + V*cos(theta);

%% Equations of Motion - Real Car
V_dot_R = Ff_R/m + Fr_R/m - U*omega_R;
theta_dot_R = omega_R;
omega_dot_R = a*Ff_R/Jy - b*Fr_R/Jy;
x_dot_R = U*cos(theta_R) - V_R*sin(theta_R);
y_dot_R = U*sin(theta_R) + V_R*cos(theta_R);
erri_dot = err;

%% Extra outputs
ext(1) = Ff;
ext(2) = Fr;
ext(3) = alpha_f;
ext(4) = alpha_r;
ext(5) = delta;
ext(6) = Ff_R;
ext(7) = Fr_R;
ext(8) = alpha_f_R;
ext(9) = alpha_r_R;
ext(10) = delta_c;

ds = [V_dot; theta_dot; omega_dot; x_dot; y_dot;....
      V_dot_R; theta_dot_R; omega_dot_R; x_dot_R; y_dot_R; erri_dot];