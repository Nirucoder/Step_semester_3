import os

base_dir = r"C:\Users\user\Desktop\STEP\Step_semester_3\src\access_modifiers\class_problems"

files = {
    'AccessRuleEngine.java': '''package access_modifiers.class_problems;

public class AccessRuleEngine {
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if ("public".equals(fieldModifier)) {
            return "ALLOWED";
        }
        if ("private".equals(fieldModifier)) {
            return "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }
        if ("default".equals(fieldModifier)) {
            return "SAME_PACKAGE".equals(accessorContext) || "SAME_CLASS".equals(accessorContext) ? "ALLOWED" : "DENIED";
        }
        if ("protected".equals(fieldModifier)) {
            if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext) || "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext)) {
                return "ALLOWED";
            }
            return "DENIED";
        }
        return "DENIED";
    }

    public static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;
        for (String[] attempt : attempts) {
            if (attempt != null && attempt.length == 2) {
                if ("ALLOWED".equals(classifyAccess(attempt[0], attempt[1]))) {
                    allowed++;
                } else {
                    denied++;
                }
            }
        }
        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    public static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.isEmpty()) return "";
        String[] parts = accessorContext.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() > 0) {
                sb.append(parts[i].substring(0, 1).toUpperCase());
                sb.append(parts[i].substring(1).toLowerCase());
                if (i < parts.length - 1) sb.append(" ");
            }
        }
        return sb.toString();
    }
}
''',
    'PatientRecord.java': '''package access_modifiers.class_problems;

public class PatientRecord {
    private String patientId;
    String wardCode;
    protected double vitalsScore;
    public String facilityName;

    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        if (patientId == null || patientId.trim().isEmpty() || patientId.trim().length() < 4) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.patientId = patientId.trim();
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }
}
''',
    'PatientVitals.java': '''package access_modifiers.class_problems;
import java.util.Arrays;

public class PatientVitals {
    private double[] readings;
    private int count;

    public PatientVitals(double[] initialReadings) {
        this.readings = new double[500];
        this.count = 0;
        if (initialReadings != null) {
            for (double r : initialReadings) {
                recordReading(r);
            }
        }
    }

    public void recordReading(double reading) {
        if (reading > 0 && reading <= 45.0 && count < readings.length) {
            this.readings[count++] = reading;
        }
    }

    public double getAverage() {
        if (count == 0) return 0.0;
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += readings[i];
        }
        return sum / count;
    }

    public double[] getAllReadings() {
        return Arrays.copyOf(readings, count);
    }
}
''',
    'PatientProfile.java': '''package access_modifiers.class_problems;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PatientProfile {
    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPinHash;
    private boolean idSet = false;

    public PatientProfile(String patientId, String name) {
        this.name = name;
        setPatientId(patientId);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile() {
        this(null, null);
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {
        if (!idSet && id != null) {
            this.patientId = id;
            this.idSet = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    public void setLockerPin(String pin) {
        if (pin != null && pin.matches("\\\\d{4,6}")) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(pin.getBytes());
                this.lockerPinHash = Base64.getEncoder().encodeToString(hash);
            } catch (NoSuchAlgorithmException e) {
                this.lockerPinHash = pin;
            }
        }
    }
}
''',
    'DischargeSummary.java': '''package access_modifiers.class_problems;
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
''',
    'CriticalCareDischargeSummary.java': '''package access_modifiers.class_problems;

public final class CriticalCareDischargeSummary extends DischargeSummary {
    private final int icuDays;

    static {
        // Any one-time shared state for CriticalCareDischargeSummary
    }

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}
'''
}

for filename, content in files.items():
    with open(os.path.join(base_dir, filename), 'w') as f:
        f.write(content)

print('Session 1 files created.')
