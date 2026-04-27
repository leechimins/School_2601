#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>

int main(void) {
	int n, m, start, end;
	int board[100][100];

	scanf("%d %d", &n, &m);
	scanf("%d", &start);

	for (int i = 0; i < m; i++)
		for (int j = 0; j < n - 1; j++)
			scanf("%1d", &board[i][j]);

	end = start;
	for (int j = 0; j < m; j++) {
		if (end == 0) {
			if (board[end][j])
				end++;
		}
		else if (end == n - 1) {
			if (board[end][j])
				end--;
		}
		else if (board[end][j])
			end++;
		else if (board[end - 1][j])
			end--;
	}

	printf("%d", end);

	return 0;
}

/*
6 3
0
10010
01001
00100
*/