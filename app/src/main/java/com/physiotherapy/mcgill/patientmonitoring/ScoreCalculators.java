package com.physiotherapy.mcgill.patientmonitoring;

import android.database.Cursor;


public class ScoreCalculators {

    public static float[] barthelScore(Cursor cursor){
        String feedingString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_FEEDING));
        String dressingString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_DRESSING));
        String sitStandString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_SITSTAND));
        String walkingString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_WALKING));
        String bladderString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_BLADDER));
        String liftsAffectedString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_LIFTSAFFECTED));
        String liftsUnaffectedString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_LIFTSUNAFFECTED));


        int feedingInt = 0;
        int dressingInt = 0;
        int sitStandInt = 0;
        int walkingInt = 0;
        int bladderInt = 0;
        int liftsAffectedInt = 0;
        int liftsUnaffectedInt = 0;
        boolean scoreToggle = true;

        if (feedingString==null) return new float[]{-1};
        switch (feedingString) {
            case "None":
                feedingInt = 0;
                break;
            case "Partial":
                feedingInt = 1;
                break;
            case "Full":
                feedingInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (dressingString==null) return new float[]{-1};
        switch (dressingString) {
            case "None":
                dressingInt = 0;
                break;
            case "Partial":
                dressingInt = 1;
                break;
            case "Full":
                dressingInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (sitStandString==null) return new float[]{-1};
        switch (sitStandString) {
            case "None":
                sitStandInt = 0;
                break;
            case "Partial":
                sitStandInt = 1;
                break;
            case "Full":
                sitStandInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (walkingString==null) return new float[]{-1};
        switch (walkingString) {
            case "None":
                walkingInt = 0;
                break;
            case "Partial":
                walkingInt = 1;
                break;
            case "Full":
                walkingInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (bladderString==null) return new float[]{-1};
        switch (bladderString) {
            case "Foley":
                bladderInt = 0;
                break;
            case "Diaper":
                bladderInt = 0;
                break;
            case "Bedpan":
                bladderInt = 1;
                break;
            case "Toilet":
                bladderInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (liftsAffectedString==null) return new float[]{-1};
        switch (liftsAffectedString) {
            case "None":
                liftsAffectedInt = 0;
                break;
            case "Partial":
                liftsAffectedInt = 1;
                break;
            case "Full":
                liftsAffectedInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (liftsUnaffectedString==null) return new float[]{-1};
        switch (liftsUnaffectedString) {
            case "None":
                liftsUnaffectedInt = 0;
                break;
            case "Partial":
                liftsUnaffectedInt = 1;
                break;
            case "Full":
                liftsUnaffectedInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (scoreToggle){
            int barthel1 = 5*feedingInt;
            int barthel2 = Math.max(0,5*feedingInt-5);
            int barthel3 = Math.max(0,5*feedingInt-5);
            int barthel4 = 5*dressingInt;
            int barthel5 = Math.min(sitStandInt,walkingInt)*5;
            int barthel6 = 5*bladderInt;
            int barthel7 = 5*bladderInt;
            int barthel8 = 5*sitStandInt;
            int barthel9 = 5*walkingInt;
            int barthel10 = Math.min(liftsAffectedInt,liftsUnaffectedInt)*5;
            float score = barthel1 + barthel2 + barthel3 + barthel4 + barthel5 + barthel6 + barthel7 + barthel8 + barthel9 + barthel10;
            return new float[] {score, barthel1, barthel2, barthel3, barthel4, barthel5, barthel6, barthel7, barthel8, barthel9, barthel10};


        } else{
            float score = -1;
            return new float[] {score};
        }


    }


    public static float[] bergScore(Cursor cursor){
        //Berg score
        String liftsAffectedString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_LIFTSAFFECTED));
        String liftsUnaffectedString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_LIFTSUNAFFECTED));
        String sitStandString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_SITSTAND));
        String standString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_STAND));
        String sittingString = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_SITTING));


        int liftsAffectedInt = 0;
        int liftsUnaffectedInt = 0;
        int sitStandInt = 0;
        int standInt = 0;
        int sittingInt = 0;
        boolean scoreToggle = true;

        if (liftsAffectedString==null) return new float[]{-1};
        switch (liftsAffectedString) {
            case "None":
                liftsAffectedInt = 0;
                break;
            case "Partial":
                liftsAffectedInt = 1;
                break;
            case "Full":
                liftsAffectedInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (liftsUnaffectedString==null) return new float[]{-1};
        switch (liftsUnaffectedString) {
            case "None":
                liftsUnaffectedInt = 0;
                break;
            case "Partial":
                liftsUnaffectedInt = 1;
                break;
            case "Full":
                liftsUnaffectedInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (sitStandString==null) return new float[]{-1};
        switch (sitStandString) {
            case "None":
                sitStandInt = 0;
                break;
            case "Partial":
                sitStandInt = 1;
                break;
            case "Full":
                sitStandInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (standString==null) return new float[]{-1};
        switch (standString) {
            case "None":
                standInt = 0;
                break;
            case "Partial":
                standInt = 1;
                break;
            case "Full":
                standInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (sittingString==null) return new float[]{-1};
        switch (sittingString) {
            case "None":
                sittingInt = 0;
                break;
            case "Partial":
                sittingInt = 1;
                break;
            case "Full":
                sittingInt = 2;
                break;
            default:
                scoreToggle = false;
                break;
        }

        float score;
        if (scoreToggle){
            if(liftsAffectedInt*liftsUnaffectedInt==4){
                score = 51;
            } else if (liftsAffectedInt*liftsUnaffectedInt==2){
                score = 44;
            } else if (sitStandInt==2){
                score = 20;
            } else if (sitStandInt==1){
                score = 18;
            } else if (standInt==2){
                score = 8;
            } else if (standInt==1){
                score = 6;
            } else if (sittingInt==2){
                score = 4;
            } else if (sittingInt==1){
                score = 2;
            } else {
                score = 0;
            }

            return new float[]{score};

        } else {
            score = -1;
            return new float[]{score};
        }
    }


    public static float[] cnsScore(Cursor cursor){
        boolean scoreToggle = true;

        String consciousness = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_CONSCIOUSNESS));
        String orientation = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_ORIENTATION));
        String speech = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_SPEECH));
        String face1 = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_FACE1));
        String upperLimbProximal = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_UPPER_LIMB_PROXIMAL));
        String upperLimbDistal = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_UPPER_LIMB_DISTAL));
        String lowerLimbProximal = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_LOWER_LIMB_PROXIMAL));
        String lowerLimbDistal = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_LOWER_LIMB_DISTAL));
        String upperLimbs = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_UPPER_LIMBS));
        String lowerLimbs = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_LOWER_LIMBS));
        String face2 = cursor.getString(cursor.getColumnIndex(DBAdapter.KEY_CNS_FACE2));


        int consciousnessInt = 0;
        int orientationInt = 0;
        int speechInt = 0;
        int face1Int = 0;
        int upperLimbProximalInt = 0;
        int upperLimbDistalInt = 0;
        int lowerLimbProximalInt = 0;
        int lowerLimbDistalInt = 0;
        int upperLimbsInt = 0;
        int lowerLimbsInt = 0;
        int face2Int = 0;

        //Doubling all values to store as integers

        if (consciousness==null) return new float[]{-1};
        switch (consciousness) {
            case "Alert":
                consciousnessInt = 6;
                break;
            case "Drowsy":
                consciousnessInt = 3;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (orientation==null) return new float[]{-1};
        switch (orientation) {
            case "Oriented":
                orientationInt = 2;
                break;
            case "Disoriented":
                orientationInt = 0;
                break;
            default:
                scoreToggle = false;
                break;
        }

        if (speech==null) return new float[]{-1};
        switch (speech) {
            case "Receptive deficit":
                speechInt = 0;

                if (face2==null) return new float[]{-1};
                switch (face2) {
                    case "Symmetrical":
                        face2Int = 1;
                        break;
                    case "Asymmetrical":
                        face2Int = 0;
                        break;
                    default:
                        scoreToggle = false;
                        break;
                }


                if (upperLimbs==null) return new float[]{-1};
                switch (upperLimbs) {
                    case "Equal":
                        upperLimbsInt = 3;
                        break;
                    case "Unequal":
                        upperLimbsInt = 0;
                        break;
                    default:
                        scoreToggle = false;
                        break;
                }


                if (lowerLimbs==null) return new float[]{-1};
                switch (lowerLimbs) {
                    case "Equal":
                        lowerLimbsInt = 3;
                        break;
                    case "Unequal":
                        lowerLimbsInt = 0;
                        break;
                    default:
                        scoreToggle = false;
                        break;
                }

                if (scoreToggle) {
                    float score = (consciousnessInt + orientationInt + speechInt + face2Int + upperLimbsInt + lowerLimbsInt) / 2;
                    return new float[]{score};
                } else {
                    float score = -1;
                    return new float[]{score};
                }

            case "Expressive deficit":
            case "Normal":
                if (speech.equals("Expressive deficit")) {
                    speechInt = 1;
                } else {
                    speechInt = 2;
                }

                if (face1==null) return new float[]{-1};
                switch (face1) {
                    case "No weakness":
                        face1Int = 1;
                        break;
                    case "Weakness":
                        face1Int = 0;
                        break;
                    default:
                        scoreToggle = false;
                        break;
                }

                if (upperLimbProximal==null) return new float[]{-1};
                switch (upperLimbProximal) {
                    case "No weakness":
                        upperLimbProximalInt = 3;
                        break;
                    case "Mild weakness":
                        upperLimbProximalInt = 2;
                        break;
                    case "Significant weakness":
                        upperLimbProximalInt = 1;
                        break;
                    case "Total weakness":
                        upperLimbProximalInt = 0;
                        break;
                    default:
                        scoreToggle = false;
                        break;
                }

                if (upperLimbDistal==null) return new float[]{-1};
                switch (upperLimbDistal) {
                    case "No weakness":
                        upperLimbDistalInt = 3;
                        break;
                    case "Mild weakness":
                        upperLimbDistalInt = 2;
                        break;
                    case "Significant weakness":
                        upperLimbDistalInt = 1;
                        break;
                    case "Total weakness":
                        upperLimbDistalInt = 0;
                        break;
                    default:
                        scoreToggle = false;
                        break;
                }

                if (lowerLimbProximal==null) return new float[]{-1};
                switch (lowerLimbProximal) {
                    case "No weakness":
                        lowerLimbProximalInt = 3;
                        break;
                    case "Mild weakness":
                        lowerLimbProximalInt = 2;
                        break;
                    case "Significant weakness":
                        lowerLimbProximalInt = 1;
                        break;
                    case "Total weakness":
                        lowerLimbProximalInt = 0;
                        break;
                    default:
                        scoreToggle = false;
                        break;
                }

                if (lowerLimbDistal==null) return new float[]{-1};
                switch (lowerLimbDistal) {
                    case "No weakness":
                        lowerLimbDistalInt = 3;
                        break;
                    case "Mild weakness":
                        lowerLimbDistalInt = 2;
                        break;
                    case "Significant weakness":
                        lowerLimbDistalInt = 1;
                        break;
                    case "Total weakness":
                        lowerLimbDistalInt = 0;
                        break;
                    default:
                        scoreToggle = false;
                        break;
                }


                if (scoreToggle) {
                    float score = (consciousnessInt + orientationInt + speechInt + face1Int + upperLimbProximalInt + upperLimbDistalInt + lowerLimbProximalInt + lowerLimbDistalInt) / 2;
                    return new float[]{score};
                } else {
                    float score = -1;
                    return new float[]{score};
                }


            default:
                float score = -1;
                return new float[]{score};
        }
    }


    public static float[] nihssScore(Cursor cursor){
        float cns = cnsScore(cursor)[0];



        float score;
        if (cns == -1){
            score = -1;
        } else {
            score = 23-2*cns;
        }

        return new float[]{score};
    }

}
