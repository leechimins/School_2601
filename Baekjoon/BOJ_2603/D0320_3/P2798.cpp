#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main(void) {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	bool flag = true;
	int n, m, sum, ans = 0;
	cin >> n >> m;

	vector<int> card(n);
	for (int i = 0; i < n; i++)
		cin >> card[i];

	sort(card.begin(), card.end());

	for (int i = 0; flag && i < n - 2; i++) {
		for (int j = i + 1; flag && j < n - 1; j++) {
			for (int k = j + 1; flag && k < n; k++) {
				sum = card[i] + card[j] + card[k];
				if (sum > m)
					break;
				if (sum == m) {
					ans = sum;
					flag = false;
				}
				else if (sum > ans)
					ans = sum;
			}
		}
	}

	cout << ans;

	return 0;
}