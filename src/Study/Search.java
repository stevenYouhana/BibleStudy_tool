package Study;

import java.util.LinkedList;
import java.util.List;

import Study.Bible.Book_Verses;
import javafx.scene.control.TextField;

public class Search {
	String content = "";
	List<Verse> foundVerses = new LinkedList<>(); 
	TextField txtSearch;
	Utility.Log p =  new Utility.Log();
	
	public Search(TextField txtSearch) {
		this.txtSearch = txtSearch;
		
		txtSearch.setOnKeyReleased(event -> {
			Search_String searchString = null;
				p.p("key pressesd: ");
				searchString = new Search_String();
				searchString.start();
		});
	}
	public List<Verse> getFoundVerses() {
		return foundVerses;
	}
	private class Search_String extends Thread {
		
		@Override
		public void run() {
			p.p("running thread >> clearing current list");
			foundVerses.clear();
			try {
				p.p("sleeping");
				Search_String.sleep(1000);
				synchronized(txtSearch) {
					p.p("synch method");
					p.p("searching for: "+txtSearch.getText());
					generateVerses(txtSearch.getText());
				}
			} catch (InterruptedException e) {
				p.p("Search_search: "+e.toString());
			}
		}
	}
	
	public void generateVerses(String find) {
		p.p("generateVerses: "+find);
		Bible.mass_verses.forEach( (verse) -> {
			content = verse.toString();
			if(indexOf(content.toLowerCase().toCharArray(),
					find.toLowerCase().toCharArray()) != -1) {
				foundVerses.add(verse);
				p.p("Search found in: "+Bible.Book_Verses.MAP.get(verse.getVerseData()[0]));
			}
		});
	}
	
	 /**
     * Returns the index within this string of the first occurrence of the
     * specified substring. If it is not a substring, return -1.
     * 
     * @param haystack The string to be scanned
     * @param needle The target string to search
     * @return The start index of the substring
     */
    public static int indexOf(char[] haystack, char[] needle) {
        if (needle.length == 0) {
            return 0;
        }
        int charTable[] = makeCharTable(needle);
        int offsetTable[] = makeOffsetTable(needle);
        for (int i = needle.length - 1, j; i < haystack.length;) {
            for (j = needle.length - 1; needle[j] == haystack[i]; --i, --j) {
                if (j == 0) {
                    return i;
                }
            }
            // i += needle.length - j; // For naive method
            i += Math.max(offsetTable[needle.length - 1 - j], charTable[haystack[i]]);
        }
        return -1;
    }
    
    /**
     * Makes the jump table based on the mismatched character information.
     */
    private static int[] makeCharTable(char[] needle) {
        final int ALPHABET_SIZE = Character.MAX_VALUE + 1; // 65536
        int[] table = new int[ALPHABET_SIZE];
        for (int i = 0; i < table.length; ++i) {
            table[i] = needle.length;
        }
        for (int i = 0; i < needle.length - 1; ++i) {
            table[needle[i]] = needle.length - 1 - i;
        }
        return table;
    }
    
    /**
     * Makes the jump table based on the scan offset which mismatch occurs.
     */
    private static int[] makeOffsetTable(char[] needle) {
        int[] table = new int[needle.length];
        int lastPrefixPosition = needle.length;
        for (int i = needle.length; i > 0; --i) {
            if (isPrefix(needle, i)) {
                lastPrefixPosition = i;
            }
            table[needle.length - i] = lastPrefixPosition - i + needle.length;
        }
        for (int i = 0; i < needle.length - 1; ++i) {
            int slen = suffixLength(needle, i);
            table[slen] = needle.length - 1 - i + slen;
        }
        return table;
    }
    
    /**
     * Is needle[p:end] a prefix of needle?
     */
    private static boolean isPrefix(char[] needle, int p) {
        for (int i = p, j = 0; i < needle.length; ++i, ++j) {
            if (needle[i] != needle[j]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns the maximum length of the substring ends at p and is a suffix.
     */
    private static int suffixLength(char[] needle, int p) {
        int len = 0;
        for (int i = p, j = needle.length - 1;
                 i >= 0 && needle[i] == needle[j]; --i, --j) {
            len += 1;
        }
        return len;
    }
    
}
