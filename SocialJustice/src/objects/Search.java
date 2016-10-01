package objects;
import java.util.HashSet;
import java.util.Vector;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Search {
	
	private final int SEARCH_CAP = 10;
	
	private Database db;
	
	public Search() {
		db = new Database();
	}
	
	//Returns a vector of schools with schools at lower indeces having names most similar to the search term
	public Vector<School> getSchools(String searchTerm) {
		Set<String> allSchools = db.getAllSchoolNames();
		PriorityQueue<ItemScore> results= new PriorityQueue<ItemScore>(10, this.new SchoolComparator());
		searchTerm = searchTerm.trim();
		String alphaOnlySearchTerm = "";
		for(int i = 0; i < searchTerm.length(); i++) {
			char c = searchTerm.charAt(i);
			if((c - 'a' >= 0 && c - 'a' < 26) || (c - 'A' >= 0 && c - 'A' < 26) || c == ' ') alphaOnlySearchTerm += c; 
		}
		for(String school : allSchools) {
			int score = calculateScore(alphaOnlySearchTerm, school);
			if(score > 9) results.add(new ItemScore(school, score));
		}
		Vector<School> topResults = new Vector<School>();
		int i = 0;
		while(!results.isEmpty() && i < SEARCH_CAP) {
			topResults.add(db.getSchoolFromName(results.remove().getSchool()));
			i++;
		}
		return topResults;
	}
	
	//Returns a vector of users based on similarity to the search term. Either the first or last name searched
	//must match exactly for a result to be found. Examines only the first two search terms.
	public Vector<RealUser> getUsers(String searchTerm){
		String alphaOnlySearchTerm = "";
		searchTerm = searchTerm.trim();
		for(int i = 0; i < searchTerm.length(); i++) {
			char c = searchTerm.charAt(i);
			if((c - 'a' >= 0 && c - 'a' < 26) || (c - 'A' >= 0 && c - 'A' < 26) || c == ' ') alphaOnlySearchTerm += c; 
		}
		searchTerm = alphaOnlySearchTerm;
		Vector<RealUser> results = new Vector<RealUser>();
		String[] terms = searchTerm.split("\\ +");
		Vector<Set<RealUser>> allSets = new Vector<Set<RealUser>>();
		int count = 0;
		for(String s: terms) {
			count++;
			Set<RealUser> fSet = db.getUserSetFirst(s);
			Set<RealUser> lSet = db.getUserSetLast(s);
			fSet.addAll(lSet);
			allSets.add(fSet);
			if(count == 2) break;
		}
		count = 1; 
		if(allSets.size() == 1) {
			for(RealUser ru : allSets.get(0)) {
				if(count > SEARCH_CAP) break;
				results.add(ru);
				count++;
			}
		} else if(allSets.size() == 2) {
			Set<RealUser> both = new HashSet<RealUser>();
			for(RealUser ru : allSets.get(0)) {
				if(allSets.get(1).contains(ru)) {
					both.add(ru);
				}
			}
			allSets.get(0).removeAll(both);
			allSets.get(1).removeAll(both);
			for(RealUser ru : both) {
				if(count > SEARCH_CAP) break;
				results.add(ru);
				count++;
			}
			for(RealUser ru : allSets.get(0)) {
				if(count > SEARCH_CAP) break;
				results.add(ru);
				count++;
			}
			for(RealUser ru : allSets.get(1)) {
				if(count > SEARCH_CAP) break;
				results.add(ru);
				count++;
			}
		}
		return results;
	}
	
	//Returns vector of posts with the matching title.
	//Titles must match exactly.
	public Vector<Post> getPosts(String postTitle) {
		Vector<Post> posts = db.getPostFromTitle(postTitle);
		Vector<Post> results = new Vector<Post>();
		for(int i = 0; i < SEARCH_CAP; i++) {
			if(i == posts.size()) break;
			results.add(posts.get(i));
		}
		return results;
	}
	
	//Returns the score representing how similar the search terms are
	private int calculateScore(String searchTerm, String item) {
		searchTerm = searchTerm.toLowerCase();
		String noSpaceSearchTerm = "";
		for(int i = 0; i < searchTerm.length(); i++) {
			if(searchTerm.charAt(i) != ' ') noSpaceSearchTerm += searchTerm.charAt(i);
		}
		String itemWithCaps = item;
		item = item.toLowerCase();
		//Exact match
		if(searchTerm.equals(item)) return Integer.MAX_VALUE;
		//Similar characters
		int score = 0;
		int[] a = new int[26];
		int[] b = new int[26];
		for(int i = 0; i < 26; i++) {
			a[i] = 0;
			b[i] = 0;
		}
		for(int i = 0; i < noSpaceSearchTerm.length(); i++) {
			a[noSpaceSearchTerm.charAt(i) - 'a'] += 1;
		}
		for(int i = 0; i < item.length(); i++) {
			if(item.charAt(i) != ' ') b[item.charAt(i) - 'a'] += 1;
		}
		for(int i = 0; i < 26; i++) {
			score += Math.min(a[i], b[i]);
		}
		//Initialism
		Scanner s = new Scanner(item);
		Scanner c = new Scanner(itemWithCaps);
		int i = 0;
		while(i < noSpaceSearchTerm.length() && s.hasNext()) {
			char cap = c.next().charAt(0);
			if(cap - 'A' >= 0 && cap - 'A' < 26) {
				if(noSpaceSearchTerm.charAt(i) == s.next().charAt(0)) {
					score += (i + 1) * 10;
					i++;
				}
			} else {
				s.next();
			}
		}
		c.close();
		s.reset();
		//First letter bonus
		Scanner t = new Scanner(searchTerm);
		while(s.hasNext() && t.hasNext()) {
			if(s.next().charAt(0) == t.next().charAt(0));
		}
		s.close();
		t.close();
		return score;
	}
	
	//Closes the database
	public void done(){
		db.disconnect();
	}
	
	//Used for priority queue
	private class ItemScore {
		
		private String school;
		private int score;
		
		public ItemScore(String school, int score) {
			this.school = school;
			this.score = score;
		}
		
		//Returns school name
		public String getSchool() {
			return school;
		}
		
		//Returns school score
		public int getScore() {
			return score;
		}
	}
	
	//Comparator for two school scores in which the school with the higher
	//score is given priority
	private class SchoolComparator implements Comparator<ItemScore> {

		@Override
		public int compare(ItemScore s1, ItemScore s2) {
			if(s1.getScore() < s2.getScore()) return 1;
			else if(s1.getScore() == s2.getScore()) return 0;
			else return -1;
		}
	}
}
