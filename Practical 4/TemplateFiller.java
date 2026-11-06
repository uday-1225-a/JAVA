import java.util.regex.*;

public class TemplateFiller {

    public static void main(String[] args) {

        String template =
            "Dear {name}, order {id} ships {date}.";

        String[] names = {"name", "id", "date"};

        String[] values = {"Uday", "A07", "2023-10-15"};

        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");

        Matcher matcher = pattern.matcher(template);

        StringBuilder result = new StringBuilder();

        while (matcher.find()) {

            String key = matcher.group(1);

            String replacement = "[?]";

            for (int i = 0; i < names.length; i++) {

                if (names[i].equals(key)) {
                    replacement = values[i];
                    break;
                }
            }

            matcher.appendReplacement(
                result,
                Matcher.quoteReplacement(replacement)
            );
        }

        matcher.appendTail(result);

        System.out.println(result);
    }
}