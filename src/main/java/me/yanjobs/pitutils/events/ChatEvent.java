package me.yanjobs.pitutils.events;

import net.weavemc.loader.api.event.ChatReceivedEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import me.yanjobs.pitutils.PitUtils;
import me.yanjobs.pitutils.utils.AddChatMessage;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
public class ChatEvent {
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    /**
     * @param min the minimum value.
     * @param max the maximum value.
     * @return a random number.
     */
    public static double simpleRandom(final double min, final double max) {
        double x = min;
        double y = max;

        if (min == max) {
            return min;
        } else if (min > max) {
            x = max;
            y = min;
        }

        return ThreadLocalRandom.current().nextDouble(x, y);
    }

    /**
     *
     * @param message the unformatted message containing quickMaths
     * @return the equation's result parsed from the message param
     */
    public String result (String message) {
        String expression = message.substring(message.indexOf(":")+2);
        expression = expression.replace("x", "*");
        return "/ac " + (int) (eval(expression));
    }

    public static double minRange() throws IOException {
        String[] range = PitUtils.getConfig().getProperties().getProperty("quickmaths.range").split(",");
        return Integer.parseInt(range[0]);
    };
    public static double maxRange() throws IOException {
        String[] range = PitUtils.getConfig().getProperties().getProperty("quickmaths.range").split(",");
        return Integer.parseInt(range[1]);
    };

    @SubscribeEvent
    public void onChatReceived(ChatReceivedEvent event) throws IOException {
        if (Objects.equals(PitUtils.getConfig().getProperties().getProperty("quickmaths.enabled"), "true")) {
            String quickMathMessage = event.getMessage().getUnformattedText();

            if (quickMathMessage.contains("QUICK MATHS! Solve: ")) {
                String result = result(quickMathMessage);

                // Creating a new task
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Minecraft.getMinecraft().thePlayer.sendChatMessage(result);
                        try {
                            AddChatMessage.addVerboseMessage("Successfully answered Quick Maths.");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                Timer timer = new Timer("Timer");
                long delay = (long) simpleRandom(minRange(), maxRange());
                // Timing the task with the delay created above
                timer.schedule(task, delay);
            }
        }
    }
}
