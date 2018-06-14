import java.util.*;
import java.io.*;

public class Foo {
    static boolean DEBUG = false;
    static String UCB_ADDITIONAL = "One of the following is required for admission effective Fall 2016:";

    public static void main (String[] args) throws Exception {
        Scanner scan = new Scanner (new File ("original.txt"));
        String line = "";
        String school = null, target = null;
        Course prev = null;
        boolean is_extra = false;
        Map<String, Set<Course>> map = new HashMap<> ();
        while (scan.hasNextLine ()) {
            line = scan.nextLine ();
            if (DEBUG) System.out.println ("\n" + line);
            if (school != null && school.equals ("pass") && !line.startsWith ("UC"))
                continue;
            if (line.startsWith ("UC")) {
                line = line.trim ();
                school = line;
                is_extra = false;
            } else if (line.contains ("recommended") || line.contains ("RECOMMENDED")) {
                is_extra = true;
            } else if (line.contains (UCB_ADDITIONAL)) {
                Set<Course> tmp_set = new HashSet<> ();
                tmp_set.add (new Course ("Refer to ASSIST for details", school));
                map.put (UCB_ADDITIONAL, tmp_set);
                school = "pass";
            } else {
                if (line.length () == 0 ||
                    !line.contains ("|") ||
                    line.startsWith ("C++, or Java")
                    )
                    continue;
                String[] halves = line.split ("\\|");
                String[] left = getCourse (halves[0]), right = {"", ""};
                if (halves.length > 1)
                    right = getCourse (halves[1]);
                if (DEBUG) System.out.printf ("%s\n%s\n", Arrays.deepToString (left), Arrays.deepToString (right));
                if (left[0].length () == 0 && right[0].length () == 0)
                    continue;
                if (left[0].length () > 0) {
                    target = left[0];
                }
                Course cur = new Course (target, school);
                if (right[0].length () > 0)
                    map.computeIfAbsent (right[0], S -> new HashSet<> ()).add (cur);
                if (DEBUG) System.out.printf ("%s\n", map);
                prev = cur;
            }
        }
        List<PreCourse> ls = new ArrayList<> (map.size ());
        for (String cur : map.keySet ())
            ls.add (new PreCourse (cur, map.get (cur)));
        Collections.sort (ls, (a, b) -> b.prereqs.size () != a.prereqs.size () ? b.prereqs.size () - a.prereqs.size () : a.name.compareTo (b.name));
        StringBuilder sb = new StringBuilder ();
        sb.append ("\"Course Name\",\"Schools That Requires\",\"Course Name in That School\"\n");
        for (PreCourse cur : ls) {
            boolean first_line = true;
            if (first_line) {
                sb.append ("\"").append (cur.name).append ("\"");
                first_line = false;
            } else {
                sb.append ("\"").append ("\"");
            }
            for (Course cur_target : cur.prereqs) {
                sb.append (String.format (",\"%s\",\"%s\"\n", cur_target.school, cur_target.name));
            }
        }
        try (BufferedWriter bw = new BufferedWriter (new FileWriter (new File ("output.csv")))) {
            bw.write (sb.toString ());
        } catch (Exception ex) {
            System.err.println ("Writing failed.");
            System.exit (1);
        }
    }

    static String trimHelper (Set<String> set) {
        try {
            int len = set.toString ().length ();
            return set.toString ().substring (1, len - 1);
        } catch (Exception ex) {
            ex.printStackTrace ();
            System.err.printf ("Trimming (%s) failed\n", set);
            System.exit (1);
        }
        return null;
    }

    static class PreCourse {
        String name;
        Set<Course> prereqs;

        PreCourse (String name, Set<Course> c) {
            this.name = name;
            this.prereqs = c;
        }
    }

    static class Course {
        String name, school;

        public Course (String name, String school) {
            this.name = name;
            this.school = school;
        }

        public String toString () {
            return String.format ("(%s, %s)", this.name, this.school);
        }
    }

    static String[] getCourse (String S) {
        if (S.startsWith (" ") || 
            Character.isLowerCase (S.charAt (0)) ||
            S.toLowerCase ().startsWith ("no course articulated"))
            return new String[] {"", ""};
        S = S.trim ();
        StringBuilder sb = new StringBuilder ();
        for (int i = 0; i < S.length (); i++) {
            if (S.charAt (i) == ' ' && S.charAt (i - 1) == ' ')
                break;
            else
                sb.append (S.charAt (i));
        }
        String[] res = new String[2];
        res[1] = "";
        res[0] = sb.toString ().trim ();
        int last_left = S.lastIndexOf ("(");
        if (last_left >= 0) {
            int last_right = S.lastIndexOf (")");
            res[1] = S.substring (last_left + 1, last_right);
        }
        return res;
    }
}