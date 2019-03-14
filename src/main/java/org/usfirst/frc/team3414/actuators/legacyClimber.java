
// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package org.usfirst.frc.team3414.actuators;

// import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// import com.ctre.phoenix.motorcontrol.NeutralMode;
// import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import com.ctre.phoenix.motorcontrol.DemandType;

// import org.usfirst.frc.team3414.config.Config;
// import org.usfirst.frc.team3414.diagnostic.LED;
// import org.usfirst.frc.team3414.teleop.Teleop;

// import edu.wpi.first.wpilibj.DigitalInput;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// /**
//  * Add your docs here.
//  */
// public class Climber {
//     double masterthrottle = 1;
//     double frontThrottle = 1;
//     private int escape = Config.ESCAPE_BUTTON;
//     DigitalInput frontsensor = new DigitalInput(3);
//     DigitalInput rearsensor = new DigitalInput(2);
//     private static Climber instance;

//     public static Climber getInstance() {
//         if (instance == null) {
//             instance = new Climber();
//         }

//         return instance;

//     }

//     TalonSRX frontMotor;
//     TalonSRX rearMotor;
//     TalonSRX middleMotor;

//     public void init() {
//         frontMotor = new TalonSRX(Config.CLIMBER_FRONT);
//         rearMotor = new TalonSRX(Config.CLIMBER_REAR);
//         middleMotor = new TalonSRX(Config.CLIMBER_MOTOR_THREE);
//         initEncoders();
//         resetEncoders();

//     }

//     public void newTopClimb() {
//         /*
//          * ` Stage 1 - Climb Evenly Stage 2 - Move Forward Stage 3 - Raise Front Stage 4
//          * - Move Whole Bot Forward Stage 5 - Raise Rear Stage 6 - Drive Forward on
//          * platform
//          */
//         int target = 16000;
//         int offset = 0  ;
//         int margin = 13500;
//         int stage = 0;
//         long start = 0;
//         rear(rearMotor);
//         front(frontMotor);
//         setFront(target);
//         stage = 1;
//         while ((!Teleop.getInstance().getRightJoystick().getRawButton(escape))) {
//             if (stage == 1) {
//                 System.out.println("Not at target height (IR)");
//                 setFront(target);
//                 setRear(getFrontEncoder() + offset);

//                 if (getRearEncoder() >= margin) {
//                     stage = 2;
//                 }
//             }
//             if (stage == 2) {
//                 setBottomSpeed(.3414);
//                 setFront(target);
//                 //setRear(getFrontEncoder() + offset);
//                 setRear(target + offset);
//                 if (atFrontDistance()) {
//                     setBottomSpeed(0);
//                     stage = 3;

//                 }

//             }
//             if (stage == 3) {
//                 newRetractFront();
//                 setRear(target);
//                 if (getFrontEncoder() <= 100) {
//                     stage = 4;

//                 }
//             }
//             if (stage == 4) {
//                 setBottomSpeed(.3414);
//                 setDriveTrain(.10);
//                 setRear(target);
//                 if (atRearDistance()) {
//                     stage = 5;
//                     setDriveTrain(0);
//                     setBottomSpeed(0);
//                 }
//             }
//             if (stage == 5) {
//                 newRetractRear();
//                 if (getRearEncoder() >= 200) {
//                     stage = 6;
//                 }
//             }
//             if (stage == 6) {
//                 setDriveTrain(.1);
//                 Timer.delay(2);
//             } else {

//             }
//         }

//     }

//     public void setDriveTrain(double speed) {
//         DriveTrain.getInstance().set(-speed, -speed);
//     }

//     public void setRear(int target) {
//         rearMotor.set(ControlMode.Position, target, DemandType.ArbitraryFeedForward, 0);
//     }

//     public void setFront(int target) {
//         frontMotor.set(ControlMode.MotionMagic, target, DemandType.ArbitraryFeedForward, 1196);
//     }

//     public void percentOutputClimber() {
//         int target = 15000;
//         frontMotor.set(ControlMode.PercentOutput, .5);
//         rearMotor.set(ControlMode.PercentOutput, .5);

