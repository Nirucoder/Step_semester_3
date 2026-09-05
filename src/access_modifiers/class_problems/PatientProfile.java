package access_modifiers.class_problems;

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
        if (pin != null && pin.matches("\\d{4,6}")) {
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
