#!/usr/bin/env python3

import rospy
from std_msgs.msg import Float32, Int32

class driveRobot():
    
    def __init__(self):
        self.robotPublishT = rospy.Publisher('/throttle', Float32, queue_size=1)
        self.robotPublishS = rospy.Publisher('/steering', Float32, queue_size=1)

        self.cmd = Float32
        self.ctrl_c = False
        self.rate = rospy.Rate(10) # 10hz
        rospy.on_shutdown(self.shutdownhook)
        
    def publish_onceT(self):
        while not self.ctrl_c:
            connections = self.robotPublishT.get_num_connections()
            if connections > 0:
                self.robotPublishT.publish(self.cmd)
                rospy.loginfo("T Cmd Published")
                break
            else:
                self.rate.sleep()
    
    def publish_onceS(self):
        while not self.ctrl_c:
            connections = self.robotPublishS.get_num_connections()
            if connections > 0:
                self.robotPublishS.publish(self.cmd)
                rospy.loginfo("S Cmd Published")
                break
            else:
                self.rate.sleep()
    
    def shutdownhook(self):
        # works better than the rospy.is_shutdown()
        self.ctrl_c = True
    
    def stopRobot(self):
        self.cmd = 0
        rospy.loginfo("Stopping!")
        self.publish_onceT()

    def moveRobot(self, moveTime , powerValue):
        self.cmd = powerValue
        rospy.loginfo("Moving!")
        
        timeCount = 0
        freq = 10
        rate = rospy.Rate(freq)
        while timeCount < moveTime: 
            self.robotPublishT.publish(self.cmd)
            self.rate.sleep()
            timeCount = timeCount + 1/freq
        rospy.loginfo("Drive Complete")

    def turnRobot(self, steeringVal):
        self.cmd = steeringVal
        rospy.loginfo("Turning!")
        self.publish_onceS()

    def turnReset(self,steering_float=0):
        self.cmd = steering_float
        rospy.loginfo("Turn Reset")
        self.publish_onceS()

if __name__ == '__main__':
    rospy.init_node('moveRobotTest', anonymous=True)
    moveRobotObject = driveRobot()
    try:
        moveRobotObject.moveRobot()
    except rospy.ROSInterruptException:
        pass