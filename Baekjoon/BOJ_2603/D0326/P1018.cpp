#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main(void) {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int n, m, count, tmp, minCount = 32;
	char input;
	int check;
	cin >> n >> m;
	vector<vector<int>> board(n, vector<int>(m));
	vector<vector<int>> wBoard(n, vector<int>(m));
	vector<vector<int>> bBoard(n, vector<int>(m, 0));


	for (int i = 0; i < n; i++) {
		check = (i % 2 == 0);
		for (int j = 0; j < m; j++) {
			cin >> input;
			board[i][j] = (input == 'W');

			bBoard[i][j] = (check == board[i][j]);
			wBoard[i][j] = (check != board[i][j]);
			if (i > 0) {
				bBoard[i][j] += bBoard[i - 1][j];
				wBoard[i][j] += wBoard[i - 1][j];
			}
			if (j > 0) {
				bBoard[i][j] += bBoard[i][j - 1];
				wBoard[i][j] += wBoard[i][j - 1];
			}
			if (i > 0 && j > 0) {
				bBoard[i][j] -= bBoard[i - 1][j - 1];
				wBoard[i][j] -= wBoard[i - 1][j - 1];
			}

			check = !check;

			//cout << bBoard[i][j] << " ";
		}
		//cout << "\n";
	}

	for (int i = 0; i <= n - 8; i++) {
		for (int j = 0; j <= m - 8; j++) {
			count = bBoard[i + 7][j + 7] - bBoard[i][j];
			tmp = wBoard[i + 7][j + 7] - wBoard[i][j];
			count = min(count, tmp);
			//cout << count << " ";
			if (count > 32)
				count = 64 - count;
			if (minCount > count)
				minCount = count;
		}
	}

	cout << minCount;

	return 0;
}