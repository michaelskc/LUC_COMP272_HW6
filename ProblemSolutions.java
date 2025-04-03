
/******************************************************************
 *
 *   Michael Simmons / COMP272-400C
 *
 *   This java file contains the problem solutions for the methods lastBoulder,
 *   showDuplicates, and pair methods. You should utilize the Java Collection
 *   Framework for these methods.
 *
 ********************************************************************/

import java.lang.reflect.Array;
import java.util.*;
import java.util.PriorityQueue;

public class ProblemSolutions {

    /**
     * Priority Queue (PQ) Game
     *
     * PQ1 Problem Statement:
     * -----------------------
     *
     * You are given an array of integers of boulders where boulders[i] is the
     * weight of the ith boulder.
     *
     * We are playing a game with the boulders. On each turn, we choose the heaviest
     * two boulders and smash them together. Suppose the heaviest two boulders have
     * weights x and y. The result of this smash is:
     *
     *    If x == y, both boulders are destroyed, and
     *    If x != y, the boulder of weight x is destroyed, and the boulder of
     *               weight y has new weight y - x.
     *
     * At the end of the game, there is at most one boulder left.
     *
     * Return the weight of the last remaining boulder. If there are no boulders
     * left, return 0.
     *
     *
     * Example 1:
     *
     * Input: boulders = [2,7,4,1,8,1]
     * Output: 1
     * Explanation:
     * We combine 7 and 8 to get 1 so the list converts to [2,4,1,1,1] then,
     * we combine 2 and 4 to get 2 so the list converts to [2,1,1,1] then,
     * we combine 2 and 1 to get 1 so the list converts to [1,1,1] then,
     * we combine 1 and 1 to get 0 so the list converts to [1] then that's the
     * value of the last stone.
     *
     * Example 2:
     *
     * Input: boulders = [1]
     * Output: 1
     *
     *
     *
     * RECOMMENDED APPROACH
     *
     * Initializing Priority Queue in reverse order, so that it gives
     * max element at the top. Taking top Elements and performing the
     * given operations in the question as long as 2 or more boulders;
     * returning the 0 if queue is empty else return pq.peek().
     */

    public static int lastBoulder(int[] boulders) {
        // Initialize a priority queue (in reverse order) so max element is at the top
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        // For each boulder in the input array:
        for (int b : boulders) {
            // Add the boulder to the max heap
            pq.offer(b);
        }
        // While there's more than 1 boulder left:
        while (pq.size() > 1) {
            int y = pq.poll(); // Heaviest
            int x = pq.poll(); // Second heaviest
            // If they aren't equal, add the difference back to the heap
            if (y != x) {
                pq.offer(y - x);
            }
        }
        // If the heap is empty, return 0; otherwise, return the last boulder
        if (pq.isEmpty()) {
            return 0;
        } else {
            // Return the last boulder using pq.peek()
            return pq.peek();
        }
    }


    /**
     * Method showDuplicates
     *
     * This method identifies duplicate strings in an array list. The list
     * is passed as an ArrayList<String> and the method returns an ArrayList<String>
     * containing only unique strings that appear more than once in the input list.
     * This returned array list is returned in sorted ascending order. Note that
     * this method should consider case (strings are case-sensitive).
     *
     * For example, if the input list was: "Lion", "Dog", "Cat", "Dog", "Horse", "Lion", "CAT"
     * the method would return an ArrayList<String> containing: "Dog", "Lion"
     *
     * @param  input an ArrayList<String>
     * @return       an ArrayList<String> containing only unique strings that appear
     *               more than once in the input list. They will be in ascending order.
     */

    public static ArrayList<String> showDuplicates(ArrayList<String> input) {


        // Initialize a HashMap to store each time a String is in the input list
        HashMap<String, Integer> duplicates = new HashMap<>();
        // Initialize a result ArrayList to return the result
        ArrayList<String> result = new ArrayList<>();

        // For each input string:
        for (String s : input) {
            // Add to the hashmap (or increment the value if key already exists)
            // I don't think I need to increment since we only care if it's >1, but will leave it in
            duplicates.put(s, duplicates.getOrDefault(s, 0) + 1);
        }
        // For each entry in the hashmap storing duplicates
        for (Map.Entry<String, Integer> entry : duplicates.entrySet()) {
            // If a duplicate is found:
            if (entry.getValue() > 1) {
                // Add the key to the result list
                result.add(entry.getKey());
            }
        }
        // Sort the result list
        Collections.sort(result);
        return result;
    }


    /**
     * Finds pairs in the input array that add up to k.
     *
     * @param input   Array of integers
     * @param k       The sum to find pairs for

     * @return an ArrayList<String> containing a list of strings. The ArrayList
     *        of strings needs to be ordered both within a pair, and
     *        between pairs in ascending order. E.g.,
     *
     *         - Ordering within a pair:
     *            A string is a pair in the format "(a, b)", where a and b are
     *            ordered lowest to highest, e.g., if a pair was the numbers
     *            6 and 3, then the string would be "(3, 6)", and NOT "(6, 3)".
     *         - Ordering between pairs:
     *            The ordering of strings of pairs should be sorted in lowest to
     *            highest pairs. E.g., if the following two string pairs within
     *            the returned ArraryList, "(3, 6)" and "(2, 7), they should be
     *            ordered in the ArrayList returned as "(2, 7)" and "(3, 6 )".
     *
     *         Example output:
     *         If the input array list was {2, 3, 3, 4, 5, 6, 7}, then the
     *         returned ArrayList<String> would be {"(2, 7)", "(3, 6)", "(4, 5)"}
     *
     *  HINT: Considering using any Java Collection Framework ADT that we have used
     *  to date, though HashSet. Consider using Java's "Collections.sort()" for final
     *  sort of ArrayList before returning so consistent answer. Utilize Oracle's
     *  Java Framework documentation in its use.
     */

    public static ArrayList<String> pair(int[] input, int k) {
        // Initialize a result ArrayList to return the result
        ArrayList<String> result = new ArrayList<>();
        // Check that the input list is valid and has enough values to create a pair
        if (input == null || input.length < 2) {
            return result; // Will just return an empty array
        }
        // Sort a copy of the input array.
        int[] arr = Arrays.copyOf(input, input.length);
        // Sort the array
        Arrays.sort(arr);
        // Create 2 pointers to search from front and back where they meet in the middle
        int left = 0;
        int right = arr.length - 1;
        // While they haven't met in the middle:
        while (left < right) {
            if (arr[left] + arr[right] == k) {
                // If the sum is equal to k, add the pair to the result in the format in the instructions
                result.add("(" + arr[left] + ", " + arr[right] + ")");
                // Store these values to skip duplicates, and move both pointers
                int leftVal = arr[left];
                int rightVal = arr[right];
                // Skip duplicate values for the left pointer.
                while (left < right && arr[left] == leftVal) {
                    left++;
                }
                // Skip duplicate values for the right pointer.
                while (left < right && arr[right] == rightVal) {
                    right--;
                }
            }
            // If the sum is less than k, then the left pointer should be moved since the list is already sorted
            else if (arr[left] + arr[right] < k) {
                left++;
            } else {
                // Otherwise, move the right pointer
                right--;
            }
        }
        // The result is already in ascending order by virtue of the two-pointer approach.
        return result;
    }
}