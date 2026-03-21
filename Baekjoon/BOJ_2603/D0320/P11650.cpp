#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

struct Point {
	int x, y;

	Point(int x = 0, int y = 0) : x(x), y(y) {}
	
	void print() const {
		cout << x << " " << y << "\n";
	}
};

bool comparePoints(const Point& a, const Point& b) {
	if (a.x == b.x)
		return a.y < b.y;
	return a.x < b.x;
}

int main(void) {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int n, x, y;
	cin >> n;

	vector<Point> v;
	v.reserve(n);
	for (int i = 0; i < n; i++) {
		cin >> x >> y;
		v.emplace_back(x, y);
	}

	sort(v.begin(), v.end(), comparePoints);

	for (int i = 0; i < n; i++)
		v[i].print();

	return 0;
}