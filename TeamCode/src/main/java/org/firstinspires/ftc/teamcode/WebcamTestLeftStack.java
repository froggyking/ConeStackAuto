package org.firstinspires.ftc.teamcode;

import com.aoe.stem.AOEColorPipeline;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.Base64;

@Autonomous(name = "LeftStackAutoFinal")
public class WebcamTestLeftStack extends LinearOpMode {
    OpenCvWebcam webcam;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor leftSlide;
    private DcMotor rightSlide;
    private Servo _4barServo;
    private CRServo intakeServo;
    String what="";

    /**
     * Describe this function...
     */
    private void encoder_drive(int frontleftpos, int frontrightpos, int backleftpos, int backrightpos, double frontleftspeed, double frontrightspeed, double backleftspeed, double backrightspeed) {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setTargetPosition(frontleftpos);
        backRight.setTargetPosition(backrightpos);
        backLeft.setTargetPosition(backleftpos);
        frontRight.setTargetPosition(frontrightpos);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(frontleftspeed);
        backLeft.setPower(backleftspeed);
        backRight.setPower(backrightspeed);
        frontRight.setPower(frontrightspeed);
        while (opModeIsActive() && frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
        }
    }

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        int parkingSpot;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        AOEColorPipeline aoepipeline = new AOEColorPipeline("2022-2023", webcam,65,220,60,20);
        webcam.setPipeline(aoepipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
            }
        });


        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        leftSlide = hardwareMap.get(DcMotor.class, "leftSlide");
        rightSlide = hardwareMap.get(DcMotor.class, "rightSlide");
        _4barServo = hardwareMap.get(Servo.class, "4barServo");
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");

        // Reverse one of the drive motors.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                parkingSpot = 0;
                what = aoepipeline.ColorValue;

                telemetry.addData("What", what);
                telemetry.update();
                sleep(10);



                leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                if (what == "blue") {
                    parkingSpot = 1;
                } else if (what == "green") {
                    parkingSpot = 2;
                } else if (what == "red") {
                    parkingSpot = 3;

                }
                //camera color code
                intakeServo.setPower(0.8);
                _4barServo.setPosition(0.5);
                sleep(500);
                //might have to update slide raise ticks
                Encoder_SlideRaise(-2600, 2600, 0.7, 0.7);
                encoder_drive(-2341, 2341,2341,-2341,0.2,0.2,0.2,0.2);
                encoder_drive(200,200,200,200,0.2,0.2,0.2,0.2);

                sleep(300);
                //high junction stops
                ///code for webcam to read the cone color
               //ps: not here for now
                //color code ends for now
                _4barServo.setPosition(0.8);
                intakeServo.setPower(-0.8);
                sleep(500);
                _4barServo.setPosition(0.5);
                encoder_drive(-205,-205,-205,-205,0.2,0.2,0.2,0.2);
                Encoder_SlideRaise(2600, -2600, 0.5, 0.5);
               encoder_drive(435,-435,-435,435,0.2,0.2,0.2,0.2);
                Encoder_SlideRaise(-300,300,0.7,0.7);
               encoder_drive(-950,-950,-950,-950,0.2,0.2,0.2,0.2);
              sleep(400);
              intakeServo.setPower(0.8);
              _4barServo.setPosition(0.1);
              sleep(500);
              Encoder_SlideRaise(-1000,1000,0.7,0.7);
              sleep(400);
              _4barServo.setPosition(0.5);
              sleep(600);
              encoder_drive(200,200,200,200,0.2,0.2,0.2,0.2);
                encoder_drive(480,-480,-480,480,0.2,0.2,0.2,0.2);
                encoder_drive(150,150,150,150,0.2,0.2,0.2,0.2);
                sleep(250);
                _4barServo.setPosition(0.8);
                sleep(300);
                intakeServo.setPower(-0.8);
                sleep(200);
                encoder_drive(-150,-150,-150,-150,0.2,0.2,0.2,0.2);
                _4barServo.setPosition(0.5);

                //cycle2
                Encoder_SlideRaise(700,-700,0.7,0.7);
                sleep(300);
                Encoder_SlideRaise(-250,250,0.7,0.7);
                sleep(150);
                encoder_drive(-460,460,460,-460,0.2,0.2,0.2,0.2);
                sleep(250);
                encoder_drive(-250,-250,-250,-250,0.2,0.2,0.2,0.2);
                sleep(200);
                intakeServo.setPower(0.8);
                _4barServo.setPosition(0.1);
                sleep(500);
                Encoder_SlideRaise(-1000,1000,0.7,0.7);
                sleep(400);
                _4barServo.setPosition(0.5);
                sleep(600);
                encoder_drive(150,150,150,150,0.2,0.2,0.2,0.2);
                encoder_drive(480,-480,-480,480,0.2,0.2,0.2,0.2);
                encoder_drive(200,200,200,200,0.2,0.2,0.2,0.2);
                sleep(250);
                _4barServo.setPosition(0.8);
                sleep(300);
                intakeServo.setPower(-0.8);
                sleep(200);
                encoder_drive(-150,-150,-150,-150,0.2,0.2,0.2,0.2);
                sleep(300);
                _4barServo.setPosition(0.5);
                sleep(150);

                //cycle3
                Encoder_SlideRaise(700,-700,0.7,0.7);
                sleep(300);
                Encoder_SlideRaise(-175,175,0.7,0.7);
                sleep(150);
                encoder_drive(-460,460,460,-460,0.2,0.2,0.2,0.2);
                sleep(250);
                encoder_drive(-250,-250,-250,-250,0.2,0.2,0.2,0.2);
                sleep(200);
                intakeServo.setPower(0.8);
                _4barServo.setPosition(0.1);
                sleep(500);
                Encoder_SlideRaise(-1000,1000,0.7,0.7);
                sleep(400);
                _4barServo.setPosition(0.5);
                sleep(600);
                encoder_drive(150,150,150,150,0.2,0.2,0.2,0.2);
                encoder_drive(480,-480,-480,480,0.2,0.2,0.2,0.2);
                encoder_drive(200,200,200,200,0.2,0.2,0.2,0.2);
                sleep(250);
                _4barServo.setPosition(0.8);
                sleep(300);
                intakeServo.setPower(-0.8);
                sleep(200);
                encoder_drive(-150,-150,-150,-150,0.2,0.2,0.2,0.2);
                sleep(300);
                _4barServo.setPosition(0.5);
                sleep(150);

                //cycle4
                Encoder_SlideRaise(700,-700,0.7,0.7);
                sleep(300);
                Encoder_SlideRaise(-150,150,0.7,0.7);
                sleep(150);
                encoder_drive(-460,460,460,-460,0.2,0.2,0.2,0.2);
                sleep(250);
                encoder_drive(-250,-250,-250,-250,0.2,0.2,0.2,0.2);
                sleep(200);
                intakeServo.setPower(0.8);
                _4barServo.setPosition(0.1);
                sleep(500);
                Encoder_SlideRaise(-1000,1000,0.7,0.7);
                sleep(400);
                _4barServo.setPosition(0.5);
                sleep(600);
                encoder_drive(150,150,150,150,0.2,0.2,0.2,0.2);
                encoder_drive(480,-480,-480,480,0.2,0.2,0.2,0.2);
                encoder_drive(200,200,200,200,0.2,0.2,0.2,0.2);
                sleep(250);
                _4barServo.setPosition(0.8);
                sleep(300);
                intakeServo.setPower(-0.8);
                sleep(200);
                encoder_drive(-150,-150,-150,-150,0.2,0.2,0.2,0.2);
                sleep(300);
                _4barServo.setPosition(0.5);
                sleep(150);
                Encoder_SlideRaise(700,-700,0.7,0.7);
                sleep(300);
                Encoder_SlideRaise(-125,125,0.7,0.7);
                sleep(150);
                encoder_drive(-460,460,460,-460,0.2,0.2,0.2,0.2);
                sleep(250);
                encoder_drive(-250,-250,-250,-250,0.2,0.2,0.2,0.2);
                sleep(200);
                intakeServo.setPower(0.8);
                _4barServo.setPosition(0.1);
                sleep(500);
                Encoder_SlideRaise(-1000,1000,0.7,0.7);
                sleep(400);
                _4barServo.setPosition(0.5);
                sleep(600);
                encoder_drive(150,150,150,150,0.2,0.2,0.2,0.2);
                encoder_drive(480,-480,-480,480,0.2,0.2,0.2,0.2);
                encoder_drive(200,200,200,200,0.2,0.2,0.2,0.2);
                sleep(250);
                _4barServo.setPosition(0.8);
                sleep(300);
                intakeServo.setPower(-0.8);
                sleep(200);
                encoder_drive(-200,-200,-200,-200,0.2,0.2,0.2,0.2);
                sleep(300);
                _4barServo.setPosition(0.5);
                sleep(150);
                //cycle5
                Encoder_SlideRaise(700,-700,0.7,0.7);
                sleep(300);
                Encoder_SlideRaise(-65,65,0.7,0.7);
                sleep(150);
                encoder_drive(-460,460,460,-460,0.2,0.2,0.2,0.2);
                sleep(250);
                encoder_drive(-250,-250,-250,-250,0.2,0.2,0.2,0.2);
                sleep(200);
                intakeServo.setPower(0.8);
                _4barServo.setPosition(0.1);
                sleep(500);
                Encoder_SlideRaise(-1000,1000,0.7,0.7);
                sleep(400);
                _4barServo.setPosition(0.5);
                sleep(600);
                encoder_drive(150,150,150,150,0.2,0.2,0.2,0.2);
                encoder_drive(480,-480,-480,480,0.2,0.2,0.2,0.2);
                encoder_drive(200,200,200,200,0.2,0.2,0.2,0.2);
                sleep(250);
                _4barServo.setPosition(0.8);
                sleep(300);
                intakeServo.setPower(-0.8);
                sleep(200);
                encoder_drive(-200,-200,-200,-200,0.3,0.3,0.3,0.3);
                sleep(300);


                if (parkingSpot == 1) {
                    encoder_drive(400, -400, -400, 400, 0.3, 0.3, 0.3, 0.3);
                    sleep(150);
                    break;

                } else if (parkingSpot == 2) {
                    encoder_drive(420, -420, -420, 420, 0.3, 0.3, 0.3, 0.3);
                    encoder_drive(550, 550, 550, 550, 0.3, 0.3, 0.3, 0.3);
                    sleep(150);
                    break;


                } else if (parkingSpot == 3) {
                    encoder_drive(367, -367, -367, 367, 0.3, 0.3, 0.3, 0.3);
                    encoder_drive(1400, 1400, 1400, 1400, 0.3, 0.3, 0.3, 0.3);
                    break;


                }






















//might have to add while left slide is busy and right slide is busy but idk (scratch this idea it works: present day adithya)

                
                break;


                }
                leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftSlide.setPower(0.4);
                rightSlide.setPower(-0.4);
                sleep(1000);
                leftSlide.setPower(0);
                rightSlide.setPower(0);
                requestOpModeStop();
            }
        }

    /**
     * Describe this function...
     */
    private void Encoder_SlideRaise(int LeftslidePos, int RightslidePos, double LeftslideSpeed, double RightslideSpeed) {
        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftSlide.setTargetPosition(LeftslidePos);
        rightSlide.setTargetPosition(RightslidePos);
        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftSlide.setPower(LeftslideSpeed);
        rightSlide.setPower(RightslideSpeed);
    }
}









