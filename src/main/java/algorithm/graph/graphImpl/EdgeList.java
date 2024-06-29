package algorithm.graph.graphImpl;


/**
 * <엣지 리스트(Edge List)/>
 * - <Strong>엣지를 중심으로</Strong> 배열을 이용하여 그래프를 표현하는 방식
 * - 1번노드에서 2번노드로 갈 수 있음을 {1,2}처럼 표현
 * - 1번노드에서 2번노드로 이동하는 가중치가 10일 때 {1,2,10}처럼 표현
 * <p>
 * <특징/>
 * -벨만포드, 크루스칼 알고리즘 등에 사용
 * -노드 중심 알고리즘에는 부적절
 */
public class EdgeList {
    /**
     * Graph Sample
     * <p>
     * 1  →  3  →  4  →  6
     * ↑     ↓
     * 2     5  ←  7  →  8
     */


    public static int[][] makeEdgeList() {
        String[] strs = "1,3/2,1/3,4/3,5/4,6/7,5/7,8"
                        .split("/");

        /*Edge List*/
        int[][] edgeList = new int[2][strs.length];

        for (int i = 0; i < strs.length; i++) {
            edgeList[0][i] = Integer.parseInt(strs[i].split(",")[0]);
            edgeList[1][i] = Integer.parseInt(strs[i].split(",")[1]);

            System.out.printf("%d,%d\n", edgeList[0][i], edgeList[1][i]);
        }
        return null;
    }

    public int[][] makeWeightEdgeList() {
        return null;
    }

    public static void main(String[] args) {
        makeEdgeList();
    }
}
