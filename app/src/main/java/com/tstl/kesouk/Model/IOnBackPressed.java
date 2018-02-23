package com.tstl.kesouk.Model;

/**
 * Created by user on 18-Jan-18.
 */

public interface IOnBackPressed {
    /**
     * Si vous retouné true le back press ne sera pas pris en compte, sinon l'activité agira naturellement
     * @return true si votre traitement est prioritaire sinon false
     */
    boolean onBackPressed();
}