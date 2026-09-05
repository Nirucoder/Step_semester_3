package access_modifiers.class_problems;

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
