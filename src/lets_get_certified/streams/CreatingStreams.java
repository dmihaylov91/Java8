package lets_get_certified.streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class CreatingStreams {
    public static void main(String[] args) {
//        fromArray();
//        fromCollection();
//        creatingPrimitiveStreams();
//        // finite streams
//        // using Stream.of(varargs)
//        Stream<String> animals = Stream.of("cat", "dog", "sheep");
//        Stream<Integer> numbers = Stream.of(21,34,17);
//        
//        // converting a Collection to a stream
//        List<String> animalList = Arrays.asList("cat", "dog", "sheep");
//        // using stream() which is a default method in Collection interface
//        Stream<String> fromList = animalList.stream();
//        // creating a parallel stream
//        Stream<String> fromListParallel = animalList.parallelStream();
//        
//        // infinite streams
//        Stream<Double> randoms = Stream.generate(() -> Math.random());// Supplier
//        Stream<Integer> oddNumbers = Stream.iterate(1,          // seed
//                                                    n -> n+2);  // next number ('n' is 1 first off)
        // Java 9
//        Stream<Integer> oddNumbersJava9 = Stream.iterate(1,           // seed
//                                                         n -> n < 100,// Predicate to say when done
//                                                         n -> n+2);   // next number 
    }
    public static void creatingPrimitiveStreams(){
        int[] ia    = {1,2,3};
        double[] da = {1.1, 2.2, 3.3};
        long[] la   = {1L, 2L, 3L};
        
        IntStream iStream1      = Arrays.stream(ia);
        DoubleStream dStream1   = Arrays.stream(da);
        LongStream lStream1     = Arrays.stream(la);
        System.out.println(iStream1.count() + ", " + 
                dStream1.count() + ", " + lStream1.count());// 3, 3, 3
        
        IntStream iStream2       = IntStream.of(1, 2, 3);
        DoubleStream dStream2    = DoubleStream.of(1.1, 2.2, 3.3);
        LongStream lStream2      = LongStream.of(1L, 2L, 3L);
        System.out.println(iStream2.count() + ", " + 
                dStream2.count() + ", " + lStream2.count());// 3, 3, 3
        
    }
    public static void fromCollection(){
        
        List<String> animalList = Arrays.asList("cat", "dog", "sheep");       
        // using stream() which is a default method in Collection interface
        Stream<String> streamAnimals = animalList.stream();
        System.out.println("Number of elements: "+streamAnimals.count()); // 3
        
        // stream() is a default method in the Collection interface and therefore 
        // is inherited by all classes that implement Collection. Map is NOT one 
        // of those i.e. Map is not a Collection. To bridge between the two, we 
        // use the Map method entrySet() to return a Set view of the Map (Set 
        // IS-A Collection).
        Map<String, Integer> namesToAges = new HashMap<>();
        namesToAges.put("Mike", 22);namesToAges.put("Mary", 24);namesToAges.put("Alice", 31);
        System.out.println("Number of entries: "+
                namesToAges
                    .entrySet() // get a Set (i.e. Collection) view of the Map
                    .stream()   // stream() is a default method in Collection  
                    .count());  // 3

        
    }
    public static void fromArray(){
        
        Double[] numbers = {1.1, 2.2, 3.3};
        // Arrays.stream() creates a stream from the array 'numbers'.
        // The array is considered the source of the stream and while the
        // data is flowing through the stream, we have an opportunity to
        // operate on the data. 
        Stream<Double> stream1 = Arrays.stream(numbers);
        
        // lets perform an operation on the data
        // note that count() is a "terminal operation" - this means that
        // you cannot perform any more operations on the stream.
        long n = stream1.count();
        System.out.println("Number of elements: "+n);// 3
        
        // Re-creating the stream using Stream.of()
        //   static <T> Stream<T> of(T... values)
        Stream<Double> stream2 = Stream.of(numbers);
        System.out.println("Number of elements: "+stream2.count()); // 3
        
        Stream<String> stream3 = Stream.of("Austria", "New Zealand");
        System.out.println("Number of elements: "+stream3.count()); // 2
    }

    public static class CollectorsExamples {
        public static void main(String[] args) {
            //doPartitioning4();
            doGroupingBy3();
        }
        public static void doPartitioning4(){

            Stream<String> names = Stream.of("Alan", "Teresa", "Mike", "Alan", "Peter");
            Map<Boolean, Set<String>> map =
                    names.collect(
                        Collectors.partitioningBy(
                            s -> s.length() > 4,// predicate
                            Collectors.toSet()    )
                    );
            System.out.println(map);// {false=[Mike, Alan], true=[Teresa, Peter]}

        }
        public static void doPartitioning3(){

            Stream<String> names = Stream.of("Thomas", "Teresa", "Mike", "Alan", "Peter");
            Map<Boolean, List<String>> map =
                    names.collect(
                        // forcing an empty list
                        Collectors.partitioningBy(s -> s.length() > 10)
                    );
            System.out.println(map);// {false=[Thomas, Teresa, Mike, Alan, Peter], true=[]}

        }
        public static void doPartitioning2(){

            Stream<String> names = Stream.of("Thomas", "Teresa", "Mike", "Alan", "Peter");
            Map<Boolean, List<String>> map =
                    names.collect(
                        // pass in a Predicate
                        Collectors.partitioningBy(s -> s.length() > 4)
                    );
            System.out.println(map);// {false=[Mike, Alan], true=[Thomas, Teresa, Peter]}

        }
        public static void doPartitioning1(){

            Stream<String> names = Stream.of("Thomas", "Teresa", "Mike", "Alan", "Peter");
            Map<Boolean, List<String>> map =
                    names.collect(
                        // pass in a Predicate
                        Collectors.partitioningBy(s -> s.startsWith("T"))
                    );
            System.out.println(map);// {false=[Mike, Alan, Peter], true=[Thomas, Teresa]}

        }
        public static void doGroupingBy3(){

            Stream<String> names = Stream.of("Martin", "Peter", "Joe", "Tom", "Tom", "Ann", "Alan");
            Map<Integer, List<String>> map =
                    names.collect(
                        Collectors.groupingBy(
                                String::length,
                                TreeMap::new,       // map type Supplier
                                Collectors.toList())// downstream collector
                    );
            System.out.println(map);// {3=[Joe, Tom, Tom, Ann], 4=[Alan], 5=[Peter], 6=[Martin]}
            System.out.println(map.getClass());// class java.util.TreeMap

        }
        public static void doGroupingBy2(){

            Stream<String> names = Stream.of("Martin", "Peter", "Joe", "Tom", "Tom", "Ann", "Alan");
            Map<Integer, Set<String>> map =
                    names.collect(
                        Collectors.groupingBy(
                                String::length,     // key Function
                                Collectors.toSet()) // what to do with the values
                    );
            System.out.println(map);// {3=[Ann, Joe, Tom], 4=[Alan], 5=[Peter], 6=[Martin]}

        }
        public static void doGroupingBy1(){

            Stream<String> names = Stream.of("Martin", "Peter", "Joe", "Tom", "Tom", "Ann", "Alan");
            Map<Integer, List<String>> map =
                    names.collect(
                        // passing in a Function that determines the
                        // key in the Map
                        Collectors.groupingBy(String::length) // s -> s.length()
                    );
            System.out.println(map);// {3=[Joe, Tom, Tom, Ann], 4=[Alan], 5=[Peter], 6=[Martin]}

        }
        public static void doCollectToMap3(){

            // The maps returned are HashMaps but this is not guaranteed. What if we wanted
            // a TreeMap implementation so our keys would be sorted. The last argument
            // caters for this.
            TreeMap<String, Integer> map =
                    Stream.of("cake", "biscuits", "apple tart", "cake")
                    .collect(
                        Collectors.toMap(s -> s,            // key is the String
                                         s -> s.length(),   // value is the length of the String
                                        (len1, len2) -> len1 +len2, // what to do if we have
                                                                    // duplicate keys
                                                                    //    - add the *values*
                                        () -> new TreeMap<>() ));// TreeMap::new works
            System.out.println(map);// {apple tart=10, biscuits=8, cake=8} Note: cake maps to 8
            System.out.println(map.getClass());// class java.util.TreeMap

        }
        public static void doCollectToMap2(){

            // We want a map: number of characters in dessert name -> dessert name
            // However, 2 of the desserts have the same length (cake and tart) and as
            // length is our key and we can't have duplicate keys, this leads to an
            // exception as Java does not know what to do...
            //    IllegalStateException: Duplicate key 4 (attempted merging values cake and tart)
            // To get around this, we can supply a merge function, whereby we append the
            // colliding keys values together.
            Map<Integer, String> map =
                    Stream.of("cake", "biscuits", "tart")
                    .collect(
                        Collectors.toMap(s -> s.length(),// key is the length
                                         s -> s,         // value is the String
                                         (s1, s2) -> s1 + "," + s2)// Merge function - what to
                                                                   // do if we have duplicate keys
                                                                   //   - append the values
                    );
            System.out.println(map);// {4=cake,tart, 8=biscuits}

        }
        public static void doCollectToMap1(){

            // We want a map: dessert name -> number of characters in dessert name
            // Output:
            //   {biscuits=8, cake=4, apple tart=10}
            Map<String, Integer> map =
                    Stream.of("cake", "biscuits", "apple tart")
                    .collect(
                        Collectors.toMap(s -> s,         // Function for the key
                                         s -> s.length())// Function for the value
                    );
            System.out.println(map);

        }
        public static void doJoining(){

            String s = Stream.of("cake", "biscuits", "apple tart")
                            .collect(Collectors.joining(", "));
            System.out.println(s);  // cake, biscuits, apple tart

        }

        public static void doAveragingInt(){

            Double avg = Stream.of("cake", "biscuits", "apple tart")
                            // averagingInt(ToIntFunction) functional method is:
                            //      int applyAsInt(T value);
                            .collect(Collectors.averagingInt(s -> s.length()));
            System.out.println(avg);  // 7.333333333333333

        }
    }
}
