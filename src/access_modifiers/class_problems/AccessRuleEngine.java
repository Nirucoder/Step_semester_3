package access_modifiers.class_problems;

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
