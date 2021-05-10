package model.graphs;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.data_structures.Graph.Vertex;

public class ChipInGraph extends Graph<Client>{
	
	public Boolean add_vertice(Client client)
	{
		Vertex<Client> vertice_client = new Vertex<>(client);
		getVertices().add(vertice_client);
        return true;
	}
	
}
