package patterns.strategy;

public class HTMLOutputStrategy implements OutputStrategy {
    @Override
    public String generateOutput(String content) {
        return  """
                <html>
                    <head>
                    <title> Rental Service </title>
                    </head>
                    <body>
                        <p>""" + content + """
                        </p>
                    </body>
                </html>
                """;
    }
}
