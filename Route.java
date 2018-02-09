/*****************
 * Title: TravelingSalemanHeldKarp.java
 * Author: Tushar Roy
 * Date: 2016
 * Code Version: 3.0
 * Availability: https://github.com/mission-peace/interview/blob/master/src/com/interview/graph/TravelingSalesmanHeldKarp.java
 */

import java.util.*;

public class Route {
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route index = (Route) o;

        if (endVertex != index.endVertex) return false;
        return !(vertices != null ? !vertices.equals(index.vertices) : index.vertices != null);
    }

    @Override
    public int hashCode() {
        int result = endVertex;
        result = 31 * result + (vertices != null ? vertices.hashCode() : 0);
        return result;
    }
    
	int endVertex;
    Set<Integer> vertices;
    
    public static Route createRoute(int vertex, Set<Integer> vertices) {
    	Route i = new Route();
        i.endVertex = vertex;
        i.vertices = vertices;
        return i;
    }
}