
package uk.ac.liv.proteoformer.simulator;

import gnu.trove.map.TDoubleDoubleMap;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 28-Aug-2015 12:50:32
 */
public class Simulator {

    public static void main(String[] args) {
        //test area

//        NormalDistribution nd = new NormalDistribution();
//        double x = -3.0;
//        for (int i = -10; i < 11; i++) {
//            
//            System.out.println("x = " + i + ", pdf(x) = " + nd.density(x) + ";");
//            x += 0.3;
//        }
        try {
            CommandLineParser parser = new DefaultParser();
            Options options = new Options();

            String helpOpt = "h";
            options.addOption(helpOpt, false, CliConstants.HELP_DESCRIPTION);

            String seqOpt = "sequence";
            options.addOption(Option.builder("s")
                    .hasArg()
                    .argName("Protein Sequence")
                    .longOpt(seqOpt)
                    .required(true)
                    .desc(CliConstants.SEQ_DESCRIPTION)
                    .build());

            String widOpt = "isotopomer-width";
            options.addOption(Option.builder("w")
                    .hasArg()
                    .argName("width")
                    .longOpt(widOpt)
                    .required(true)
                    .desc("the width of isotopomer window.")
                    .build());

            String outOpt = "output";
            options.addOption(Option.builder("o")
                    .hasArg()
                    .argName("output csv file")
                    .longOpt(outOpt)
                    .required(true)
                    .desc(CliConstants.OUTPUT_DESCRIPTION)
                    .build());
            
            //example
            args = new String[6];
            args[0] = "-s";
            args[1] = "MRLSSPASLAVLRFRPLCIFFFQVLPSKSCFPRFSVTPFSSVFTFLLRPPVSSFPSSFSPRMPQREETPLLARVSA"
                    + "PLSEREELVSLVVCLPASLHLLSANSLLLLSVGRSSSRRNFLLRGNNREGGEKRTAFQERREKRTGTNKRGKNEESLREKRATPEVESQHQVKALFVGASLPFCPRPFWSLRSLSLHI";
            args[2] = "-w";
            args[3] = "13";
            args[4] = "-o";
            args[5] = "peakMap.csv";
            
            // parse command line
            CommandLine line = parser.parse(options, args);

            // interrogating stage
            if (line.getOptions().length == 0 || line.hasOption(helpOpt)) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("Proteoformer Simulator", options);
            }
            else {
                try {
                    String seq = line.getOptionValue("s");
                    String out = line.getOptionValue("o");
                    String widthString = line.getOptionValue("w");
                    int width = Integer.parseInt(widthString);

                    SimulatorBuilder simBuilder = new SimulatorBuilder(seq, width);

                    TDoubleDoubleMap peakMap = simBuilder.getPeakMap();
                    write(peakMap, out);
                }
                catch (NumberFormatException nfex) {
                    throw new IllegalArgumentException("Input width is not an integer: " + nfex.getMessage() + ".\n");
                }

            }
        }
        catch (ParseException ex) {
            Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void write(TDoubleDoubleMap peakMap, String out) {

        try (FileWriter writer = new FileWriter(out)) {
            writer.write("mz, intensity\n");
            for (double mz : peakMap.keys()) {
                writer.write(String.valueOf(mz) + ", " + String.valueOf(peakMap.get(mz)) + "\n");
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
