package model.graphs;

import com.jwetherell.algorithms.data_structures.Graph;

public class ChipInGraph extends Graph<Client>{
	
	/**
	 * adds the client to the graph as vertices and links them when there 
	 * are more than one client 
	 *
	 * @param  client the new client that is to be added to graph
	 * @param  vertex<Client> the vertex of the client to be linked to
	 * @return boolean true if addition of vertex was a successs and false otherwise
	 */
	public Boolean addClient(Client client, Vertex<Client> vertice_client1,int graphSize)
	{
		Vertex<Client> vertice_client = new Vertex<>(client);
		if (graphSize == 0) {
			getVertices().add(vertice_client);
			return true;			
		}else if (graphSize > 0){
			getVertices().add(vertice_client);
			add_edges(vertice_client, vertice_client1);
	        return true;
		}
		return false;
	}
	
	
	/**
	 * adds edges to the graph 
	 *
	 * @param  client the new client that is to be added to graph
	 * @param  vertex<Client> the vertex of the client to be linked to
	 * @return nothing
	 */
	private void add_edges( Vertex<Client> vertex_client1, Vertex<Client> vertex_client2)
	{
		//check if  vertex have edges
		Edge<Client> edge1 = vertex_client1.getEdge(vertex_client2);
		Edge<Client> edge2 = vertex_client2.getEdge(vertex_client1);
		if(edge1==null||edge2==null)
		{
			//then create new edges
			Edge<Client> new_edge1 = new Edge<>(0,vertex_client1,vertex_client2);
			Edge<Client> new_edge2 = new Edge<>(0,vertex_client1,vertex_client2);
			vertex_client1.addEdge(new_edge1); 
			vertex_client2.addEdge(new_edge2);
			
			getEdges().add(new_edge1);
			getEdges().add(new_edge1);
		}
		
	}
	
	
	/**
	 *	removes client from the graph
	 *
	 * @param  client the client to remoce
	 * @return boolean, true if removed and false if not
	 */
	public Boolean removem_vertex(Client client) {
	    Vertex<Client> find = null;
	    for (Vertex<Client>  vertex: getVertices()) {
	        if ((vertex.getValue().compareTo(client)) == 0) {
	            find = vertex;
	            for (Edge<Client> connection: vertex.getEdges()) {
	            	Vertex<Client>  con_vertex = connection.getToVertex();
	                Edge<Client> edge = con_vertex.getEdge(vertex);

	                con_vertex.getEdges().remove(edge);
	                getEdges().remove(edge);
	            }
	            getEdges().removeAll(vertex.getEdges());
	            vertex.getEdges().clear();
	            getVertices().remove(vertex);
	        }
	    }
	    if (find == null) {
	        return false;
	    } {
	        return true;
	    }
	}
	
}
