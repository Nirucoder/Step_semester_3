package access_modifiers.class_problems;
import java.util.Arrays;

// Since CriticalCareDischargeSummary extends this, it cannot be final, but we can make all fields final.
public class DischargeSummary {
    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        this.patientId = patientId;
        if (medicationCodes == null) {
            throw new IllegalArgumentException("construction rejected");
        }
        for (String code : medicationCodes) {
            if (code == null || !code.matches("MED-[A-Z]")) {
                throw new IllegalArgumentException("construction rejected");
            }
        }
        this.medicationCodes = Arrays.copyOf(medicationCodes, medicationCodes.length);
    }

    public String getPatientId() {
        return patientId;
    }

    public String[] getMedicationCodes() {
        return Arrays.copyOf(medicationCodes, medicationCodes.length);
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        String[] newCodes = Arrays.copyOf(medicationCodes, medicationCodes.length);
        newCodes[index] = newCode;
        return new DischargeSummary(this.patientId, newCodes);
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {
        if (summaries == null) return "0 processed";
        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;
        
        for (DischargeSummary summary : summaries) {
            if (summary == null) {
                nullSkipped++;
            } else {
                processed++;
                if (summary instanceof CriticalCareDischargeSummary) {
                    criticalCare++;
                } else {
                    routine++;
                }
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + criticalCare + " critical-care | " + routine + " routine";
    }
}
