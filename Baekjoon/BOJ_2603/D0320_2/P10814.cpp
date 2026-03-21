#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

struct Person {
	int age;
	string name;

	Person(int age, string name) : age(age), name(name) {}

	void print() const {
		cout << age << " " << name << "\n";
	}
};

bool compareAge(const Person& a, const Person& b) {
	return a.age < b.age;
}

int main(void) {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int n, age;
	string name;
	cin >> n;

	vector<Person> v;
	v.reserve(n);
	for (int i = 0; i < n; i++) {
		cin >> age >> name;
		v.emplace_back(age, name);
	}

	stable_sort(v.begin(), v.end(), compareAge);

	for (int i = 0; i < n; i++)
		v[i].print();

	return 0;
}