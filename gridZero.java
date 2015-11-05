import java.util.Arrays;
import java.util.Stack;

class GaussianElimination {
	int[][] matrix;
	int df = -1;
	int[] ans;
	Stack<Integer> stack = new Stack<Integer>();
	int R, C;
	int minSum;
	int curSum;

	public GaussianElimination(int[][] matrix) {
		this.matrix = matrix;
		R = matrix.length;
		C = matrix[0].length - 1;
		ans = new int[R];
	}

	void swapRow(int r1, int r2) {
		for (int i = 0; i <= C; i++) {
			int tmp = matrix[r1][i];
			matrix[r1][i] = matrix[r2][i];
			matrix[r2][i] = tmp;
		}
	}

	void swapCol(int c1, int c2) {
		for (int i = 0; i < R; i++) {
			int tmp = matrix[i][c1];
			matrix[i][c1] = matrix[i][c2];
			matrix[i][c2] = tmp;
		}
	}

	public int minAnswer() {
		if (df == -1)
			return -1;
		minSum = 0;
		curSum = 0;
		for (int i = R - df; i < R; i++)
			ans[i] = 0;
		for (int i = 0; i < R - df; i++) {
			minSum += matrix[i][C];
		}
		if (df == 0)
			return minSum;

		for (int i = 0; i < R - df; i++) {
			ans[i] = 0;
		}
		for (int t = 0; t < 1 << df; t++) {
			int n = t, m = R - 1;
			curSum = 0;
			while (n != 0) {
				ans[m--] = n & 1;
				curSum += n & 1;
				n >>>= 1;
			}
			if (curSum >= minSum)
				continue;
			for (int i = 0; i < R - df; i++) {
				ans[i] = matrix[i][C];
				for (int j = R - df; j < C; j++) {
					if (ans[j] == 1 && matrix[i][j] == 1)
						ans[i] = ans[i] == 1 ? 0 : 1;
				}
				curSum += ans[i];
				if (curSum >= minSum)
					break;
			}
			if (curSum < minSum)
				minSum = curSum;
			System.out.println(curSum + ": " + Arrays.toString(ans));
		}

		// for (int i = 1; i <= (minSum - 1 < df ? minSum - 1 : df); i++)
		// getAnswer(i);
		return minSum;
	}

	void getAnswer(int m) {
		if (m == 0) {
			int sum = stack.size();
			for (int i = R - df; i < R; i++)
				ans[i] = 0;
			for (int i = 0; i < R - df; i++) {
				ans[i] = matrix[i][C];
				for (int j : stack) {
					ans[j] = 1;
					if (matrix[i][j] == 1)
						ans[i] = ans[i] == 1 ? 0 : 1;
				}
				sum += ans[i];
				if (sum >= minSum)
					return;
			}
			// System.out.println(sum + ":" + Arrays.toString(ans));
			if (sum < minSum)
				minSum = sum;
		} else {
			int i = stack.isEmpty() ? R - df : (stack.peek() + 1);
			for (; i < C; i++) {
				stack.push(i);
				getAnswer(m - 1);
				stack.pop();
			}
		}
	}

	public void GE0_1() {
		int mod = 2;
		int i = 0, c = 0;
		for (; i < R && c < C; i++, c++) {
			if (matrix[i][c] == 0) {
				int maxRow = i;
				for (int j = i + 1; j < R; j++) {
					if (matrix[j][c] != 0) {
						maxRow = j;
						break;
					}
				}
				if (maxRow != i) {
					swapRow(i, maxRow);
				}
			}
			if (matrix[i][c] == 0) {
				i--;
				continue;
			}
			for (int j = i + 1; j < R; j++)
				if (matrix[j][c] == 1)
					for (int k = c; k <= C; k++) {
						matrix[j][k] -= matrix[i][k];
						matrix[j][k] = (matrix[j][k] + mod) % mod;
					}
		}
		for (int j = i; j < R; j++) {
			if (matrix[j][C] != 0) {
				df = -1;
				return;
			}
		}
		int last = i - 1;
		df = R - i;
		for (i = 0; i < R && i < C; i++) {
			if (matrix[i][i] == 0) {
				int j = i + 1;
				for (; j < C; j++)
					if (matrix[i][j] != 0)
						break;
				if (j < C) {
					swapCol(i, j);
				}

			}
		}
		for (i = last; i > 0; i--) {
			for (int j = 0; j < i; j++)
				if (matrix[j][i] != 0)
					for (int k = i; k <= C; k++) {
						matrix[j][k] -= matrix[i][k];
						matrix[j][k] = (matrix[j][k] + mod) % mod;
					}
		}
	}

	public static int[][] getArgumentedMatrix(int[][] matrix) {
		int N = matrix.length;
		int numOfLights = N * N;
		int[][] mt = new int[numOfLights][];
		for (int i = 0; i < numOfLights; i++) {
			mt[i] = new int[numOfLights + 1];
		}
		for (int i = 0; i < numOfLights; i++) {
			for (int j = 0; j < numOfLights; j++) {
				mt[i][j] = (i / N == j / N) || (i % N == j % N) ? 1 : 0;
			}
			mt[i][numOfLights] = matrix[i / N][i % N];
		}
		return mt;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public int degreeOfFreedom() {
		return df;
	}

	@Override
	public String toString() {
		return String.format("%d: %s", df, Arrays.deepToString(matrix));
	}

}

/*
 * I started with Gaussian Elimination, then I found i too time costly and give
 * it up.
 */
public class gridZero {
	static int bitSum(int[] P) {
		int n = 0;
		for (int p : P)
			n += Integer.bitCount(p);
		return n;
	}

