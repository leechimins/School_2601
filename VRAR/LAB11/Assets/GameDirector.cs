using UnityEngine;
using TMPro;

public class GameDirector : MonoBehaviour
{
    GameObject timerText;
    float time = 25.0f;

    GameObject scoreText;
    int score = 0;

    GameObject generator;

    void Start()
    {
        this.timerText = GameObject.Find("Timer");
        this.scoreText = GameObject.Find("Score");
        this.generator = GameObject.Find("ItemGenerator");
    }

    void Update()
    {
        this.time -= Time.deltaTime;
        this.timerText.GetComponent<TextMeshProUGUI>().text = this.time.ToString("F1") + "s";
        switch (this.time) {
            case >= 20:
                this.generator.GetComponent<ItemGenerator>().setParameter(1, -0.03f, 2);
                break;
            case >= 10:
                this.generator.GetComponent<ItemGenerator>().setParameter(0.7f, -0.04f, 4);
                break;
            case >= 5:
                this.generator.GetComponent<ItemGenerator>().setParameter(0.4f, -0.06f, 6);
                break;
            default:
                this.generator.GetComponent<ItemGenerator>().setParameter(0.9f, -0.04f, 3);
                break;
        }
    }

    public void SetScore(bool flag) {
        if (flag) {
            this.score += 100;
        }
        else {
            this.score /= 2;
        }
        this.scoreText.GetComponent<TextMeshProUGUI>().text = this.score.ToString() + "Point";
    }
}
