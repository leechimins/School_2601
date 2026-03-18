// 백준 문제 아니고 그냥 C++ 연습

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main(void) {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	vector<int> v;
	v.push_back(1);
	v.push_back(7);
	v.push_back(3);
	v.push_back(5);
	v.push_back(9);

	sort(v.begin(), v.end(), greater<int>());
	for (int x : v)
		cout << x << " ";
	cout << "\n";

	auto it = lower_bound(v.begin(), v.end(), 4);

	cout << it - v.begin();

	return 0;
}