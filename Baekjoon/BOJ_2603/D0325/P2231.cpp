#include <iostream>

using namespace std;

int main(void) {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int n, ans, len, tmp, sum;
	bool notFound = true;
	cin >> n;

	for (len = 0, tmp = n; tmp > 0; tmp /= 10)
		len++;

	for (ans = n - len * 9; notFound && ans <= n; ans++) {
		for (sum = ans, tmp = ans; tmp > 0; tmp /= 10)
			sum += tmp % 10;
		if (sum == n) {
			notFound = false;
			break;
		}
	}

	if (notFound)
		cout << 0;
	else
		cout << ans;

	return 0;
}