import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class IRCColorSelect {

    // Made by Joseph El-Khouri

    // Used info from here for help ...
    // https://help.codeux.com/textual/Text-Formatting.kb

    public static String BOLD       = "\u0002";
    public static String ITALIC     = "\u001D";
    public static String UNDERLINE  = "\u001F";
    public static String COLOR      = "\u0003";
    public static String TERMINATE  = "\u000F";
    
    private String text;
    private int color;
    private int background;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    
    public String getText() {
        return text;
    }
    
    public String getColor() {
        return n2sf(color);
    }
    
    public String getBackground() {
        return n2sf(background);
    }
    
    public String getBold() {
        return yn(bold);
    }
    
    public String getItalic() {
        return yn(italic);
    }
    
    public String getUnderline() {
        return yn(underline);
    }
    
    public void setText(String t) {
        text = t;
    }
    
    public void setColor(int c) {
        if (c < -1) color = -1;
        if (c > 15) color = 15;
        color = c;
    }
    
    public void setBackground(int b) {
        if (b < -1) background = -1;
        if (b > 15) background = 15;
        background = b;
    }
    
    public boolean toggleBold() {
        bold = !bold;
        return bold;
    }
    
    public boolean toggleItalic() {
        italic = !italic;
        return italic;
    }
    
    public boolean toggleUnderline() {
        underline = !underline;
        return underline;
    }
    
    private String yn(boolean b) {
        if (b) return "yes";
        return "nah";
    }
    
    private String n2s(int i) {
        if (i<0) return "00";
        if (i>15) return "15";
        if (i<10) return "0" + String.valueOf(i);
        return String.valueOf(i);
    }
    
    public static String n2sf(int i) {
        switch (i) {
            case -1:
                return "nah";
            case 0: 
                return "white";
            case 1: 
                return "black";
            case 2: 
                return "blue (navy)";
            case 3: 
                return "green";
            case 4: 
                return "red";
            case 5: 
                return "brown (maroon)";
            case 6: 
                return "purple";
            case 7: 
                return "orange";
            case 8: 
                return "yellow";
            case 9: 
                return "light green (lime)";
            case 10:
                return "teal (green/blue cyan)";
            case 11:
                return "light cyan (cyan) (aqua)";
            case 12:
                return "light blue (royal)";
            case 13:
                return "pink (light purple) (fuchsia)";
            case 14:
                return "grey";
            case 15:
                return "light grey (silver)";
            default: 
                return "ERROR (NON COLOR)";
        }
    }
    
    public IRCColorSelect() {
        ArrayList<String> msgs = new ArrayList<>();
        msgs.add(":D lol hey there");
        msgs.add("(^:");
        msgs.add("colors r cool");
        msgs.add("butts");
        msgs.add("8==========D");
        msgs.add("lmao");
        msgs.add("'ls' is not recognized as an internal or external command");
        msgs.add("OH    YE");
        msgs.add("cleanis");
        msgs.add("wenis");
        msgs.add("AAAAAAAAAAAAAAAAAAA");
        msgs.add("wasd");
        msgs.add(":D :D :D/");
        msgs.add("O: !!!!");
        msgs.add("saveybot wuz here");
        Random r = new Random();
        text = msgs.get(r.nextInt(msgs.size()));
        color = -1;
        background = -1;
        bold = false;
        italic = false;
        underline = false;
    }
    
    private String toClipboard() {
        StringBuilder pre  = new StringBuilder();
        StringBuilder post = new StringBuilder();
        if (bold) {
            pre.append(BOLD);
            post.insert(0, BOLD);
        }
        if (italic) {
            pre.append(ITALIC);
            post.insert(0, ITALIC);
        }
        if (underline) {
            pre.append(UNDERLINE);
            post.insert(0, UNDERLINE);
        }
        if (color != -1) {
            pre.append(COLOR);
            pre.append(n2s(color));
            if (background != -1) {
                pre.append(",");
                pre.append(n2s(background));
            }
            post.insert(0, COLOR);
        }
        pre.append(text).append(post.toString());
        StringSelection ss = new StringSelection(pre.toString());
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(ss, null);
        return pre.toString();
    }

    public static void main(String[] args) {
        IRCColorSelect ics = new IRCColorSelect();
        Scanner s;
        String currentMessage = "";
        while(true) {
            s = new Scanner(System.in);
            System.out.println("__________________________________");
            System.out.println("| #  IRC Color Select :: Menu  # |");
            System.out.println("|--------------------------------|");
            System.out.println("|[m] Set Message (from clipboard)|");
            System.out.println("|[n] Set Message (typed)         |");
            System.out.println("|[-] Copy to Clipboard           |");
            System.out.println("|[c] Set Color/Background        |");
            System.out.println("|[b] Bold                        |");
            System.out.println("|[i] Italic                      |");
            System.out.println("|[u] Underline                   |");
            System.out.println("|[r] Reset                       |");
            System.out.println("|________________________________|");
            System.out.println("MSG: " + ics.getText());
            System.out.println("[STATUS]");
            System.out.println("       BOLD: " + ics.getBold());
            System.out.println("     ITALIC: " + ics.getItalic());
            System.out.println("  UNDERLINE: " + ics.getUnderline());
            System.out.println("      COLOR: " + ics.getColor());
            System.out.println(" BACKGROUND: " + ics.getBackground());
            System.out.println(currentMessage);
            currentMessage = "";
            System.out.printf(" -> ");
            String option = s.nextLine();
            if (option.isEmpty() || option.length() > 1) option = "wtf lmao";
            option = option.toLowerCase();
            StringBuilder msg = new StringBuilder();
            s = new Scanner(System.in);
            boolean bool;
            switch (option.charAt(0)) {
                case 'm':
                    try {
                        String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor); 
                        msg.append("Data from clipboard: ").append(data);
                        ics.setText(data);
                        
                    } catch (Exception e) {
                        msg.append("Yikes, clipboard get failed!");
                    }
                    break;
                case 'n':
                    System.out.printf(" Set new message -> ");
                    String newMessage = s.nextLine();
                    ics.setText(newMessage);
                    msg.append("New message set: ");
                    msg.append(newMessage);
                    break;
                case 'c':
                    System.out.println("-- FOREGROUND --");
                    for (int i=-1; i<=15; i++) {
                        System.out.println(i + ". " + IRCColorSelect.n2sf(i));
                    }
                    System.out.printf(" foreground -> ");
                    String f = s.nextLine();
                    System.out.printf(" background -> ");
                    String b = s.nextLine();
                    int fi;
                    int bi;
                    try {
                        fi = Integer.parseInt(f);
                        bi = Integer.parseInt(b);
                        if (fi < -1 || fi > 15 || bi < -1 || bi > 15) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        msg.append("That wasn't a valid number!! '" + f + "' or '" + b + "'...");
                        break;
                    }
                    ics.setColor(fi);
                    ics.setBackground(bi);
                    msg.append("The foreground is now ");
                    msg.append(IRCColorSelect.n2sf(fi));
                    msg.append(" and the background is now ");
                    msg.append(IRCColorSelect.n2sf(bi));
                    break;
                case 'b':
                    bool = ics.toggleBold();
                    msg.append("Bold toggled... was ");
                    msg.append(!bool);
                    msg.append(", now ");
                    msg.append(bool);
                    break;
                case 'i':
                    bool = ics.toggleItalic();
                    msg.append("Italic toggled... was ");
                    msg.append(!bool);
                    msg.append(", now ");
                    msg.append(bool);                    
                    break;
                case 'u':
                    bool = ics.toggleUnderline();
                    msg.append("Underline toggled... was ");
                    msg.append(!bool);
                    msg.append(", now ");
                    msg.append(bool);  
                    break;
                case '-':
                    msg.append("Message sent to clipboard: ");
                    msg.append(ics.toClipboard());
                    break;
                case 'r':
                    msg.append("ICS Reset to Default Settings");
                    ics = new IRCColorSelect();
                    break;
                default:
                    msg.append("Error: Invalid Menu Option '").append(option).append("'");
                    break;
            }
            currentMessage = msg.toString();
        }
    }

}