	static void print(int[] P) {
		int N = P.length;
		for (int i = 0; i < N; i++) {
			int n = P[i];
			int count = N;
			while (n != 0) {
				System.out.print(n & 1);
				n >>>= 1;
				count--;
			}
			while (count-- > 0)
				System.out.print(0);
			System.out.println();
		}
	}

	/*
	 * Get the answer of a N*N matrix M when N is even
	 * 
	 * When N is even, for each light(i, j) that is on, press all the light in row
	 * i and col j would only turn light(i, j) off without effecting other light.
	 * 
	 * Therefore, for each m in M, there is always a way p in P{N*N} that can
	 * solve the matrix. In F: P -> M, there are 2^N possible values in Range M,
	 * each has a p in P that F(p) = M; and there are at most 2^N values in P, its
	 * easy to prove that F: P->M is one-to-one mapping. So for each m in M, there
	 * is one and only one p in P that F(p) = m.
	 */
	static int[] getAnswer(int[][] matrix) {
		int N = matrix.length;
		int[] P = new int[N];
		int col = 1;
		for (int j = 0; j < N; j++) {
			for (int i = 0; i < N; i++) {
				if (matrix[i][j] != 0) {
					P[i] = ~P[i];
					for (int k = 0; k < N; k++)
						P[k] ^= col;
					P[i] ^= col;
				}
			}
			col <<= 1;
		}
		for (int i = 0; i < N; i++)
			P[i] &= (1 << N) - 1;

		//print(P);
		return P;
	}

	/*
	 * When N = 2n, there is only one answer, which is the minimum one.
	 * 
	 * When N = 2n+1, it can be easily proved that for each light(i,j) where i ==
	 * N || j == N, (i,j) != (N,N) (bottom and right edge excluding the bottom
	 * right one), there are some lights(i',j') in M[1...2n][1...2n] and M[2n+1][2n+1]
	 * that pressing them gives the same effect with pressing light(i,j). Thus, if
	 * there is a solution, there will be another solution of pressing zero or any
	 * lights on the right and bottom edge without the bottom right angle.
	 */
	static int getMin(int[][] matrix) {
		int N = matrix.length;
		if ((N & 1) == 0)
			return bitSum(getAnswer(matrix));
		else {
			int n = 0;
			// if (N,N) is on, press it to turn it off
			if (matrix[N - 1][N - 1] == 1) {
				for (int i = 0; i < N - 1; i++)
					matrix[i][N - 1] ^= 1;
				for (int j = 0; j < N; j++)
					matrix[N - 1][j] ^= 1;
				n++;
			}
			// find the solution for matrix[1...N-1][1...N-1], since N-1 is even, it has
			// one and only one solution
			//
			// if it is not the solution for M[1...N][1...N], then there is no answers
			// because this is the only solution for matrix[1-N][1-N] without pressing
			// any light on edge
			int[][] submatrix = new int[N - 1][];
			for (int i = 0; i < N - 1; i++) {
				submatrix[i] = new int[N - 1];
				for (int j = 0; j < N - 1; j++)
					submatrix[i][j] = matrix[i][j];
			}
			int[] P = getAnswer(submatrix);
			for (int i = 0; i < N - 1; i++) {
				int bitCount = Integer.bitCount(P[i]);
				if (((bitCount + matrix[i][N - 1]) & 1) != 0)
					return -1;
			}
			int col = 1;
			for (int j = 0; j < N - 1; j++) {
				int bitCount = 0;
				for (int i = 0; i < N - 1; i++)
					bitCount += (P[i] & col) == 0 ? 0 : 1;
				if (((bitCount + matrix[N - 1][j]) & 1) != 0)
					return -1;
				col <<= 1;
			}
			int ret = bitSum(P) + n;
			// by pressing the lights on bottom line, we have 2^(N-1) possibilities.
			for (int s = 0; s < 1 << (N - 1); s++) {
				// find the matrix P for this situation
				int[] nP = new int[N - 1];
				int bitS = Integer.bitCount(s);
				for (int i = 0; i < N - 1; i++) {
					if ((bitS & 1) == 0)
						nP[i] = P[i] ^ s;
					else {
						nP[i] ^= P[i] ^ ~s;
						nP[i] &= (1 << (N-1)) - 1;
					}
				}
				int m = n ^ (bitS & 1);
				// choose the line that has the largest (Num-of-0s - Num-of-1s);
				// N is very small, no need to sort
				boolean[] choose = new boolean[N - 1];
				int nret = 0;
				int sum = bitSum(nP);
				// the array of point-line affect is like 1:N-1, 2:2, 3:N-3, 4:4, 5:N-5, ...
				for (int i = 1; i < N; i++) {
					int c = 0, b = 0, maxBit = -1;
					for (int j = 0; j < N - 1; j++) {
						int bitCount = Integer.bitCount(nP[j]);
						if (!choose[j] && bitCount > maxBit) {
							maxBit = bitCount;
							c = j;
							b = bitCount;
						}
					}
					choose[c] = true;
					// sum of 1s in P[1...(N-1)]
					sum += (N - 1 - b) - b;
					// Num of 1s = Num of 1s in P[1...(N-1)][1...(N-1)] + P[1...(N-1)][N] + P[N][1...(N-1)] + P[N][N]
					nret = sum + ((i & 1) == 0 ? i : N - 1 - i) + bitS + (m ^ (i & 1));
					if (nret < ret)
						ret = nret;
				}

			}
			return ret;
		}

	}

	public static int answer(int[][] matrix) {
		// System.out.println(Arrays.deepToString(matrix));
		// int[][] mt = GaussianElimination.getArgumentedMatrix(matrix);
		// GaussianElimination ge = new GaussianElimination(mt);
		// ge.GE0_1();
		// System.out.println(ge);
		// return ge.minAnswer();
		return getMin(matrix);
	}
}
