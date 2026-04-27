using TMPro;
using UnityEngine;

public class GameDirector : MonoBehaviour
{
    GameObject car;
    GameObject flag;
    GameObject distance;
    GameObject timer;
    GameObject scoreBoard;
    float time = 0;
    float length = 0;
    bool isFinished = false;

    void Start()
    {
        car = GameObject.Find("car_0");
        flag = GameObject.Find("flag_0");
        distance = GameObject.Find("distance");
        timer = GameObject.Find("timer");
        scoreBoard = GameObject.Find("score");
        scoreBoard.GetComponent<TextMeshProUGUI>().text = "Score: 0";
    }

    // Update is called once per frame
    void Update() {
        length = flag.transform.position.x - car.transform.position.x;
        if (length <= 0) {
            isFinished = true;
        }

        if (isFinished) {
            if (time <= 3f) {
                distance.GetComponent<TextMeshProUGUI>().text = "Success";
                scoreBoard.GetComponent<TextMeshProUGUI>().text = "Score: 100";
            }
            else if (time <= 5f) {
                distance.GetComponent<TextMeshProUGUI>().text = "Success";
                scoreBoard.GetComponent<TextMeshProUGUI>().text = "Score: 50";
            }
            else {
                distance.GetComponent<TextMeshProUGUI>().text = "To Late!";
                scoreBoard.GetComponent<TextMeshProUGUI>().text = "Score: 0";
            }
        }
        else {
            time += Time.deltaTime;
            timer.GetComponent<TextMeshProUGUI>().text = "Timer: " + time.ToString("F2");
            distance.GetComponent<TextMeshProUGUI>().text = "Distance: " + length.ToString("F2") + "m";
        }
    }
}
