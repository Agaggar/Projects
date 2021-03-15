#!/usr/bin/env python3

import rospy
from ucsd_robo_car_simple_ros.srv import CustomServiceMessage, CustomServiceMessageResponse 
from drive import driveRobot
import sys

def my_callback(request):
    rospy.loginfo("The Service driveRobotTest has been called")
    print("Request Data==> duration="+str(request.duration))
    my_response = CustomServiceMessageResponse()
    driveRobot_object = driveRobot()
    driveRobot_object.turnRobot(request.angle)
    driveRobot_object.moveRobot(request.duration, request.power)
    driveRobot_object.turnReset()
    rospy.loginfo("Finished service driveRobotTest")
    my_response.success = True
    return  my_response # the service Response class, in this case MyCustomServiceMessageResponse

rospy.init_node('service_driveRobot_server') 
driveRobot_test = rospy.Service('/driveRobotTest', CustomServiceMessage , my_callback) # create the Service called my_service with the defined callback

rospy.loginfo("Service /driveRobotTest Ready")
rospy.spin() # mantain the service open.