//         while ((getFrontEncoder() < target) && (!Teleop.getInstance().getRightJoystick().getRawButton(escape))) {
//             if (getRearEncoder() - getFrontEncoder() > 200) {
//                 // Slow Rear
//                 rearMotor.set(ControlMode.PercentOutput, rearMotor.getMotorOutputPercent() - .01);
//             } else if (getFrontEncoder() - getRearEncoder() > 200) {
//                 // Speed Up Rear
//                 rearMotor.set(ControlMode.PercentOutput, rearMotor.getMotorOutputPercent() + .01);
//             }
//             // frontMotor.set(ControlMode.Position, 13000);
//             // rearMotor.set(ControlMode.Position, getRearEncoder() + 500);
//         }
//         while (!Teleop.getInstance().getRightJoystick().getRawButton(escape)) {
//             frontMotor.set(ControlMode.Position, target);
//             rearMotor.set(ControlMode.Position, target);
//         }
//     }

//     public void motionmagicclimber() {
//         rear(rearMotor);
//         front(frontMotor);
//         LED.setRed();
//         triggered = false;
//         rear(rearMotor);
//         front(frontMotor);
//         // frontMotor.configMotionAcceleration(4784 / 2);
//         frontMotor.set(ControlMode.MotionMagic, 16000, DemandType.ArbitraryFeedForward, 1196);
//         long startTime = System.currentTimeMillis();
//         int offset = Config.TOP_REAR_CLIMBER_OFFSET;
//         while ((!Teleop.getInstance().getRightJoystick().getRawButton(escape))) {
//             diagnostic();
//             SmartDashboard.putNumber(" BIG Middle Encoder", getMiddleEncoder());
//             // if(System.currentTimeMillis() % 100 ==0){
//             // System.out.println("frontMotor\t" +
//             // frontMotor.getSensorCollection().getQuadraturePosition());
//             // }
//             System.out.println(getFrontEncoder());
//             if (getFrontEncoder() > 15000) {
//                 middleMotor.setNeutralMode(NeutralMode.Coast);
//                 System.out.println("HEY LOOK AT ME " + getFrontEncoder());

//                 LED.setYellow();

//                 moveBottomForward(1.5, 16000);
//                 middleMotor.setNeutralMode(NeutralMode.Brake);
//                 // Hold the rear to current position
//                 // rearMotor.set(ControlMode.MotionMagic, 16000,
//                 // DemandType.ArbittraryFeedForward, 0);
//                 retractFront(16000);
//                 // moveForward(2.5, .2 );
//                 moveForward(0, .25);
//                 moveBottomForward(1, 16000);
//                 DriveTrain.getInstance().set(-.2, -.2);
//                 retractRear(1.8, 0);
//                 moveForward(.5, .25);
//                 LED.setGreen();
//                 break;

//             } // Once the front gets so high, turn on the middle (bottom, stationary motor)
//             else {
//                 rearMotor.set(ControlMode.Position, getFrontEncoder() + offset, DemandType.ArbitraryFeedForward, 0);
//             }

//             // System.out.println("frontMotor\t" +
//             // frontMotor.getSensorCollection().getQuadraturePosition());
//             // Timer.delay(20);
//             System.out.println("frontMotor\t" + getFrontEncoder());

//         }
//         eStop(); // TODO, how do we want to have drivetrain work, sensors or manual control?
//     }

//     public void magicClimbMid() {

//         LED.setRed();

//         triggered = false;
//         rear(rearMotor);
//         front(frontMotor);
//         // frontMotor.configMotionAcceleration(4784 / 2);
//         int target = 8500;
//         frontMotor.set(ControlMode.MotionMagic, target, DemandType.ArbitraryFeedForward, 1196);
//         long startTime = System.currentTimeMillis();
//         int offset = Config.TOP_REAR_CLIMBER_OFFSET;
//         int margin = 4500;
//         while ((!Teleop.getInstance().getRightJoystick().getRawButton(escape))) {
//             diagnostic();
//             SmartDashboard.putNumber(" BIG Middle Encoder", getMiddleEncoder());
//             // if(System.currentTimeMillis() % 100 ==0){
//             // System.out.println("frontMotor\t" +
//             // frontMotor.getSensorCollection().getQuadraturePosition());
//             // }
//             System.out.println(getFrontEncoder());

//             if (getFrontEncoder() > margin) {
//                 System.out.println("HEY LOOK AT ME " + getFrontEncoder());

//                 LED.setYellow();

//                 moveBottomForward(2, margin);
//                 // Hold the rear to current position
//                 // rearMotor.set(ControlMode.MotionMagic, 16000,
//                 // DemandType.ArbitraryFeedForward, 0);
//                 retractFront(margin);
//                 // moveForward(2.5, .2 );
//                 moveForward(0, .25);
//                 moveBottomForward(1.2, margin);
//                 DriveTrain.getInstance().set(0, 0);
//                 retractRear(2, 0);
//                 moveForward(.5, .25);
//                 LED.setGreen();
//                 break;

