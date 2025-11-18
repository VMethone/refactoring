package theater;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * HTML variant of the StatementPrinter that renders the invoice as HTML.
 */
public class HTMLStatementPrinter extends StatementPrinter {

    public HTMLStatementPrinter(Invoice invoice, java.util.Map<String, Play> plays) {
        super(invoice, plays);
    }

    @Override
    public String statement() {
        final StringBuilder result = new StringBuilder(String.format("<h1>Statement for %s</h1>%n",
                invoice.getCustomer()));
        result.append("<table>").append(System.lineSeparator());
        result.append(String.format(" <caption>Statement for %s</caption>%n", invoice.getCustomer()));
        result.append(" <tr><th>play</th><th>seats</th><th>cost</th></tr>").append(System.lineSeparator());
        for (Performance p : invoice.getPerformances()) {
            Play play = getPlay(p);
            result.append(String.format(" <tr><td>%s</td><td>%s</td><td>%s</td></tr>%n",
                    play.getName(), p.getAudience(), usd(getAmount(p))));
        }
        result.append("</table>").append(System.lineSeparator());

        result.append(String.format("<p>Amount owed is <em>%s</em></p>%n", usd(getTotalAmount())));
        result.append(String.format("<p>You earned <em>%s</em> credits</p>%n", getTotalVolumeCredits()));
        return result.toString();
    }
}
