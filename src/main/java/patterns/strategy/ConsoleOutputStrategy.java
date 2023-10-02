package patterns.strategy;

public class ConsoleOutputStrategy implements OutputStrategy {
    @Override
    public String generateOutput(String content) {
        return content;
    }
}
