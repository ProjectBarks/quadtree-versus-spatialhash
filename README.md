# Quadtree Versus SpatialHash

This is a utility program to test whether Spatial Hashing or Quad Trees have a faster overall performance and helping a programmer make a choice between the two algorithms. This includes a result visualizer, timing logger, and other similar tools.
<img src="https://i.imgur.com/OCMm7ZI.png" />

### How To Use

There are three key components to getting this program to run and getting to benchmarking of the two algorithms. 

1. Create the data by executing [`DataGeneration.java`](/src/org/dasd/ee/DataGeneration.java)
   - First it will ask for the *maximum samples* for the maximum amount of entities to test. It will then generate tests with entities from 1 to the max.
   - Then it will ask how many *tests per sample* giving you a more accurate representation per quantity  of entities as you increase. 
   - *Test area* is the size of the square of the entities to be placed on.
   - *Object size* is the size of each entity
   - Finally a test file will be made that can be shared and passed around to know what data was tested against. It is also used to allow resuming of tests
2.  Benchmark by running [`Benchmark.java`](/src/org/dasd/ee/Benchmark.java)
   - After choosing what algorithm to test it will export the data required for reviewing the results.
   - This program is single threaded so it is important to run this it a computer with a powerful process
   - The results will be exported in a similarly labeled `.CSV`
3. Review results with [`ReviewResults.java`](/src/org/dasd/ee/ReviewResults.java)
   - This is not required to properly test data but provides insight into how the algorithm works
   - Once you select the test you can view any result of any test.

### My own Findings

If you are interested in seeing what this program yielded and how it functioned please read my paper [here](https://brandonbarker.me/downloads/quadtree_spatialhash.pdf). The paper goes over the time complexities of both algorithms, limitations, advantages, and situations when to use each algorithm. If you plan to use the information from my paper I would love to hear from you!  

### Libraries and Tools Used

- [GSON](https://github.com/google/gson) - For serialization of test data
- [JGoodies Form Layout](http://www.jgoodies.com/freeware/libraries/forms/) - Review result layout file
- [Lombok](https://projectlombok.org/) - Easy setter and getter generation
