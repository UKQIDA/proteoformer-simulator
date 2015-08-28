
package uk.ac.liv.proteoformer.simulator;

import gnu.trove.map.TDoubleDoubleMap;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 28-Aug-2015 16:26:34
 */
public interface Isotopomer {

    String getSequence();
    TDoubleDoubleMap getPeakMap();
    
    default int getSequenceLength(){
        return this.getSequence().length();
    }
    
    default int getMaxChargeState(){
        return this.getSequenceLength()/10;
    }
    
    default int getCentralChargeState(){
        return (int) (this.getMaxChargeState() * 0.7);
    }
}
