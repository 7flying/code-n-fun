# -*- coding: utf-8 -*-
from math import sqrt

critics = {'Lisa Rose': {'Lady in the Water': 2.5,
                         'Snakes on a Plane': 3.5,
                         'Just My Luck': 3.0,
                         'Superman Returns': 3.5,
                         'The Night Listener': 3.0,
                         'You, Me and Dupree': 2.5},
           'Gene Seymour': {'Lady in the Water': 3.0,
                            'Snakes on a Plane': 3.5,
                            'Just My Luck': 1.5,
                            'Superman Returns': 5.0,
                            'The Night Listener': 3.0,
                            'You, Me and Dupree': 3.5},
           'Michael Philips': {'Lady in the Water': 2.5,
                               'Snakes on a Plane': 3.0,
                               'Superman Returns': 3.5,
                               'The Night Listener': 4.0},
           'Claudia Puig': {'Snakes on a Plane': 3.5,
                            'Just My Luck': 3.0,
                            'Superman Returns': 4.0,
                            'The Night Listener': 4.5,
                            'You, Me and Dupree': 2.5},
           'Mick Lasalle': {'Lady in the Water': 3.0,
                            'Snakes on a Plane': 4.0,
                            'Just My Luck': 2.0,
                            'Superman Returns': 3.0,
                            'The Night Listener': 3.0,
                            'You, Me and Dupree': 2.0},
           'Jack Matthews': {'Lady in the Water': 3.0,
                             'Snakes on a Plane': 4.0,
                             'Superman Returns': 5.0,
                             'The Night Listener': 3.0,
                             'You, Me and Dupree': 3.5},
           'Toby': {'Snakes on a Plane': 4.5,
                    'Superman Returns': 4.0,
                    'You, Me and Dupree': 1.0}}

def _get_shared_items(prefs, person1, person2):
    """ Returns the shared items among person1 and person2. """
    shared_items = {}
    for item in prefs[person1]:
        if item in prefs[person2]:
            shared_items[item] = 1
    return shared_items

def sim_distance(prefs, person1, person2):
    """ Returns a distace-based similarity score for person1 and person2."""
    # Get the list of shared items
    shared_items = _get_shared_items(prefs, person1, person2)
    if len(shared_items) == 0:
        return 0
    # Add up he squares of all the differences
    sum_of_squares = sum([pow(prefs[person1][item] - prefs[person2][item], 2)
                          for item in shared_items])
    # Add 1 to the function to avoid a division-by-zero error and invert it
    return 1 / (1 + sqrt(sum_of_squares))

def sim_pearson(prefs, person1, person2):
    """ Returns the Pearson correlation coefficient for person1 and person2. """
    shared_items = _get_shared_items(prefs, person1, person2)
    # Get the number of elements
    number = len(shared_items)
    # If they have no ratings in common, return 0
    if number == 0:
        return 0
    # Add up all the preferences
    sum1 = sum([prefs[person1][item] for item in shared_items])
    sum2 = sum([prefs[person2][item] for item in shared_items])
    # Sum up the squares
    squares1 = sum([pow(prefs[person1][item], 2) for item in shared_items])
    squares2 = sum([pow(prefs[person2][item], 2) for item in shared_items])
    # Sum up the products
    product_sum = sum([prefs[person1][item] * prefs[person2][item] \
                        for item in shared_items])
    # Calculate the Pearson score
    num = product_sum - (sum1 * sum2 / number)
    denomi = sqrt((squares1 - pow(sum1, 2) / number) * (squares2 - pow(sum2, 2) \
                    / number))
    return num / denomi

def top_matches(prefs, person, number=5, similarity=sim_pearson):
    """ Returns the best matches for person from the prefs dict. """
    scores = [(similarity(prefs, person, other), other) \
                for other in prefs if other != person]
    # Set highest scores at the top
    scores.sort()
    scores.reverse()
    return scores[:number]

def get_recommendations(prefs, person, similarity=sim_pearson):
    """
    Gets recommendations for a person by using a weighted average of every
    other user's rankings.
    """
    totals = {}
    sim_sums = {}
    for other in prefs:
        # Don't compare equals
        if other != person:
            sim = similarity(prefs, person, other)
            # Ignore bad scores
            if sim > 0:
                for item in prefs[other]:
                    # Score movies which haven't been seen
                    if item not in prefs[person] or prefs[person][item] == 0:
                        # Similarity * score
                        totals.setdefault(item, 0)
                        totals[item] += prefs[other][item] * sim
                        # Sum of similarities
                        sim_sums.setdefault(item, 0)
                        sim_sums[item] += sim
    # Create the normalized list
    rankings = [(total / sim_sums[item], item) for item, total in totals.items()]
    # Set highest scores at the top
    rankings.sort()
    rankings.reverse()
    return rankings

def transform_prefs(prefs):
    """ Transforms the preferences from person-item to item-person. """
    result = {}
    for person in prefs:
        for item in prefs[person]:
            result.setdefault(item, {})
            # Flip the item and person
            result[item][person] = prefs[person][item]
    return result

def get_sim_items(prefs, number=10, debug=False):
    """
    Returns a dictionary of items showing other items they are most
    similar to.
    """
    result = {}
    # Invert the preference matrix to be item-centric
    item_prefs = transform_prefs(prefs)
    count = 0
    for item in item_prefs:
        count += 1
        if debug:
            if count % 100 == 0:
                print "%d / %d" % (count, len(item_prefs))
        # Get the most similar items to the current item
        result[item] = top_matches(item_prefs, item, number=number,
                                   similarity=sim_distance)
    return result

def get_recommended_items(prefs, item_match, user):
    """ """
    user_ratings = prefs[user]
    scores = {}
    total_sim = {}
    # Loop over items rated by this user
    for item, rating in user_ratings.items():
        # Loop over items similar to this one
        for similarity, item2 in item_match[item]:
            # If the user hasn't rated this item
            if item2 not in user_ratings:
                # Weighted sum of rating times similarity
                scores.setdefault(item2, 0)
                scores[item2] += similarity * rating
                # Sum of al similarities
                total_sim.setdefault(item2, 0)
                total_sim[item2] += similarity
    # Divide each score by the total weighting to get an average
    rankings = [(score / total_sim[item], item) for item, score in scores.items()]
    # Set the highest scores at the top
    rankings.sort()
    rankings.reverse()
    return rankings
