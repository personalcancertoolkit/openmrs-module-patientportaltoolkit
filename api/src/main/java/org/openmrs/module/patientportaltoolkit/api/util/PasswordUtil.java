/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientportaltoolkit.api.util;

import java.util.Random;

/**
 * Created by maurya on 6/29/16.
 */
public class PasswordUtil {

        private static final String ALPHA_CAPS  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String ALPHA   = "abcdefghijklmnopqrstuvwxyz";
        private static final String NUM     = "0123456789";
        private static final String SPL_CHARS   = "!@#$%^&*_=+-/";

        public static char[] generatePassword(int minLen, int maxLen, int noOfCAPSAlpha,
                                          int noOfDigits, int noOfSplChars) {
            if(minLen > maxLen)
                throw new IllegalArgumentException("Min. Length > Max. Length!");
            if( (noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen )
                throw new IllegalArgumentException
                        ("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
            Random rnd = new Random();
            int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
            char[] pswd = new char[len];
            int index = 0;
            for (int i = 0; i < noOfCAPSAlpha; i++) {
                index = getNextIndex(rnd, len, pswd);
                pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
            }
            for (int i = 0; i < noOfDigits; i++) {
                index = getNextIndex(rnd, len, pswd);
                pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
            }
            for (int i = 0; i < noOfSplChars; i++) {
                index = getNextIndex(rnd, len, pswd);
                pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
            }
            for(int i = 0; i < len; i++) {
                if(pswd[i] == 0) {
                    pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
                }
            }
            return pswd;
        }

        private static int getNextIndex(Random rnd, int len, char[] pswd) {
            int index = rnd.nextInt(len);
            while(pswd[index = rnd.nextInt(len)] != 0);
            return index;
        }

    public static char[] getNewPassword() {
        int noOfCAPSAlpha = 1;
        int noOfDigits = 1;
        int noOfSplChars = 1;
        int minLen = 8;
        int maxLen = 12;
        return generatePassword(minLen, maxLen, noOfCAPSAlpha,
         noOfDigits, noOfSplChars);

    }




}
