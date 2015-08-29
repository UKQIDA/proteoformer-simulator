
package uk.ac.liv.proteoformer.simulator;

import gnu.trove.map.TDoubleDoubleMap;
import gnu.trove.map.hash.TDoubleDoubleHashMap;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 28-Aug-2015 17:25:23
 */
public class ChargedIsotopomer implements Isotopomer {

    private final BaseIsotopomer baseIsotopomer;
    private final int chargeState;
    private final TDoubleDoubleMap peakMap;

    ChargedIsotopomer(BaseIsotopomer bIs, int charge) {
        this.baseIsotopomer = bIs;
        this.chargeState = charge;
        this.peakMap = new TDoubleDoubleHashMap();
    }

    public int getChargeState() {
        return this.chargeState;
    }

    @Override
    public String getSequence() {
        return this.baseIsotopomer.getSequence();
    }

    @Override
    public TDoubleDoubleMap getPeakMap() {
        TDoubleDoubleMap basePeakMap = this.baseIsotopomer.getPeakMap();
        for (double mass : basePeakMap.keys()) {
            double mz = mass / this.chargeState + CliConstants.PROTON_MASS;
            double intensity = basePeakMap.get(mass);
            peakMap.put(mz, intensity);
        }
        return peakMap;
    }

    /**
     * @return the baseIsotopomer
     */
    public BaseIsotopomer getBaseIsotopomer() {
        return baseIsotopomer;
    }

}
