#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main(void) {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int n = 0, max_val = 0;
	double sum = 0, avg = 0;

	cin >> n;
	
	vector<int> v(n);
	for (int i = 0; i < n; i++)
		cin >> v[i];

	auto it = max_element(v.begin(), v.end());
	max_val = *it;

	for (int x : v)
		sum += double(x) / max_val * 100;
	avg = sum / n;

	cout << avg;

	return 0;
}