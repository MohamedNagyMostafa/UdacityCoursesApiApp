package com.example.mohamednagy.udacity.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.mohamednagy.udacity.R;

/**
 * Created by mohamednagy on 12/5/2016.
 */
public class AppAnimation {

    private static boolean syllabusPanelIsOpened = false;
    private static boolean summaryPanelIsOpened  = false;
    private static boolean requirementsPanelIsOpened  = false;
    private static boolean expectedLearningPanelIsOpened  = false;
    private static final int CASES = 2;

    public static void setPanelAnimation(
            final TextView syllabusTextView, final TextView syllabusPanelTextView,
            TextView summaryTextView, TextView summaryPanelTextView,
            TextView requirementsTextView, TextView requirementsPanelTextView,
            TextView expectedLearningTextView, TextView expectedLearningPanelTextView,
            Context context){

        setOnClickListeners(syllabusTextView, syllabusPanelTextView, context);
        setOnClickListeners(summaryTextView, summaryPanelTextView, context);
        setOnClickListeners(requirementsTextView, requirementsPanelTextView, context);
        setOnClickListeners(expectedLearningTextView, expectedLearningPanelTextView, context);
    }

    private static int booleanToInt(boolean booleanVariable){
        return (booleanVariable)?1:0;
    }

    private static boolean intToBoolean(int integerVariable){
        return (integerVariable == 1);
    }
    // Display or hidden the panel's text depend upon it's mode
    private static void setOnClickListeners(
            final TextView hiddenTextView, final TextView panelTextView, final Context context){

        panelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean currentMode = setAndGetCurrentMode(panelTextView,context);

                if(currentMode){

                    hiddenTextView.animate().alpha(0.8f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            hiddenTextView.setVisibility(View.VISIBLE);
                            panelTextView.setBackgroundColor(
                                    context.getResources().getColor(R.color.colorTheme));
                            panelTextView.setTextColor(
                                    context.getResources().getColor(R.color.colorWhite));
                        }
                    });

                }else{

                    hiddenTextView.animate().alpha(0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            hiddenTextView.setVisibility(View.GONE);
                            panelTextView.setBackgroundColor(
                                    context.getResources().getColor(R.color.colorWhite));
                            panelTextView.setTextColor(
                                    context.getResources().getColor(R.color.colorBlack));
                        }
                    });
                }
            }
        });
    }
    // Change value of open variable between true/false
    // and return current value
    private static boolean setAndGetCurrentMode(TextView panelTextView, Context context){

        final String summaryText = context.getString(R.string.summary_panel);
        final String syllabusText = context.getString(R.string.syllabus_panel);
        final String requiredText = context.getString(R.string.requirements_panel);
        final String expectedLearningText = context.getString(R.string.expected_learning_panel);

        if(panelTextView.getText().toString().equals(summaryText)) {
            summaryPanelIsOpened = intToBoolean((booleanToInt(summaryPanelIsOpened) + 1) % CASES);
            return summaryPanelIsOpened;
        }else if(panelTextView.getText().toString().equals(syllabusText)) {
            syllabusPanelIsOpened = intToBoolean((booleanToInt(syllabusPanelIsOpened) + 1) % CASES);
            return syllabusPanelIsOpened;
        }else if(panelTextView.getText().toString().equals(requiredText)) {
            requirementsPanelIsOpened = intToBoolean((booleanToInt(requirementsPanelIsOpened) + 1) % CASES);
            return requirementsPanelIsOpened;
        }else{
            expectedLearningPanelIsOpened = intToBoolean((booleanToInt(expectedLearningPanelIsOpened) + 1) % CASES);
            return expectedLearningPanelIsOpened;
        }

    }

}
