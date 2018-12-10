package Study;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.scene.control.TextField;

public class Search {
	String content = "";
	public volatile static List<Verse> foundVerses = null;
	private TextField txtSearch;
	Search_String searchString;
	Utility.Log p =  new Utility.Log();
	
	public Search(TextField txtSearch) {
		this.txtSearch = txtSearch;
		foundVerses = FXCollections.observableArrayList();
		p.p("init foundVerses: "+foundVerses);
		this.txtSearch.setOnKeyReleased(event -> {
				searchString = new Search_String(txtSearch);
				searchString.start();
				synchronized(this) {
					if(!foundVerses.isEmpty()) notifyAll();
				}
		});
		
	}
	public Set<String> getFoundVerses() {
		Set<String> set = FXCollections.observableSet();
		foundVerses.forEach( verse -> {
			set.add(verse.toString());
		});
		foundVerses.clear();
		return set;
	}
	public class Search_String extends Thread {
		private TextField txtSearch;
		protected Search_String(TextField txtSearch) {
			this.txtSearch = txtSearch;
		}
		@Override
		public void run() {
			p.p("searchString run for "+txtSearch.getText());
			
			try {
				TimeUnit.SECONDS.sleep(1);	
					generateVerses(txtSearch.getText());
			} catch (InterruptedException e) {
				p.p("Search_search: "+e.toString());
			}
		}
	}
	
	public void generateVerses(String find) {
		Bible.mass_verses.forEach( (verse) -> {
			content = verse.toString();
			if(indexOf(content.toLowerCase().toCharArray(),
					find.toLowerCase().toCharArray()) != -1) {
				foundVerses.add(verse);
				p.p("verse list"+foundVerses);
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
            // i += needle.length - j; // For native method
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
