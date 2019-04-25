/*****************
 * Title: TravelingSalemanHeldKarp.java
 * Author: Tushar Roy
 * Date: 2016
 * Code Version: 3.0
 * Availability: https://github.com/mission-peace/interview/blob/master/src/com/interview/graph/TravelingSalesmanHeldKarp.java
 */

/*****************
 * Title: N/A
 * Author: Engineero
 * Date: 2016
 * Code Version: 1.0
 * Availability: http://stackoverflow.com/questions/127704/algorithm-to-return-all-combinations-of-k-elements-from-n
 */

import java.util.*;
import java.util.Map;

public class ShortestCombination {
	int [] indices;
	
	public static void main (String[]args){
		ShortestCombination route = new ShortestCombination();
		
		double [][] distanceMatrix = route.individualDistances(getAllDestinations());
		ArrayList<Destination> results = route.findShortestCombination(distanceMatrix, getAllDestinations());
		for (int i = 0; i < results.size(); i++){
			System.out.println(results.get(i).getName());
		}
		
		String example = "";
		for (int i = 0; i < results.size(); i++){
			if (i == results.size() - 1){
				example += results.get(i).getName();
			}else {
				example += results.get(i).getName() + " -> ";
			}
        }
		System.out.println(example);
	}
	
	public static ArrayList<Destination> getAllDestinations(){
		ArrayList<Destination> allDestinations = new ArrayList<Destination>();
		
		Destination ISAK = new Destination("ISAK", 0, 0, 0);
		allDestinations.add(ISAK);
		
		Destination Seizanso = new Destination("Seizanso", 40, 0, 1);
		allDestinations.add(Seizanso); 
		
		Destination KaruizawaHS = new Destination("Karuizawa High School", 30, -16, 2);
		allDestinations.add(KaruizawaHS);
		
		Destination KaruizawaJH = new Destination("Karuizawa Junior High School", 24, -14, 3);
		allDestinations.add(KaruizawaJH);
		
		Destination SeibuES = new Destination("Seibu Elementary School", -4, -16, 4);
		allDestinations.add(SeibuES);
		
		Destination ChubuES = new Destination("Chubu Elementary School", 16, -15, 5);
		allDestinations.add(ChubuES);
		
		Destination TobuES = new Destination("Tobu Elementary School", 34, -12, 6);
		allDestinations.add(TobuES);
		
		return allDestinations;
	}
	
	public int[] matching(ArrayList<Destination> destinations){
		int[] indices = new int[destinations.size()];
		
		for (int i = 0; i < indices.length; i++){
			indices[i] = destinations.get(i).getOrder();
		}
		
		return indices;
	}
	
	public double[][] individualDistances (ArrayList<Destination> destinations){ //Matrix of distances between every pair of nodes
		double [][] distances;
		distances = new double [destinations.size()][destinations.size()];
		
		for (int i = 0; i < destinations.size() ; i ++){
			for (int j = 0; j < destinations.size(); j++){
				distances[i][j] = destinations.get(i).distanceTo(destinations.get(j));
			}
		}
		return distances;
	}
	
	public ArrayList<Destination> findShortestCombination(double[][] distance, ArrayList<Destination> destinations){
		int number = distance.length;
		
		Map<Route, Double> costs = new HashMap<>();
		Map<Route, Integer> subRoutes = new HashMap<>();
		
		List<Set<Integer>> combinations = allCombinations(number - 1);

		for(Set<Integer> set : combinations) {
			for(int end : set) {
				Route index = Route.createRoute(end, set);
				
				double minimum = 10000000;
                int prev = 0;
                
				if (set.size() == 1){
					minimum = distance[0][end];
					costs.put(index, minimum);
					subRoutes.put(index, 0);

					continue;
				}
				Set<Integer> copySet = new HashSet<>(set);
				
				for(int previous : set) {
					if (end == previous){
						continue;
					}
					
					double cost = distance[previous][end] + getDistance(copySet, end, previous, costs);
					
					if(cost < minimum) {
                        minimum = cost;
                        prev = previous;
                    }
				}
				costs.put(index, minimum); 
                subRoutes.put(index, prev);
			}
		}
		
		Set<Integer> set = new HashSet<>();
        for(int i=1; i < number; i++) {
            set.add(i);
        }
        
        double min = 1000000;
        int prevVertex = -1;

        Set<Integer> copySet = new HashSet<>(set);
        
        for(int i = 1; i < number; i++) {
            double cost = distance[i][0] + getDistance(copySet, i, costs);
            if(cost < min) {
                min = cost;
                prevVertex = i;
            }
        }
        
        Set<Integer> finalSet = new HashSet<>();
        for(int i = 0; i < number; i++) {
            finalSet.add(i);
        }
        subRoutes.put(Route.createRoute(0, finalSet), prevVertex);
        int[] order = getRoute(subRoutes, number);
        
        int[] matching = matching(destinations);
        		
        ArrayList<Destination> orderedDestinations = new ArrayList<Destination>();
        for (int i = 0; i < order.length; i++){
        	orderedDestinations.add(getAllDestinations().get(matching[order[i]]));
        }
        
        return orderedDestinations;
	}
	
	private int[] getRoute(Map<Route, Integer> subRoutes, int number){
		Set<Integer> set = new HashSet<>();
		int [] reverse = new int [number + 1];
		int [] order = new int [number + 1];

		int increment = 0;
		
		for(int i = 0; i < number; i++) {
			set.add(i);
			}
		
		Integer start = 0;
		Integer temp;

        while(true) {
        	reverse[increment] = start;

            temp = start;
            start = subRoutes.get(Route.createRoute(start, set));
            set.remove(temp);
            if(start == null) {
            	System.out.println();
                break;
            }
            increment = increment + 1;
        }
        
        for (int i = 0; i < reverse.length; i++){
        	order[i] = reverse[reverse.length - i - 1];
        }
        return order;
	}
	
	private double getDistance(Set<Integer> set, int end, int previous, Map<Route, Double> costs) {
		set.remove(end);
        Route index = Route.createRoute(previous, set);
        double cost = costs.get(index);
        set.add(end);
        return cost;
    }
	
	private double getDistance(Set<Integer> set, int end, Map<Route, Double> costs) {
        Route index = Route.createRoute(end, set);
        double cost = costs.get(index);
        return cost;
    }
	
	public List<Set<Integer>> allCombinations(int number){
		List<Set<Integer>> allCombinations= new ArrayList<>();
		
		int input[] = new int[number];
		
        for(int i = 0; i < input.length; i++) {
            input[i] = i+1;
        }
        
		for (int i = 1; i <= number; i++){
			combinations(allCombinations, input, i, 0, new int[i]);
		}
		return allCombinations;
	}
	
	public void combinations(List<Set<Integer>> list, int input [], int rem, int position, int result[]){
		if (rem == 0){
			Set<Integer> set = new HashSet<>();
			for (int i = 0; i < result.length; i++){
				set.add(result[i]);
			}
			list.add(set);
			return;
		}
		
		for (int i = position; i <= input.length - rem; i++){
			result[result.length - rem] = input[i];
			combinations(list, input, rem-1, i+1, result);
		}
	}
}