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