//             } // Once the front gets so high, turn on the middle (bottom, stationary motor)
//             else {
//                 rearMotor.set(ControlMode.Position, getFrontEncoder() + offset, DemandType.ArbitraryFeedForward, 0);
//             }

//             // System.out.println("frontMotor\t" +
//             // frontMotor.getSensorCollection().getQuadraturePosition());
//             // Timer.delay(20);
//             System.out.println("frontMotor\t" + getFrontEncoder());

//         }
//         eStop(); // TODO, how do we want to have drivetrain work, sensors or manual control?
//     }

//     public void motionmagicclimberMidplatform() {
//         System.out.println("MID PLATFORM");
//         LED.setRed();
//         triggered = false;
//         rear(rearMotor);
//         front(frontMotor);
//         frontMotor.set(ControlMode.MotionMagic, 6000, DemandType.ArbitraryFeedForward, 1196);
//         long startTime = System.currentTimeMillis();
//         int offset = Config.MID_FRONT_CLIMBER_OFFSET;
//         while ((!Teleop.getInstance().getRightJoystick().getRawButton(escape))) {
//             System.out.println("REAR " + getRearEncoder());
//             System.out.println("FRONT " + getFrontEncoder()); // if(System.currentTimeMillis() % 100 ==0){
//             // System.out.println("frontMotor\t" +
//             // frontMotor.getSensorCollection().getQuadraturePosition());
//             // }

//             if (getFrontEncoder() > 5200) {
//                 LED.setYellow();
//                 moveBottomForward(2.5, 5800);
//                 retractFront(5800);
//                 // moveForward(2, .15 );

//                 moveForward(.65, .30);
//                 // retractRear(.4,0);
//                 retractRear(.45, -400);
//                 moveForward(.5, .5);
//                 LED.setGreen();
//                 break;

//             } // Once the front gets so high, turn on the middle (bottom, stationary motor)
//             else {
//                 rearMotor.set(ControlMode.Position, getFrontEncoder() + offset, DemandType.ArbitraryFeedForward, 0);
//             }

//             // System.out.println("frontMotor\t" +
//             // frontMotor.getSensorCollection().getQuadraturePosition());
//             // Timer.delay(20);
//             System.out.println("frontMotor\t" + getFrontEncoder());

//         }
//         eStop(); // TODO, how do we want to have drivetrain work, sensors or manual control?
//     }

//     public void rear(TalonSRX climber) {
//         climber.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
//         climber.getSensorCollection().setQuadraturePosition(0, 10);
//         climber.configMotionAcceleration(4784);
//         climber.configMotionCruiseVelocity(1196);// 1196
//         climber.configPeakOutputForward(1.0);
//         climber.configPeakOutputReverse(-1.0);
//         climber.config_kP(0, 12);// (0, 0.175); or 7
//         climber.config_kI(0,0); // (0,0)
//         climber.config_kD(0, 0); // 1.75
//         climber.config_kF(0, 0.427799073);
//         climber.config_IntegralZone(0, 0);
//         climber.configClosedLoopPeakOutput(0, 1.0);
//         climber.selectProfileSlot(0, 0);

//     }

//     public void front(TalonSRX climber) {
//         climber.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
//         climber.getSensorCollection().setQuadraturePosition(0, 10);
//         climber.configMotionAcceleration(4784);
//         climber.configMotionCruiseVelocity(1196); // 1196
//         climber.configPeakOutputForward(1.0);
//         climber.configPeakOutputReverse(-1.0);
//         climber.config_kP(0, 3); // Originally 1
//         climber.config_kI(0, 0);
//         climber.config_kD(0, 0);
//         climber.config_kF(0, 0);
//         climber.config_IntegralZone(0, 0);
//         climber.configClosedLoopPeakOutput(0, 1.0);
//         climber.selectProfileSlot(0, 0);

//     }

//     boolean triggered = false;

//     public void moveBottomForward(double seconds, int reartarget) {
//         long starttime = System.currentTimeMillis();
//         seconds *= 1000;
//         while ((System.currentTimeMillis() < starttime + seconds)
//                 && (!Teleop.getInstance().getRightJoystick().getRawButton(escape))) {
//             // Timer.delay(time);
//             middleMotor.set(ControlMode.PercentOutput, .25);
//             rearMotor.set(ControlMode.Position, reartarget); // ,16000
//         }
//         middleMotor.set(ControlMode.PercentOutput, 0);
//     }

