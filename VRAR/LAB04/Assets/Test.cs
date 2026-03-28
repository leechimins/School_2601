using UnityEngine;

public class Test : MonoBehaviour {
    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start() {
        Debug.Log("Hello, World");
        
        int age = 0;
        age = 24;
        float height1 = 155.0f;
        float height2 = height1;
        string name;
        name = "이지민";

        Debug.Log(name + "의 나이는 " + age + "살이고, 키는 " + height2 + "cm 입니다.");

        int answer;
        answer = 9 / 4;
        Debug.Log("9 / 4는? " + answer + "(이유: answer가 정수라서 소수점 삭제");
        int n1 = 3, n2 = 4;
        answer = n1 - n2;
        Debug.Log(n1 + " - " + n2 + " = " + answer);
        answer = n1 * n2;
        Debug.Log(n1 + " * " + n2 + " = " + answer);

        answer += 2;
        Debug.Log("answer += 2 : " + answer);
        answer++;
        Debug.Log("answer++ : " + answer);

        string a = "hello ", b = "world";
        a += b;
        Debug.Log(a);

        int hp = 200;
        if (hp >= 100)
            Debug.Log("공격!");
        else
            Debug.Log("방어!");
        
        hp = 180;
        if (hp <= 50) {
            Debug.Log("도망!");
        }
        else if (hp >= 200) {
            Debug.Log("공격!");
        }
        else {
            Debug.Log("방어!");
        }

        for (int i = 0; i < 5; i++) {
            Debug.Log(i);
        }

        int[] arr = new int[5];
        int[] points = { 83, 99, 52, 93, 15 };
        int sum = 0;
        for (int i = 0; i < points.Length; i++) {
            if (points[i] >= 90)
                Debug.Log(points[i]);
                sum += points[i];
        }
        float avg = (float)sum / points.Length;
        Debug.Log("배열의 평균은 " + avg);

        Player myPlayer = new Player(100, 20);
        myPlayer.Attack();
        myPlayer.Damage(30);

        Vector2 playerPos = new Vector2(3.0f, 4.0f);
        playerPos.x += 8.0f;
        playerPos.y -= 1.0f;
        Debug.Log(playerPos);

        Vector2 startPos = new Vector2(2.0f, 1.0f);
        Vector2 endPos = new Vector2(8.0f, 5.0f);
        Vector2 dir = endPos - startPos;
        Debug.Log(dir);

        float len = dir.magnitude;
        Debug.Log(len);
    }

    // Update is called once per frame
    void Update() {
        
    }
}

public class Player {
    private int hp;
    private int power;

    public Player(int hp = 200, int power = 40) {
        this.hp = hp;
        this.power = power;
    }

    public void Attack() {
        Debug.Log(this.power + "데미지를 입혔다!!");
    }

    public void Damage(int dmg) {
        this.hp -= dmg;
        Debug.Log(dmg + "데미지를 입었다. (남은 체력: " + this.hp + ")");
    }
}