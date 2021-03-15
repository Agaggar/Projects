#!/usr/bin/env python2.7

import rospy
import math

from time import time
from sensor_msgs.msg import Imu
from std_msgs.msg import Header
from geometry_msgs.msg import Quaternion, QuaternionStamped
from tf.transformations import euler_from_quaternion, quaternion_from_euler

car_output = QuaternionStamped()
f = open(r"/home/jetson/projects/catkin_ws/src/razor_imu_9dof/nodes/coordinates.txt", "w")
f.write("current_time, x_position, y_position, U_velocity, V_velocity\n")
rospy.init_node('robocar04')
# Initialize variables
x = 0
y = 0
x_dot = 0
y_dot = 0
U = 0
V = 0
W = 0
last_t = rospy.get_rostime()
i = 1
bias_accel_x = 0
bias_accel_y = 0
bias_accel_z = 0
lin_accel_x = 0
lin_accel_y = 0
lin_accel_z = 0
# tau = 0.1, k1 = 0.1813, k2 = -0.8187
# tau = 0.2, k1 = 0.2581, k2 = -0.7419
# tau = 0.05, k1 = 0.3297, k2 = -0.6703
k1 = 0.2581
k2 = -0.7419
g = 9.80665
yaw = 0
def callback(imuMsg):
    delta_error = 0.0000000001
    num_bias_samples = 50
    global bias_accel_x
    global bias_accel_y
    global bias_accel_z
    global lin_accel_x
    global lin_accel_y
    global lin_accel_z

    global i
    global last_t
    if i <= num_bias_samples:
      	bias_accel_x = bias_accel_x + imuMsg.linear_acceleration.x
      	bias_accel_y = bias_accel_y + imuMsg.linear_acceleration.y
      	bias_accel_z = bias_accel_z + imuMsg.linear_acceleration.z
        i = i + 1
	last_t = rospy.Time.now()
    
    if i > num_bias_samples:

        lin_accel_x = k1*(imuMsg.linear_acceleration.x - bias_accel_x/num_bias_samples) + k2*lin_accel_x
        lin_accel_y = k1*(imuMsg.linear_acceleration.y - bias_accel_y/num_bias_samples) + k2*lin_accel_y
        lin_accel_z = k1*(imuMsg.linear_acceleration.z - bias_accel_z/num_bias_samples + g) + k2*lin_accel_z
        ang_vel_z = imuMsg.angular_velocity.z

        #rospy.loginfo("accel x: " + str(lin_accel_x))
        #rospy.loginfo("accel y: " + str(lin_accel_y))
        #rospy.loginfo("accel z: " + str(imuMsg.linear_acceleration.z))
        #rospy.loginfo("accel z: " + str(lin_accel_z))
        #rospy.loginfo("bin accel: " + str(bias_accel_x/num_bias_samples))
        #rospy.loginfo("bin accel: " + str(bias_accel_y/num_bias_samples))
        #rospy.loginfo("bin accel: " + str(bias_accel_z/num_bias_samples))
        

        if abs(lin_accel_x) < delta_error:
            lin_accel_x = 0
        if abs(lin_accel_y) < delta_error:
            lin_accel_y = 0
        if abs(lin_accel_z-g) < delta_error:
            lin_accel_z = g
        if abs(ang_vel_z) < delta_error:
            ang_vel_z = 0

	#rospy.loginfo("deadband accel x: " + str(lin_accel_x))
        #rospy.loginfo("deadband accel y: " + str(lin_accel_y))
        #rospy.loginfo("deadband accel z: " + str(lin_accel_z))

        global x
        global y
        global x_dot
        global y_dot
        global U
        global V
	global yaw
        current_time = rospy.Time.now()
        delta_t = imuMsg.header.stamp - last_t
        delta_t = delta_t.to_sec()
        #delta_t = 1.0/250
	last_t = imuMsg.header.stamp
        #rospy.loginfo("delta_t: "+ str(delta_t))
        # Calculate the longitudinal and lateral accelerations
        U_dot = lin_accel_x + V*ang_vel_z
        V_dot = lin_accel_y - U*ang_vel_z
        # Integrate to get longitudinal and lateral velocities
        U = U + U_dot*delta_t
        V = V + V_dot*delta_t

        db_error = 0.01
	if abs(U) < db_error:
            U = 0.0
        if abs(V) < db_error:
            V = 0.0

	#rospy.loginfo("U: " + str(U))
        #rospy.loginfo("V: " + str(V))
        #orientation_car = [imuMsg.orientation.x, imuMsg.orientation.y, imuMsg.orientation.z, imuMsg.orientation.w]
        # angles = euler_from_quaternion(orientation_car, axes='sxyz')
        # yaw = angles[2]
        yaw = yaw + ang_vel_z*delta_t
	yaw = yaw % (2*math.pi)
        x_dot = U*math.cos(yaw) + V*math.sin(yaw)
        y_dot = -U*math.sin(yaw) + V*math.cos(yaw)
        	
        # Integrate to get current estimated position
        x = x + x_dot*delta_t
        y = y + y_dot*delta_t
	#rospy.loginfo(angles)
        rospy.loginfo(str(x) + ", " + str(y) + ", " + str(U) +  ", "+ str(V) + ", " + str(yaw))

        # Publish x, y, x_dot, y_dot, U, V
        car_coordinates = Quaternion(x, y, U, V)
        # car_coordinates[0] = x
        # car_coordinates[1] = y
        # car_coordinates[2] = U
        # car_coordinates[3] = V
        car_output.header = Header(imuMsg.header.seq, current_time, "coordinates")
        car_output.quaternion = car_coordinates

        car_pub.publish(car_output)
        #rospy.loginfo("Published data to /final_project" + "\n\n")
        # Save x, y, x_dot, y_dot, U, V to a data file
        f.write(str(current_time.to_sec()) + ", " + str(x) + ", " + str(y) + ", " + str(U) + ", " + str(V) + ", " + str(yaw) + "\n")
        #f.close()


car_pub = rospy.Publisher('/final_project', QuaternionStamped, queue_size=10)
car_sub = rospy.Subscriber('/imu', Imu, callback)
rospy.spin()
