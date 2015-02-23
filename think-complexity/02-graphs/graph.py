# -*- coding: utf-8 -*-

class Vertex(object):
    """A Vertex is a node in a graph."""
    def __init__(self, label=''):
        self.label = label

    def __repr__(self):
        return 'Vertex(%s)' % repr(self.label)

    # __repr__ returns a string representation of an object
    __str__ = __repr__


class Edge(tuple):
    """An edge contains two vertices."""
    def __new__(cls, e1, e2):
        return tuple.__new__(cls, (e1, e2))

    def __repr__(self):
        return 'Edge(%s, %s)' % (repr(self[0]), repr(self[1]))

    __str__ = __repr__


class Graph(dict):
    """
    A Graph is a dictionary of dictionaries.
    Map from vertex to inner dict that maps from other vertices to edges.
    Given the vertices a and b, graph[a][b] maps to the edge that connects a->b,
    if it exists.
    """
    def __init__(self, vs=[], es=[]):
        """ Creates a new Graph. vs: list of vertices, es: list of edges."""
        super(Graph, self).__init__()
        for vert in vs:
            self.add_vertex(vert)
        for edge in es:
            self.add_edge(edge)

    def add_vertex(self, vert):
        """ Adds a vertex to the graph."""
        self[vert] = {}

    def add_edge(self, edge):
        """ Adds an edge. The edge is added in both directions."""
        one, two = edge
        self[one][two] = edge
        self[two][one] = edge

    def get_edge(self, vert1, vert2):
        """ Returns the edge between the vertices if exists, None otherwise."""
        try:
            return self[vert1][vert2]
        except KeyError:
            return None

    def remove_edge(self, edge):
        """ Removes all references to the edge from the graph."""
        pass

    def vertices(self):
        """ Returns a list of the vertices in a graph."""
        pass

    def edges(self):
        """ Returns a list of the edges in a graph."""
        pass

    def out_vertices(self, vertex):
        """ Returns a list of the adjacent vertices."""
        pass

    def out_edges(self, vertex):
        """ Returns a list of edges connected to the given vertex."""
        pass

    def add_all_edges(self):
        """ ? """
        pass


def main():
    v = Vertex('v')
    print v
    w = Vertex('w')
    print w
    edge = Edge(v, w)
    print edge
    graph = Graph([v, w], [edge])
    print graph
    print graph.get_edge(Vertex('v'), Vertex('w'))
    # Must return None
    print graph.get_edge('v', 'w')

if __name__ == '__main__':
    main()
        