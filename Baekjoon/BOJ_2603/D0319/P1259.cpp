#include <iostream>
#include <algorithm>

using namespace std;

int main(void) {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	string num;

	while (cin >> num && num != "0") {
		string res(num.rbegin(), num.rend());
		if (num == res)
			cout << "yes\n";
		else
			cout << "no\n";
	}

	return 0;
}