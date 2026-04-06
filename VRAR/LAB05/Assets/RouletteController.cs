using UnityEngine;

public class RouletteController : MonoBehaviour
{
    float rotSpeed = 0f;

    void Start()
    {
        Application.targetFrameRate = 60;
        
    }

    void Update()
    {
        /*
        // #00
        if (Input.GetMouseButtonDown(0)) {
            this.rotSpeed = 10;
        }
        this.rotSpeed *= 0.96f;
        */

        /*
        // #01
        if (Input.GetMouseButton(1)) {
            this.rotSpeed = 10;
        } else {
            this.rotSpeed *= 0.96f;
        }
        */

        /*
        // #02
        if (Input.GetKeyDown(KeyCode.Space)) {
            this.rotSpeed = -10;
        }
        this.rotSpeed *= 0.96f;
        */

        /*
        // #03
        if (Input.GetKey(KeyCode.UpArrow)) {
            this.rotSpeed = 10;
        } else {
            this.rotSpeed *= 0.96f;
        }
        */

        bool isStop = false;

        if (Input.GetMouseButton(0)) {
            isStop = false;
            this.rotSpeed = 10;
        } else {
            this.rotSpeed *= 0.96f;
        }

        if (0 < this.rotSpeed && this.rotSpeed < 0.1f) {
            this.rotSpeed = 0;
            isStop = true;
        } else {
            transform.Rotate(0, 0, this.rotSpeed);
        }

        if (isStop) {
            printResult();
        }
    }

    void printResult() {
        float angle = transform.eulerAngles.z;
        if (330 <= angle || angle < 30) {
            Debug.Log("운수 나쁨");
        } else if (30 <= angle && angle < 90) {
            Debug.Log("운수 대통");
        } else if (90 <= angle && angle < 150) {
            Debug.Log("운수 매우 나쁨");
        } else if (150 <= angle && angle < 210) {
            Debug.Log("운수 보통");
        } else if (210 <= angle && angle < 270) {
            Debug.Log("운수 조심");
        } else if (270 <= angle && angle < 330) {
            Debug.Log("운수 좋음");
        } else {
            Debug.Log("오류");
        }
    }
}
