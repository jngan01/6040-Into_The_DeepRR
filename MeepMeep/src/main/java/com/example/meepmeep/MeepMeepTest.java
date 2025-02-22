package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
int i = 0;
        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(17, -62.5, Math.toRadians(270)))


                .strafeTo(new Vector2d(4, -33))
                .waitSeconds(.5)
                //.strafeTo(new Vector2d(10, -40))
                .strafeTo(new Vector2d(34, -36))
                //.waitSeconds(.5)
                .turnTo(Math.toRadians(90))
                //.waitSeconds(.5)
                //Pushing samples
                .strafeTo(new Vector2d(34, -15))
                .strafeTo(new Vector2d(49, -15))
                .strafeTo(new Vector2d(49, -58))
                .strafeTo(new Vector2d(49, -15))
                .strafeTo(new Vector2d(58, -15))
                .strafeTo(new Vector2d(58, -58))

                .strafeTo(new Vector2d(47.5, -52))
                .strafeTo(new Vector2d(47.5, -58))


                .waitSeconds(.5)

                .strafeTo(new Vector2d(17, -50))
                //.waitSeconds(.25)
                .turnTo(Math.toRadians(270))
                //.waitSeconds(.5)
                .strafeTo(new Vector2d(2, -33))
                .waitSeconds(.5)
                //.strafeTo(new Vector2d(10, -40))
                .strafeTo(new Vector2d(60, -50))








                .build());
        myBot.setDimensions(12, 15);
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}