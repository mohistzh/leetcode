package com.mohistzh.leetcode.dfs;

import java.util.*;

/**
 Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.

 Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some email that is common to both accounts. Note that even if two accounts have the same name,
 they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

 After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.

 Example 1:
 Input:
 accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"], ["John", "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
 Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  ["John", "johnnybravo@mail.com"], ["Mary", "mary@mail.com"]]

 Explanation:
 The first and third John's are the same person as they have the common email "johnsmith@mail.com".
 The second John and Mary are different people as none of their email addresses are used by other accounts.
 We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'],
 ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
 Note:

 The length of accounts will be in the range [1, 1000].
 The length of accounts[i] will be in the range [1, 10].
 The length of accounts[i][j] will be in the range [1, 30].

 https://leetcode.com/problems/accounts-merge/
 * @Author Z
 * @Date 2020/9/28
 **/
public class AccountsMerge {
    /**
     * Intuition
     *
     * Draw an edge between two emails if they occur in the same account. The problem comes down to finding connected components of this graph.
     *
     * Algorithm
     *
     * For each account, draw the edge from the first email to all other emails. Additionally, we'll remember a map from emails to names on the side.
     * After finding each connected component using a depth-first search, we'll add that to our answer.
     *
     * @param input
     * @return
     */
    public static List<List<String>> mergeAccounts(String[][] input) {
        /**
         * 0. elements with the same email are connected together
         * 1. find the same email
         * 2. merge them into a new list
         */

        Map<String, List<String>> graph = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        /**
         * build a graph
         */
        for (String[] account : input) {
            String name = "";
            for (String email : account) {
                if ("".equals(name)) {
                    // remind the name
                    name = email;
                    continue;
                }
                /**
                 * use an certainly un-empty (the second element of the account list shouldn't be empty) element as our component to build pairs
                 */
                String component = account[1];
                graph.computeIfAbsent(email, s -> new ArrayList<String>()).add(component);
                graph.computeIfAbsent(component, s -> new ArrayList<String>()).add(email);
                emailToName.put(email, name);
            }
        }

        List<List<String>> result = new ArrayList<>();
        Set<String> memo = new HashSet<>();

        /**
         * DFS
         */
        for (String email : graph.keySet()) {
            if (!memo.contains(email)) {
                memo.add(email);
                Stack<String> stack = new Stack<>();
                stack.push(email);
                List<String> components = new ArrayList<>();
                while (!stack.isEmpty()) {
                    String node = stack.pop();
                    components.add(node);
                    List<String> edges = graph.get(node);
                    for (String neighbor : edges) {
                        if (!memo.contains(neighbor)) {
                            memo.add(neighbor);
                            stack.push(neighbor);
                        }
                    }
                }
                Collections.sort(components);
                components.add(0, emailToName.get(email));
                result.add(components);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        String[][] accounts = {
                {"John", "johnsmith@mail.com", "john00@mail.com"},
                {"John", "johnnybravo@mail.com"},
                {"John", "johnsmith@mail.com", "john_newyork@mail.com"},
                {"Mary", "mary@mail.com"}
        };
        List<List<String>> result = mergeAccounts(accounts);
        System.out.println(result);

    }

}
