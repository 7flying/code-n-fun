# -*- coding: utf-8 -*-
import feedparser
import re
from hashlib import md5

def get_word_counts(url):
	""" Returns title and a dictionary of word counts for an RSS feed. """
	data = feedparser.parse(url)
	word_count = {}
	for entry in data.entries:
		summary = entry.summary if 'summary' in entry else entry.description
		# Extract a list of words
		words = getwords(entry.title + ' ' + summary)
		for word in words:
			word_count.setdefault(word, 0)
			word_count[word] += 1
	if data.feed.get('title', None) != None:
		return data.feed.title, word_count
	else:
		print "\t Feed title is none"
		return md5(url.encode('utf-8')).hexdigest(), word_count

def getwords(html):
	""" Gets the words from an html doc. """
	# Remove html tags
	txt = re.compile(r'<[^>]+>').sub('', html)
	# Split words by all non-alpha characters
	words = re.compile(r'[^A-Z^a-z]+').split(txt)
	# return words converted to lowercase
	return [word.lower() for word in words if word != '']

if __name__ == '__main__':
	appe_count = {}
	word_counts = {}
	feedlist = []
	for feedurl in file('feedlist.txt'):
		print "\n[Processing...] " + feedurl
		feedlist.append(feedurl)
		title, wc = get_word_counts(feedurl)
		word_counts[title] = wc
		for word, count in wc.items():
			appe_count.setdefault(word, 0)
			if count > 1:
				appe_count[word] += 1
	wordlist = []
	for word, blogcount in appe_count.items():
		frac = float(blogcount) / len(feedlist)
		if frac > 0.1 and frac < 0.5:
			wordlist.append(word)

	# Create the text file
	out = file('blogdata.txt', 'w')
	out.write('Blog')
	for word in wordlist:
		out.write('\t%s' % word)
	out.write('\n')
	for blog, wc in word_counts.items():
		blog = blog.encode('ascii', 'ignore')
		out.write(blog)
		for word in wordlist:
			if word in wc:
				out.write('\t%d' % wc[word])
			else:
				out.write('\t0')
		out.write('\n')
