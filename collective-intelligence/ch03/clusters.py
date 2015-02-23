# -*- coding: utf-8 -*-
from math import sqrt
from PIL import Image, ImageDraw

def readfile(filename):
    """
    Loads the dataset obtaining the name of the rows and colums and the data.
    """
    lines = [line for line in file(filename)]
    # Get the title of the colum from the first line
    colnames = lines[0].strip().split('\t')[1:]
    rownames = []
    data = []
    for line in lines[1:]:
        row = line.strip().split('\t')
        # First column in each row is rowname
        rownames.append(row[0])
        # The data of the row is from 1 to N
        data.append([float(x) for x in row[1:]])
    return rownames, colnames, data

def pearson(list_one, list_two):
    """ Returns the Pearson correlation coefficient. """
    # Sum
    sum_one = sum(list_one)
    sum_two = sum(list_two)
    # Sum of the squares
    squares_one = sum([pow(val, 2) for val in list_one])
    squares_two = sum([pow(val, 2) for val in list_two])
    # Sum of the products
    products = sum([list_one[i] * list_two[i] for i in range(len(list_one))])
    # Calculare the Pearson Score
    num = products - (sum_one * sum_two / len(list_one))
    deno = sqrt((squares_one - pow(sum_one, 2) / len(list_one)) * \
        (squares_two - pow(sum_two, 2) / len(list_one)))
    return 1.0 - num / deno if deno != 0 else 1.0

class BigCluster(object):
    """ Class fo clusters. """
    def __init__(self, vec, left=None, right=None, distance=0.0, clusid=None):
        self.left = left
        self.right = right
        self.vec = vec
        self.distance = distance
        self.clusid = clusid

def hcluster(rows, distance=pearson):
    """
    Hierarchical clustering algorithm.
    Searches for the two best matches and merges them into a single cluster.
    Repeated until only one cluster remains.
    """
    # Cache of distance calculations
    distances = {}
    current_clustid = -1
    # Initialy the clusters are the rows
    clust = [BigCluster(rows[i], clusid=i) for i in range(len(rows))]
    while len(clust) > 1:
        lowestpair = (0, 1)
        closest = distance(clust[0].vec, clust[1].vec)
        # Loop through every pair looking for the smallest distance
        for i in range(len(clust)):
            for j in range(i + 1, len(clust)):
                if (clust[i].clusid, clust[j].clusid) not in distances:
                    distances[(clust[i].clusid, clust[j].clusid)] = distance(
                        clust[i].vec, clust[j].vec)
                dist = distances[(clust[i].clusid, clust[j].clusid)]
                if dist < closest:
                    closest = dist
                    lowestpair = (i, j)
        # Calculate the average of the two clusters
        mergevec = [(clust[lowestpair[0]].vec[i] + clust[lowestpair[1]].vec[i])\
                      / 2.0 for i in range(len(clust[0].vec))]
        # Create a new cluster
        newcluster = BigCluster(mergevec, left=clust[lowestpair[0]],
                                right=clust[lowestpair[1]], distance=closest,
                                clusid=current_clustid)
        # Cluster ids that weren't in the original set are negative
        current_clustid -= 1
        del clust[lowestpair[1]]
        del clust[lowestpair[0]]
        clust.append(newcluster)
    return clust[0]

def printclust(clust, labels=None, n=0):
    """ Prints a cluster like a filesystem hierarchy. """
    # Indent to make a hierarchy layout
    for i in range(n):
        print ' ',
    if clust.clusid < 0:
        # Negative means that is branch
        print '-'
    else:
        # Positive -> endpoint
        if labels == None:
            print clust.clusid
        else:
            print labels[clust.clusid]
    # Print left and right branches
    if clust.left != None:
        printclust(clust.left, labels=labels, n=n+1)
    if clust.right != None:
        printclust(clust.right, labels=labels, n=n+1)

def getheight(clust):
    """ The height if the sum o the heights of its branches. """
    if clust.left == None and clust.right == None:
        return 1
    return getheight(clust.left) + getheight(clust.right)

def getdepth(clust):
    """
    The error depth of a node is the maximum possible error from its brances.
    """
    # The fistance of an endpoint is 0
    if clust.left == None and clust.right == None:
        return 0
    # The distance of a branch ist the greater of its two sides plus its
    # own distance
    return max(getdepth(clust.left), getdepth(clust.right)) + clust.distance

def draw_dendrogram(clust, labels, jpeg='clusters.jpg'):
    """ Draws the dendrogram of a cluster. """
    height = getheight(clust) * 20
    width = 1200
    depth = getdepth(clust) * 20
    # Width is fixed, so scale distances accordingly
    scaling = float(width - 150)/depth
    # Create a new image with a white background
    img = Image.new('RGB', (width, height), (255, 0, 0))
    draw = ImageDraw.Draw(img)
    draw.line((0, height / 2, 10, height / 2), fill=(255, 0, 0))
    # Draw the first node
    draw_node(draw, clust, 20, (height / 2), scaling, labels)
    img.save(jpeg, 'JPEG')

def draw_node(draw, clust, x, y, scaling, labels):
    """
    Takes a cluster and its location, then takes the heights of the child nodes,
    calculates where they should be and draws lines to them.
    """
    if clust.clusid < 0:
        h1 = getheight(clust.left) * 20
        h2 = getheight(clust.right) * 20
        top = y - (h1 + h2) / 2
        bottom = y + (h1 + h2) / 2
        # Line lenght
        lilen = clust.distance * scaling
        # Vertical line from this cluster to its children
        draw.line((x, top + h1 / 2, x, bottom - h2 / 2), fill=(255, 0, 0))
        # Horizontal line to left item
        draw.line((x, top + h1 / 2, x + lilen, top + h1 / 2), fill=(255, 0, 0))
        # Horizontal line to right item
        draw.line((x, bottom - h2 / 2, x + lilen, bottom - h2 / 2),
                  fill=(255, 0, 0))
        # Draw left and right nodes
        draw_node(draw, clust.left, x + lilen, top + h1 / 2, scaling, labels)
        draw_node(draw, clust.right, x + lilen, bottom - h2 / 2, scaling, labels)
    else:
        # This is an endpoint, draw item label
        draw.text((x + 5, y - 7), labels[clust.clusid], (0, 0, 0))
