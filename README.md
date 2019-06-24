# Bigram Probabilities

Program to compute the bigram model (counts and probabilities) on the given corpus (HW2_F17_NLP6320-NLPCorpusTreebank2Parts-CorpusA.txt) under the following three (3) scenarios:
 1. No Smoothing
 2. Add-one Smoothing
 3. Good-Turing Discounting based Smoothing
 
- Note:
1. The “ . ” string sequence in the corpus is used to break it into sentences.
2. Each sentence is tokenized into words and the bigrams computed ONLY within a sentence.
3. Used whitespace (i.e. space, tab, and newline) to tokenize a sentence into words/tokens that are required for the bigram model.
4. Any type of word/token normalization (i.e. stem, lemmatize, lowercase, etc.) have not been performed.
5. Creation and matching of bigrams is exact and case-sensitive.

* Input Sentence: 
> The Fed chairman warned that the board 's decision is bad
