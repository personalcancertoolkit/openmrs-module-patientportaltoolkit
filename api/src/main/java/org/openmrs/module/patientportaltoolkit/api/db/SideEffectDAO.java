/**
 * The contents of this file are subject to the Regenstrief Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance with the License.
 * Please contact Regenstrief Institute if you would like to obtain a copy of the license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) Regenstrief Institute.  All Rights Reserved.
 */

package org.openmrs.module.patientportaltoolkit.api.db;

import org.openmrs.module.patientportaltoolkit.SideEffect;

import java.util.List;

/**
 * Created by Maurya on 02/06/2015.
 */
public interface SideEffectDAO {

    void deleteSideEffect(SideEffect sideEffect);

    List<SideEffect> getAllSideEffects();

    void saveSideEffect(SideEffect sideEffect);
}
