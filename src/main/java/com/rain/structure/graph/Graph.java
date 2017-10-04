package com.rain.structure.graph;

import java.util.*;

/**
 * 关于图的一点的知识
 *  1.存储方式既有基于矩阵的,也有基于邻接表的.基于矩阵的适用于边稠密的图.领接表适用于边稀疏的图
 *  二者的时间复杂度可以分析分析
 *  2.图的遍历方式有两种
 *      广度优先遍历(树的层次遍历)
 *      深度优先遍历(树的先序遍历)
 */
public class Graph {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int row = Integer.valueOf(scanner.nextLine());
        Map<Integer, List<Integer>> map = new LinkedHashMap<>();
        int i = 0;
        int size = row;
        while (row -- > 0) {
            i ++;
            String[] params = scanner.nextLine().split("\\s");
            List<Integer> arcs = new ArrayList<>();
            for (String s : params) {
                arcs.add(Integer.valueOf(s));
            }
            map.put(i, arcs);
        }

        AlGraph alGraph = new AlGraph();
        alGraph.createAlGraph(size, map);
        alGraph.toString();

        scanner.close();
    }
}

/**
 * 采用邻接矩阵存储图
 *  邻接矩阵用来存放图中顶点之间的每一条边
 *  同时使用一个数组存放顶点
 */
class MGraph {
    private char[] vertexType;
    private int[][] edge;
    public MGraph() {
        this.vertexType = null;
        this.edge = null;
    }
    public void createMGraph(char[] vertexType, int[][] edge) {
        this.vertexType = Arrays.copyOf(vertexType, vertexType.length);
        this.edge = new int[edge.length][edge.length];
        for (int i = 0;i < edge.length;i++) {
            for (int j = 0;j < edge[i].length;j++) {
                this.edge[i][j] = edge[i][j];
            }
        }
    }

    @Override
    public String toString() {
        System.out.println("该图包含顶点的个数是: " + this.vertexType.length);
        System.out.println("该图包含的边的信息是: ");
        for (int i = 0;i < this.edge.length;i++) {
            for (int j = 0;j < this.edge[i].length;j++) {
                System.out.print(this.edge[i][j] + "\t");
            }
            System.out.println();
        }
        return null;
    }
}

/**
 * 采用邻接表存储图
 *  结构类似于Hash中的拉链法避免冲突
 */
class AlGraph {
    private VertexNode[] vertexNodes;
    public AlGraph() {
        this.vertexNodes = null;
    }

    // 顶点
    private static class VertexNode {
        private int data;
        private ArcNode arcNode;
        public VertexNode(int data) {
            this.data = data;
            arcNode = null;
        }
    }

    // 链表节点
    private static class ArcNode {
        private int data;
        private ArcNode next;
        public ArcNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public void createAlGraph(int n, Map<Integer, List<Integer>> map) {
        this.vertexNodes = new VertexNode[n + 1];
        for (int i = 1;i <= n;i++) {
            vertexNodes[i] = new VertexNode(i);
        }
        for (int i = 1;i <= n;i++) {

            List<Integer> arcs = map.get(i);
            for (Integer arc : arcs) {
                // 采用头插法将节点倒序插入
                ArcNode arcNode = new ArcNode(arc);
                if (vertexNodes[i].arcNode != null) {
                    ArcNode first = vertexNodes[i].arcNode;
                    vertexNodes[i].arcNode = arcNode;
                    arcNode.next = first;
                } else {
                    vertexNodes[i].arcNode = arcNode;
                }
            }
        }
    }

    @Override
    public String toString() {
        for (int i = 1;i < this.vertexNodes.length;i++) {
            if (vertexNodes[i] != null) {
                System.out.print(vertexNodes[i].data + "-->");
            }
            ArcNode iterator = vertexNodes[i].arcNode;
            while (iterator != null) {
                System.out.print(iterator.data);
                iterator = iterator.next;
                if (iterator != null) {
                    System.out.print("-->");
                }
            }
            System.out.println();
        }
        return null;
    }
}