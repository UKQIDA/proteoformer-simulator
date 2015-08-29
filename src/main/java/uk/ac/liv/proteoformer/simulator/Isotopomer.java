
package uk.ac.liv.proteoformer.simulator;

import gnu.trove.map.TDoubleDoubleMap;
import gnu.trove.map.hash.TDoubleDoubleHashMap;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 28-Aug-2015 16:26:34
 */
public interface Isotopomer {

    String getSequence();

    TDoubleDoubleMap getPeakMap();

    default int getSequenceLength() {
        return this.getSequence().length();
    }

    default int getMaxChargeState() {
        return this.getSequenceLength() / 10;
    }

    default int getCentralChargeState() {
        return (int) (this.getMaxChargeState() * 0.7);
    }

    default TDoubleDoubleMap getScaledPeakMap(double factor) {
        TDoubleDoubleMap scPeakMap = new TDoubleDoubleHashMap();
        TDoubleDoubleMap peakMap = this.getPeakMap();
        for (double mz : peakMap.keys()) {
            double intensity = peakMap.get(mz);
            scPeakMap.put(mz, intensity * factor);
        }
        return scPeakMap;
    }

}
