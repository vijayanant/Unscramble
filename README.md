# Unscrambler 

Given a jumbled word, of length 'n', how would you find all the valid words that can be formed from those letters? And what if I tell you that all the words  must also contain a specific letter?

#### Examples:
    1. get(god) = [dog, doggo, god, good] 
    2. get(dogs) = [dog, doggo, god, good, dogs, gods, goods]
    3. get(good, o) = [dog, doggo, god, good] 
    4. get(otm) = [mom, moo, moot, mot, motto, tom]
    5. get(otm, t) = [moot, mot, motto, tom]
    
## API

### Construction
The construction is very simple. Just pass the path to a dictionary file as constructor argument. The dictionary file should contain 1 word per line. 

    Unscramble ds = new Unscramble("dict-file");

You can add any additional word using `put()`-

    ds.put(newWord);

This way you could add words from a dictionary that are not in the required format -

    Unscramble ds = new Unscramble();
    for(String s: wordList) {
        ds.put(s);
    }


### Querying

Once you have constructed the object, querying is very simple.  

    Iterable<String> words = ds.get(letters, keyLetter);

`words` will now contain all the words in the dictionary that can be formed using the given letters and also containing the key letter.


In case you don't want to filter on key letter, there is another API just for that :

    Iterable<String> words = ds.get(letters);
    
## Running Time

###  Construction 
The construction time for the data structure is __`O(nm)`__. Where _`n`_ is the number of words in the dictionay and _`m`_ is average length of those words.

### Retrieval

#### TBD
<!--
The simplified version supports only letters from a-z. The retrieval time is, amazngly, __`O(m)`__, where `m` is the length of the input string. 
-->

## Memory Overhead
Technically, for the worst case data, the additional memory usage is huge.

     Size of each node = ~16 bytes (worst case)
     Number of Nodes = 2^26  (worst case)
     Total  = (2^16) * 2^4 bytes  = 2^20 bytes

But, in reality, a dictionary is no where near the worst case. For a dictionary with over 75,000 words, it takes __less than 2MB extra space.__    


[ref_unscramble_1]: {% post_url 2014-10-08-Unscramble-Part-I %} 
[ref_unscramble_2]: {% post_url 2014-10-22-Unscramble-Part-II %} 
[ref_trie]: http://en.wikipedia.org/wiki/Trie
[ref_binary_tree]: http://en.wikipedia.org/wiki/Binary_tree
[ref_github_unscramble]: https://github.com/vijayanant/Unscramble
    