//     public void moveForward(double time, double speed) {
//         DriveTrain.getInstance().set(-speed, -speed);
//         Timer.delay(time);
//     }

//     public void unlockDriveTrain() {
//         DriveTrain.getInstance().setBlock(false); // Release drivetrain block
//     }

//     public void newRetractFront() {

//         frontMotor.set(ControlMode.Position, 10, DemandType.ArbitraryFeedForward, 0);
//         // rearMotor.set(ControlMode.MotionMagic, 12000,
//         // DemandType.ArbitraryFeedForward, 0);
//     }

//     public void retractFront(int target) {

//         frontMotor.set(ControlMode.Position, 10, DemandType.ArbitraryFeedForward, 0);
//         while (getFrontEncoder() > 200 && !Teleop.getInstance().getRightJoystick().getRawButton(escape)) {
//             rearMotor.set(ControlMode.Position, target);
//         }
//         // rearMotor.set(ControlMode.MotionMagic, 12000,
//         // DemandType.ArbitraryFeedForward, 0);
//     }

//     /*
//      * public void retractFront() {
//      * 
//      * frontMotor.set(ControlMode.MotionMagic, 10, DemandType.ArbitraryFeedForward,
//      * 0);
//      * 
//      * // rearMotor.set(ControlMode.MotionMagic, 12000, //
//      * DemandType.ArbitraryFeedForward, 0); }
//      */
//     public void retractRear(double time, int offset) {
//         System.out.println("RIGHT BEFORE REAR " + getRearEncoder());
//         while (getRearEncoder() > 1500 && !Teleop.getInstance().getRightJoystick().getRawButton(escape)) {
//             System.out.println("DURING REAR RETRACT: " + getRearEncoder());
//             rearMotor.set(ControlMode.PercentOutput, -1);
//         }
//         rearMotor.set(ControlMode.PercentOutput, 0);
//     }

//     public void newRetractRear() {
//         rearMotor.set(ControlMode.Position, 0);
//     }

//     public void eStop() {
//         frontMotor.set(ControlMode.PercentOutput, 0);
//         rearMotor.set(ControlMode.PercentOutput, 0);
//         middleMotor.set(ControlMode.PercentOutput, 0);

//     }

//     public void initEncoders() {
//         frontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
//         rearMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
//         middleMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
//     }

//     public void resetEncoders() {

//         frontMotor.getSensorCollection().setQuadraturePosition(0, 10);
//         rearMotor.getSensorCollection().setQuadraturePosition(0, 10);
//         rearMotor.getSensorCollection().setQuadraturePosition(0, 10);
//     }

//     public void setBottomSpeed(double demand) {
//         middleMotor.set(ControlMode.PercentOutput, demand);
//     }

//     public int getFrontEncoder() {
//         return frontMotor.getSensorCollection().getQuadraturePosition();
//     }

//     public int getRearEncoder() {
//         return rearMotor.getSensorCollection().getQuadraturePosition();
//     }

//     public double getMiddleEncoder() {
//         return middleMotor.getSensorCollection().getQuadraturePosition();
//     }

//     public double getDiff() {
//         return frontMotor.getSensorCollection().getQuadraturePosition()
//                 - rearMotor.getSensorCollection().getQuadraturePosition();
//         // If this is positive, it means the front is ahead by x ticks. Negative rear is
//         // ahead by x ticks
//     }

//     public void diagnostic() {
//         SmartDashboard.putNumber("Climber Front %:", frontMotor.getMotorOutputPercent());
//         SmartDashboard.putNumber("Climber Middle %:", middleMotor.getMotorOutputPercent());
//         SmartDashboard.putNumber("Climber Rear %:", rearMotor.getMotorOutputPercent());
//         SmartDashboard.putNumber("Climber Front Encoder", getFrontEncoder());
//         SmartDashboard.putNumber("Climber Rear Encoder", getRearEncoder());
//         SmartDashboard.putNumber("Climber Middle Encoder", getMiddleEncoder());
//         SmartDashboard.putNumber("Climber Encoder Difference", getDiff());
//     }

//     public boolean atFrontDistance() {
//         return !frontsensor.get();
//     }

//     public boolean atRearDistance() {
//         return !rearsensor.get();
//     }
// }
